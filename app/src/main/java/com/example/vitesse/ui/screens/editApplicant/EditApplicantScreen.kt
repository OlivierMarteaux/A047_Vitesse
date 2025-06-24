package com.example.vitesse.ui.screens.editApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.components.VitesseDatePicker
import com.example.vitesse.ui.components.VitesseTopAppBar
import com.example.vitesse.ui.navigation.NavigationDestination
import com.example.vitesse.ui.screens.addApplicant.AddOrEditApplicantBody
import com.example.vitesse.ui.screens.addApplicant.AddOrEditApplicantFab
import com.example.vitesse.ui.screens.applicantDetail.ErrorScreen
import com.example.vitesse.ui.screens.applicantDetail.LoadingScreen
import com.example.vitesse.ui.screens.common.GetDataState
import java.time.LocalDate

object EditApplicantDestination : NavigationDestination {
    override val route = "edit_applicant"
    override val titleRes = R.string.edit_applicant_screen
    const val ApplicantIdArg = "applicantId"
    val routeWithArgs = "$route/{$ApplicantIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditApplicantScreen (
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    viewModel: EditApplicantViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    with (viewModel.uiState) {
        when (applicant) {
            is GetDataState.Loading -> LoadingScreen()
            is GetDataState.Error -> ErrorScreen()
            is GetDataState.Success -> SuccessEditScreen(
                navigateBack = navigateBack,
                modifier = modifier,
                applicant = applicant.data,
                isSaveable = isSaveable,
                onFabClick = { viewModel.saveEditedApplicant(); navigateBack() },
                onApplicantEdit = viewModel::updateUiState,
                onBirthdateChange = { viewModel.updateUiState( applicant.data.copy(birthDate = it)) }
            )
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun SuccessEditScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    applicant: Applicant,
    isSaveable: Boolean,
    onFabClick: () -> Unit,
    onApplicantEdit: (Applicant) -> Unit,
    onBirthdateChange: (LocalDate?) -> Unit
){
    Scaffold(
        modifier = modifier,
        topBar = {
            VitesseTopAppBar(
                title = stringResource(R.string.edit_a_candidate),
                navigateBack = navigateBack,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AddOrEditApplicantFab(
                enabled = isSaveable,
                onClick = onFabClick
            )
        }
    ){ topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = applicant,
            onApplicantEdit = onApplicantEdit,
        ){
            applicant.birthDate?.let{
                VitesseDatePicker(
                    initialDate = applicant.birthDate,
                    icon = ImageVector.vectorResource(id = R.drawable.cake_24dp),
                    onValueChange = onBirthdateChange,
                    isError = false,
                    errorText = ""
                )
            }
        }
    }
}