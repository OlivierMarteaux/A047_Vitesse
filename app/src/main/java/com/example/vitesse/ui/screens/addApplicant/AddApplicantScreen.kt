package com.example.vitesse.ui.screens.addApplicant

import android.os.Build
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.components.VitesseDatePicker
import com.example.vitesse.ui.components.VitesseIcon
import com.example.vitesse.ui.components.VitesseImage
import com.example.vitesse.ui.components.VitesseTopAppBar
import com.example.vitesse.ui.components.texts.TextLabelLarge
import com.example.vitesse.ui.components.vitesseImagePicker
import com.example.vitesse.ui.navigation.NavigationDestination
import com.example.vitesse.ui.screens.applicantDetail.ErrorScreen
import com.example.vitesse.ui.screens.applicantDetail.GetDataState
import com.example.vitesse.ui.screens.applicantDetail.LoadingScreen
import com.example.vitesse.ui.screens.common.AddOrEditApplicantUiState
import extensions.isValidEmail
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
    val uiState = viewModel.uiState
    when (uiState.applicant) {
        is GetDataState.Loading -> { LoadingScreen()
        }
        is GetDataState.Success -> {
            SuccessAddScreen(
                viewModel = viewModel,
                uiState = uiState,
                navigateBack = navigateBack,
                modifier = modifier,
            )
        }
        is GetDataState.Error -> {
            ErrorScreen(retryAction = {})
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun SuccessAddScreen(
    modifier: Modifier = Modifier,
    viewModel: AddApplicantViewModel,
    uiState: AddOrEditApplicantUiState,
    navigateBack: () -> Unit
){
    val applicant: Applicant = uiState.applicant.run { (this as GetDataState.Success<Applicant>).data }

    Scaffold(
        modifier = modifier,
        topBar = {
            VitesseTopAppBar(
                title = stringResource(R.string.add_a_candidate),
                navigateBack = navigateBack,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AddOrEditApplicantFab(
                enabled = viewModel.uiState.isSaveable,
                onClick = { viewModel.saveNewApplicant(); navigateBack() }
            )
        }
    ) { innerPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(innerPadding),
            applicant = applicant,
            onApplicantEdit = viewModel::updateUiState,
        ){
            VitesseDatePicker(
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
            VitesseImage(
                photoUri = photoUri,
                modifier = Modifier
                    .width(dimensionResource(R.dimen.image_width))
                    .height(dimensionResource(R.dimen.image_height))
                    .padding(top = 7.dp, bottom = 22.dp)
                    .clickable {
                        imagePickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    }
            )
            AddOrEditApplicantTextField(
                icon = Icons.Default.Person,
                label = stringResource(R.string.first_name),
                onValueChange = { onApplicantEdit(copy(firstName = it)) },
                value = firstName,
                isError = firstName.isEmpty(),
                errorText = stringResource(R.string.mandatory_field)
            )
            AddOrEditApplicantTextField(
                label = stringResource(R.string.last_name),
                onValueChange = { onApplicantEdit(copy(lastName = it)) },
                value = lastName,
                isError = lastName.isEmpty(),
                errorText = stringResource(R.string.mandatory_field)
            )
            AddOrEditApplicantTextField(
                icon = Icons.Outlined.Call,
                label = stringResource(R.string.phone_number),
                onValueChange = { onApplicantEdit(copy(phone = it)) },
                value = phone,
                isError = phone.isEmpty(),
                errorText = stringResource(R.string.mandatory_field),
                keyboardType = KeyboardType.Phone
            )
            AddOrEditApplicantTextField(
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
            AddOrEditApplicantTextField(
                icon = ImageVector.vectorResource(id = R.drawable.money_24dp),
                label = stringResource(R.string.expected_salary),
                onValueChange = { onApplicantEdit(copy(salary = it.toDoubleOrNull() ?: 0.0)) },
                value = round(salary).toInt().toString().run { if (this == "0") "" else this },
                keyboardType = KeyboardType.Number
            )
            AddOrEditApplicantTextField(
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
fun AddOrEditApplicantTextField(
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

@Composable
fun AddOrEditApplicantFab(
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
    ){ TextLabelLarge(stringResource(R.string.save)) }
}