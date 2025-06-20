package com.example.vitesse.ui.editApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.addApplicant.AddOrEditApplicantBody
import com.example.vitesse.ui.addApplicant.DockedDatePicker
import com.example.vitesse.ui.addApplicant.SaveApplicantFab
import com.example.vitesse.ui.components.VitesseTopAppBar
import com.example.vitesse.ui.navigation.NavigationDestination

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
    val applicant = viewModel.uiState.applicant

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { VitesseTopAppBar(
            title = stringResource(R.string.edit_a_candidate),
            modifier = Modifier,
            navigateBack = navigateBack,
            actions = {}
        ) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {SaveApplicantFab(
            enabled = viewModel.uiState.isSaveable,
            onClick = { viewModel.saveEditedApplicant(); navigateBack() }
        )}
    ){ topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding),
            applicant = viewModel.uiState.applicant,
            onApplicantEdit = viewModel::updateUiState
        ){
            applicant.birthDate?.let{
                DockedDatePicker(
                    initialDate = applicant.birthDate,
                    icon = ImageVector.vectorResource(id = R.drawable.cake_24dp),
                    onValueChange = { viewModel.updateUiState(applicant.copy(birthDate = it)) },
                    isError = false,
                    errorText = ""
                )
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EditApplicantScreenPreview(){
//    EditApplicantScreen()
//}