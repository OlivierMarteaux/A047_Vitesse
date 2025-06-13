package com.example.vitesse.ui.addApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository

@RequiresApi(Build.VERSION_CODES.O)
data class ApplicantUiState(
    val applicant: Applicant = Applicant()
)

@RequiresApi(Build.VERSION_CODES.O)
class AddApplicantViewModel(private val applicantRepository: ApplicantRepository): ViewModel() {

    var uiState: ApplicantUiState by mutableStateOf(ApplicantUiState())
    private set

    fun updateApplicant(applicant: Applicant){
        uiState = uiState.copy(applicant = applicant)
    }

    suspend fun addApplicant(){
        applicantRepository.upsertApplicant(uiState.applicant)
    }
}