package com.example.vitesse.ui.editApplicant

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vitesse.R
import com.example.vitesse.VitesseTopAppBar
import com.example.vitesse.ui.addApplicant.AddOrEditApplicantBody
import com.example.vitesse.ui.addApplicant.SaveFab
import com.example.vitesse.ui.navigation.NavigationDestination

object EditApplicantDestination : NavigationDestination {
    override val route = "edit_applicant"
    override val titleRes = R.string.edit_applicant_screen
    const val ApplicantIdArg = "applicantId"
    val routeWithArgs = "$route/{$ApplicantIdArg}"
}

@Composable
fun EditApplicantScreen (
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    //viewModel: EditApplicantViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { VitesseTopAppBar(
            title = stringResource(R.string.edit_a_candidate),
            modifier = Modifier,
            navigateBack = navigateBack,
            actions = {}
        ) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { SaveFab(onClick = { TODO()}) }
    ){ topAppBarPadding ->
        AddOrEditApplicantBody(
            modifier = modifier.padding(topAppBarPadding),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditApplicantScreenPreview(){
    EditApplicantScreen()
}