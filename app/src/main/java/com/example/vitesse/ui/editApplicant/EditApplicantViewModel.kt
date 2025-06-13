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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class EditApplicantViewModel (
    savedStateHandle: SavedStateHandle,
    applicantRepository: ApplicantRepository,
): ViewModel() {

    var uiState by mutableStateOf(ApplicantUiState())

    private val applicantId: Int = checkNotNull(savedStateHandle[EditApplicantDestination.ApplicantIdArg])
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()


    init {
        viewModelScope.launch {
            getApplicantById.collect {
                uiState = ApplicantUiState(it)
            }
        }
    }

    fun updateApplicant(applicant: Applicant){
        uiState = ApplicantUiState(applicant = applicant)
    }
}