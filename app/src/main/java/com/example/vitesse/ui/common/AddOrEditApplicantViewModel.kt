package com.example.vitesse.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vitesse.data.model.Applicant
import extensions.isValidEmail

@RequiresApi(Build.VERSION_CODES.O)
data class AddOrEditApplicantUiState(
    val applicant: Applicant = Applicant(),
    val isSaveable: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
abstract class AddOrEditApplicantViewModel : ViewModel() {

    var uiState by mutableStateOf(AddOrEditApplicantUiState())
        protected set

    fun updateUiState(applicant: Applicant) {
        uiState = uiState.copy(
            applicant = applicant,
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