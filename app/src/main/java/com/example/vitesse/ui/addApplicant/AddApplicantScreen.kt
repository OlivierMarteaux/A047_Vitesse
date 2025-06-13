package com.example.vitesse.ui.addApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.TextHeadLineLarge
import com.example.vitesse.TextLabelLarge
import com.example.vitesse.VitesseIcon
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.navigation.NavigationDestination
import java.time.LocalDate
import kotlin.math.round

object AddApplicantDestination : NavigationDestination {
    override val route = "add_applicant"
    override val titleRes = R.string.add_applicant_screen
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddApplicantScreen(
    modifier: Modifier = Modifier,
    viewModel: AddApplicantViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VitesseTopAppBar(
                title = stringResource(R.string.add_a_candidate),
                modifier = Modifier,
                navigateBack = navigateBack,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { SaveFab(onClick = { TODO()}) }
    ) { topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = viewModel.uiState.applicant,
            onApplicantEdit = viewModel::updateApplicant
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddOrEditApplicantBody(
    modifier: Modifier = Modifier,
    applicant: Applicant,
    onApplicantEdit: (Applicant) -> Unit,
){
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        with (applicant) {
            Image(
                painter = painterResource(R.drawable.martyna_siddeswara),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.image_height))
                    .padding(14.dp),
                contentDescription = null
            )
            AddOrEditApplicantCard(
                icon = Icons.Default.Person,
                label = stringResource(R.string.first_name),
                onValueChange = { onApplicantEdit(copy(firstName = it)) },
                value = firstName,
            )
            AddOrEditApplicantCard(
                label = stringResource(R.string.last_name),
                onValueChange = { onApplicantEdit(copy(lastName = it)) },
                value = lastName,
            )
            AddOrEditApplicantCard(
                icon = Icons.Outlined.Call,
                label = stringResource(R.string.phone_number),
                onValueChange = { onApplicantEdit(copy(phone = it)) },
                value = phone,
                keyboardType = KeyboardType.Phone
            )
            AddOrEditApplicantCard(
                icon = ImageVector.vectorResource(id = R.drawable.chat_24px),
                label = stringResource(R.string.email),
                onValueChange = { onApplicantEdit(copy(email = it)) },
                value = email,
                keyboardType = KeyboardType.Email
            )
            DatePickerModalInput(
                onDateSelected = {},
                onDismiss = {},
                icon = ImageVector.vectorResource(id = R.drawable.cake_24dp),
                onValueChange = { onApplicantEdit(copy(birthDate = it)) },
            )
//        VitesseDatePicker(
//            state = rememberDatePickerState(),
//            onDateSelected = {},
//            onDismiss = {},
//            icon = ImageVector.vectorResource(id = R.drawable.cake_24dp)
//        )
            AddOrEditApplicantCard(
                icon = ImageVector.vectorResource(id = R.drawable.money_24dp),
                label = stringResource(R.string.salary_expectations),
                onValueChange = { onApplicantEdit(copy(salary = it.toDoubleOrNull() ?: 0.0)) },
                value = round(salary).toInt().toString().run { if (this == "0") "" else this },
                keyboardType = KeyboardType.Number
            )
            AddOrEditApplicantCard(
                icon = Icons.Outlined.Edit,
                label = stringResource(R.string.notes),
                modifier = Modifier.padding(bottom = 10.dp),
                onValueChange = { onApplicantEdit(copy(note = it)) },
                value = note,
                imeAction = ImeAction.Done,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddOrEditApplicantCard(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String,
    onValueChange: (String) -> Unit,
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text
){
    Row(
        modifier = modifier.padding(bottom = 45.dp),
    ){
        icon?.let{ VitesseIcon(icon = icon, modifier = Modifier.padding(top = 14.dp)) }
            ?: Spacer(Modifier.size(24.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {Text(label)},
            placeholder = { Text(label) },
            singleLine = true,
            shape = MaterialTheme.shapes.extraSmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            modifier = modifier
                .padding(start = 15.dp)
                .weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onValueChange: (LocalDate) -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    Row( modifier = modifier.padding(bottom = 11.dp)) {

        VitesseIcon(icon = icon, modifier = Modifier.padding(top = 14.dp, end = 15.dp))
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
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ),
            modifier = Modifier.clip(shape = MaterialTheme.shapes.extraLarge)
        )
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun VitesseDatePicker(
//    state: DatePickerState,
//    modifier: Modifier = Modifier,
//    onDismiss: () -> Unit,
//    onDateSelected: (Long?) -> Unit,
//    icon: ImageVector
//){
//    var showDatePicker by remember { mutableStateOf(false) }
//    val datePickerState = rememberDatePickerState()
//    val selectedDate = datePickerState.selectedDateMillis?.let {
//        convertMillisToDate(it)
//    } ?: ""
//
//    Row (
//        modifier = modifier.padding(bottom = 11.dp).fillMaxWidth(),
//    ){
//        VitesseIcon(icon = icon, modifier = Modifier.padding(top = 14.dp, end = 15.dp))
//        Card (modifier = Modifier.fillMaxWidth()) {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                TextLabelLarge(
//                    text = stringResource(R.string.select_a_date),
//                    modifier = Modifier.padding(top = 16.dp, start = 24.dp),
//                )
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                ) {
//                    TextHeadLineLarge(
//                        text = stringResource(R.string.input_a_date),
//                        modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
//                    )
//                    VitesseIconButton(
//                        icon = Icons.Default.DateRange,
//                        onClick = { showDatePicker = true },
//                        )
//                }
//                if (showDatePicker) {
//                    Popup(
//                        onDismissRequest = { showDatePicker = false },
//                        alignment = Alignment.TopStart
//                    ) { DatePicker(state = state) }
//                }
//            }
//        }
//    }
//}
//
//fun convertMillisToDate(millis: Long): String {
//    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
//    return formatter.format(Date(millis))
//}

@Composable
fun SaveFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = false,
){
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {
        Text("Save", style = MaterialTheme.typography.labelLarge)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddApplicantScreenPreview() {
    AddApplicantScreen(navigateBack = {})
}
//@Preview
//@Composable
//fun DatePickerModalPreview() {
//    DatePickerModalInput(onDateSelected = {}, onDismiss = {})
//}