package com.example.vitesse.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vitesse.R
import com.example.vitesse.ui.components.texts.TextHeadLineLarge
import com.example.vitesse.ui.components.texts.TextLabelLarge
import extensions.toLocalDateString
import extensions.toLong
import extensions.upTo
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * A composable date picker component that allows the user to select a date from the past or input it manually.
 *
 * This component restricts selection to past dates only and displays an icon alongside the date picker.
 * It supports error display and propagates the selected date to the caller.
 *
 * @param modifier Optional [Modifier] to style the outer layout container.
 * @param initialDate The initially selected [LocalDate], or null if no date is selected.
 * @param icon The [ImageVector] icon displayed next to the date picker.
 * @param onValueChange Callback invoked whenever the selected date changes; returns the selected [LocalDate] or null.
 * @param isError Boolean flag indicating whether to display an error state.
 * @param errorText The error message string shown below the date input field when [isError] is true.
 *
 * Usage example:
 * ```
 * VitesseDatePicker(
 *     initialDate = LocalDate.of(1990, 1, 1),
 *     icon = Icons.Default.DateRange,
 *     onValueChange = { date -> /* handle new date */ },
 *     isError = false,
 *     errorText = ""
 * )
 * ```
 *
 * Requirements:
 * - Requires API level 26 (Android O) or higher.
 * - Uses Material3 experimental API for DatePicker.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitesseDatePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    icon: ImageVector,
    onValueChange: (LocalDate?) -> Unit,
    isError: Boolean,
    errorText: String
) {
    // a date picker state that allows only past dates to be selected.
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        selectableDates = LocalDate.now().upTo(),
        initialSelectedDateMillis = initialDate?.toLong()
    )

    // 🔄 Propagate changes to parent
    val selectedDateMillis = datePickerState.selectedDateMillis
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    selectedDateMillis?.let {
        selectedDate = Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        onValueChange(selectedDate)
    }

    Row( modifier = modifier.padding(bottom = 11.dp)) {

        VitesseIcon(icon = icon, modifier = Modifier.padding(top = 14.dp, end = 15.dp))
        Box {
            DatePicker(
                state = datePickerState,
                title = {
                    TextLabelLarge(
                        text = stringResource(R.string.select_a_date),
                        modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                    )
                },
                headline = {
                    TextHeadLineLarge(
                        text = stringResource(R.string.input_a_date),
                        modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
                    )
                },
                modifier = Modifier.clip(shape = MaterialTheme.shapes.extraLarge),
                colors = DatePickerDefaults.colors()
            )

            // a surface to make the outlinedTextField Read-Only and clickable:
            if (datePickerState.displayMode == DisplayMode.Input){
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    modifier = modifier
                        .padding(start = 14.dp, top = 130.dp, end = 14.dp)
                        .height(70.dp)
                        .fillMaxWidth()
//                        .border(width = 1.dp, color = Color.Black)
                ){}
                OutlinedTextField(
                    value = selectedDate?.toLocalDateString()?:"",
                    onValueChange = {},
                    label = { Text("Date") },
                    isError = isError,
                    supportingText = { if (isError) Text(errorText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 130.dp, end = 24.dp),
                )
                Surface(
                    color = Color.Transparent,
                    modifier = modifier
                        .padding(start = 14.dp, top = 130.dp, end = 14.dp)
                        .height(70.dp)
                        .fillMaxWidth()
                        .clickable { datePickerState.displayMode = DisplayMode.Picker }
//                        .border(width = 1.dp, color = Color.Black)
                ){}
            }
        }
    }
}