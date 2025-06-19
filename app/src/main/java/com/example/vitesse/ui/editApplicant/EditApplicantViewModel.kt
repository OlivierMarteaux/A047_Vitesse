package com.example.vitesse.ui.editApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.addApplicant.ApplicantUiState
import extensions.isValidEmail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class EditApplicantViewModel (
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
): ViewModel() {

    var uiState by mutableStateOf(ApplicantUiState(isEnabled = true))
        private set

    private val applicantId: Int = checkNotNull(savedStateHandle[EditApplicantDestination.ApplicantIdArg])
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()

    init {
        viewModelScope.launch {
            getApplicantById.collect {
                uiState = ApplicantUiState(applicant = it, isEnabled = true)
            }
        }
    }

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

    fun saveApplicant(){
        viewModelScope.launch {
            applicantRepository.upsertApplicant(uiState.applicant)
        }
    }
}