package com.example.vitesse.ui.addApplicant

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.vitesse.R
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.components.VitesseIcon
import com.example.vitesse.ui.components.VitesseTopAppBar
import com.example.vitesse.ui.components.texts.TextHeadLineLarge
import com.example.vitesse.ui.components.texts.TextLabelLarge
import com.example.vitesse.ui.navigation.NavigationDestination
import extensions.isValidEmail
import extensions.toLocalDateString
import extensions.toLong
import extensions.upTo
import utils.debugLog
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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
    val applicant: Applicant = viewModel.uiState.applicant

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
        floatingActionButton = {SaveApplicantFab(
            enabled = viewModel.uiState.isSaveable,
            onClick = { viewModel.saveNewApplicant(); navigateBack() }
        )}
    ) { topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = viewModel.uiState.applicant,
            onApplicantEdit = viewModel::updateUiState,
        ){
            DockedDatePicker(
                icon = ImageVector.vectorResource(id = R.drawable.cake_24dp),
                onValueChange = { viewModel.updateUiState(applicant.copy(birthDate = it)) },
                isError = applicant.birthDate == null,
                errorText = stringResource(R.string.mandatory_field)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddOrEditApplicantBody(
    modifier: Modifier = Modifier,
    applicant: Applicant,
    onApplicantEdit: (Applicant) -> Unit,
    datePicker: @Composable () -> Unit,
){
    // Get the ImagePicker launcher
    val imagePickerLauncher = vitesseImagePicker { uri ->
        onApplicantEdit(applicant.copy(photoUri = uri.toString()))
    }

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        applicant.run {
                Image(
                    painter = rememberAsyncImagePainter(photoUri?:R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clickable {
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest(
                                    PickVisualMedia.ImageOnly
                                )
                            )
                        }
                        .height(dimensionResource(R.dimen.image_height))
                        .padding(top = 7.dp, bottom = 22.dp),
                    contentDescription = null
                )
            AddOrEditApplicantCard(
                icon = Icons.Default.Person,
                label = stringResource(R.string.first_name),
                onValueChange = { onApplicantEdit(copy(firstName = it)) },
                value = firstName,
                isError = firstName.isEmpty(),
                errorText = stringResource(R.string.mandatory_field)
            )
            AddOrEditApplicantCard(
                label = stringResource(R.string.last_name),
                onValueChange = { onApplicantEdit(copy(lastName = it)) },
                value = lastName,
                isError = lastName.isEmpty(),
                errorText = stringResource(R.string.mandatory_field)
            )
            AddOrEditApplicantCard(
                icon = Icons.Outlined.Call,
                label = stringResource(R.string.phone_number),
                onValueChange = { onApplicantEdit(copy(phone = it)) },
                value = phone,
                isError = phone.isEmpty(),
                errorText = stringResource(R.string.mandatory_field),
                keyboardType = KeyboardType.Phone
            )
            AddOrEditApplicantCard(
                icon = ImageVector.vectorResource(id = R.drawable.chat_24px),
                label = stringResource(R.string.email),
                onValueChange = { onApplicantEdit(copy(email = it)) },
                value = email,
                isError = email.run { isEmpty() || !isValidEmail()},
                errorText = when {
                    email.isEmpty() -> stringResource(R.string.mandatory_field)
                    !email.isValidEmail() -> stringResource(R.string.invalid_format)
                    else -> ""
                                 },
                keyboardType = KeyboardType.Email
            )
            datePicker()
            AddOrEditApplicantCard(
                icon = ImageVector.vectorResource(id = R.drawable.money_24dp),
                label = stringResource(R.string.expected_salary),
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
                singleLine = false
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
    keyboardType: KeyboardType = KeyboardType.Text,
    errorText: String = "null",
    isError: Boolean = false,
    singleLine: Boolean = true
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
            singleLine = singleLine,
            shape = MaterialTheme.shapes.extraSmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
                errorPlaceholderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.scrim,
                unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                errorTextColor = MaterialTheme.colorScheme.scrim,
                focusedLabelColor = MaterialTheme.colorScheme.outline,
                unfocusedLabelColor = MaterialTheme.colorScheme.outline,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            isError = isError,
            supportingText = { if (isError) Text(errorText) },
            modifier = Modifier
                .padding(start = 15.dp)
                .weight(1f)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockedDatePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    icon: ImageVector,
    onValueChange: (LocalDate?) -> Unit,
    isError: Boolean,
    errorText: String
) {
    debugLog("AddorEditApplicantScreen: DockedDatePicker: initialDate: ${initialDate?.toLocalDateString()?:"null"}")

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

@Composable
fun SaveApplicantFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = false,
){
    FloatingActionButton(
        onClick = if (enabled) onClick else ({}),
        shape = CircleShape,
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
            .alpha(if (enabled) 1f else 0.5f),
    ){ Text(
        text = stringResource(R.string.save),
        style = MaterialTheme.typography.labelLarge
    )}
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun vitesseImagePicker(
    onImagePicked: (Uri) -> Unit
): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {

    // Registers a photo picker activity launcher in single-select mode.
    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(PickVisualMedia()) { pickedUri ->
            // Callback is invoked after the user selects a media item or closes the photo picker.
            pickedUri?.let { uri ->
                Log.d("OM_TAG", "VitesseImagePicker: picked URI: $uri")
                val persistentUri = copyImageToInternalStorage(context, uri)
                persistentUri?.let { onImagePicked(it) }
                Log.d("OM_TAG", "VitesseImagePicker: Persistent URI: $persistentUri")
            } ?: Log.d("OM_TAG", "VitesseImagePicker: No media selected")
        }
    return pickMedia
}

fun copyImageToInternalStorage(context: Context, uri: Uri): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        file.toUri() // Return a persistent URI
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}