package com.example.vitesse.ui.addApplicant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vitesse.R
import com.example.vitesse.TextHeadLineLarge
import com.example.vitesse.TextLabelLarge
import com.example.vitesse.VitesseIcon
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.ui.navigation.NavigationDestination

object AddApplicantDestination : NavigationDestination {
    override val route = "add_applicant"
    override val titleRes = R.string.add_applicant_screen
}

@Composable
fun AddApplicantScreen(
    modifier: Modifier = Modifier,
//    viewModel: AddApplicantViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            VitesseTopAppBar(
                title = "Alice JOHNSON",
                modifier = Modifier,
                navigateBack = navigateBack,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { SaveFab(onClick = { /*TODO*/ }) }
    ) { topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier
                .padding(topAppBarPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
        }
    }
}

@Composable
fun AddOrEditApplicantBody(
    modifier: Modifier = Modifier,
    onSave: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
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
            label = stringResource(R.string.first_name)
        )
        AddOrEditApplicantCard(
            label = stringResource(R.string.last_name)
        )
        AddOrEditApplicantCard(
            icon = Icons.Outlined.Call,
            label = stringResource(R.string.phone_number)
        )
        AddOrEditApplicantCard(
            icon = ImageVector.vectorResource(id = R.drawable.chat_24px),
            label = stringResource(R.string.email)
        )
        DatePickerModalInput(
            onDateSelected = {},
            onDismiss = {},
            icon = ImageVector.vectorResource(id = R.drawable.cake_24dp)
        )
//        VitesseDatePicker(
//            state = rememberDatePickerState(),
//            onDateSelected = {},
//            onDismiss = {},
//            icon = ImageVector.vectorResource(id = R.drawable.cake_24dp)
//        )
        AddOrEditApplicantCard(
            icon = ImageVector.vectorResource(id = R.drawable.money_24dp),
            label = stringResource(R.string.salary_expectations)
        )
        AddOrEditApplicantCard(
            icon = Icons.Outlined.Edit,
            label = stringResource(R.string.notes),
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

@Composable
fun AddOrEditApplicantCard(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    label: String,
){
    Row(
        modifier = modifier.padding(bottom = 45.dp),
    ){
        icon?.let{ VitesseIcon(icon = icon, modifier = Modifier.padding(top = 14.dp)) }
            ?: Spacer(Modifier.size(24.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
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
    icon: ImageVector
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