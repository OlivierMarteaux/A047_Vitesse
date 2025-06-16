package com.example.vitesse.ui.addApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import extensions.isValidEmail
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
data class ApplicantUiState(
    val applicant: Applicant = Applicant(),
    val isEnabled: Boolean
)

@RequiresApi(Build.VERSION_CODES.O)
class AddApplicantViewModel(private val applicantRepository: ApplicantRepository): ViewModel() {

    var uiState: ApplicantUiState by mutableStateOf(ApplicantUiState(isEnabled = false))
    private set

    fun updateApplicant(applicant: Applicant){
        uiState = uiState.copy(
            applicant = applicant,
            isEnabled = with(applicant) {
                firstName.isNotBlank() &&
                        lastName.isNotBlank() &&
                        phone.isNotBlank() &&
                        email.isNotBlank() &&
                        email.isValidEmail()
            }
        )
    }

    fun saveApplicant() {
        viewModelScope.launch {
            applicantRepository.upsertApplicant(uiState.applicant)
        }
    }
}