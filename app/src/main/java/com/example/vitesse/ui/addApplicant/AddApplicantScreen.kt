package com.example.vitesse.ui.addApplicant

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vitesse.R
import com.example.vitesse.ui.AppViewModelProvider
import com.example.vitesse.ui.applicantDetail.TextBodyLarge
import com.example.vitesse.ui.navigation.NavigationDestination

object AddApplicantDestination : NavigationDestination {
    override val route = "add_applicant"
    override val titleRes = R.string.add_applicant_screen
}

@Composable
fun AddApplicantScreen(
    modifier: Modifier = Modifier,
    viewModel: AddApplicantViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    TextBodyLarge(text = "text_exemple",)
//    AddOrEditApplicantBody(
//        modifier = modifier
//    )
}