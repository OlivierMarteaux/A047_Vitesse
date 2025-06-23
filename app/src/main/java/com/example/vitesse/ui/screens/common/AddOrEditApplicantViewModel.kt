package com.example.vitesse.ui.screens.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.ui.screens.applicantDetail.GetDataState
import extensions.isValidEmail

@RequiresApi(Build.VERSION_CODES.O)
data class AddOrEditApplicantUiState(
    val applicant: GetDataState<Applicant> = GetDataState.Loading,
    val isSaveable: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
abstract class AddOrEditApplicantViewModel : ViewModel() {

    var uiState by mutableStateOf(AddOrEditApplicantUiState())
        protected set

    fun updateUiState(applicant: Applicant) {
        uiState = uiState.copy(
            applicant = GetDataState.Success(applicant),
            isSaveable = with(applicant) {
                firstName.isNotBlank() &&
                        lastName.isNotBlank() &&
                        phone.isNotBlank() &&
                        email.isNotBlank() &&
                        email.isValidEmail() &&
                        birthDate != null
            }
        )
    }
}