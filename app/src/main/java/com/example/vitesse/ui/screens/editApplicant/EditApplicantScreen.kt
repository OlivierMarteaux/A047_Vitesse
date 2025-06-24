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
import com.example.vitesse.ui.screens.common.AddOrEditApplicantUiState
import com.example.vitesse.ui.screens.common.GetDataState

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
    val uiState = viewModel.uiState
    when (uiState.applicant) {
        is GetDataState.Loading -> { LoadingScreen()
        }
        is GetDataState.Success -> {
            SuccessEditScreen(
                viewModel = viewModel,
                uiState = uiState,
                navigateBack = navigateBack,
                modifier = modifier,
            )
        }
        is GetDataState.Error -> {
            ErrorScreen()
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun SuccessEditScreen(
    modifier: Modifier = Modifier,
    viewModel: EditApplicantViewModel,
    uiState: AddOrEditApplicantUiState,
    navigateBack: () -> Unit
){
    val applicant = uiState.applicant

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
                enabled = viewModel.uiState.isSaveable,
                onClick = { viewModel.saveEditedApplicant(); navigateBack() }
            )
        }
    ){ topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = (applicant as GetDataState.Success<Applicant>).data,
            onApplicantEdit = viewModel::updateUiState
        ){
            applicant.data.birthDate?.let{
                VitesseDatePicker(
                    initialDate = applicant.data.birthDate,
                    icon = ImageVector.vectorResource(id = R.drawable.cake_24dp),
                    onValueChange = { viewModel.updateUiState(applicant.data.copy(birthDate = it)) },
                    isError = false,
                    errorText = ""
                )
            }
        }
    }
}