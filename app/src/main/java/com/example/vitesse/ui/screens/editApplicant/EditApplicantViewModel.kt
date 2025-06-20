package com.example.vitesse.ui.screens.editApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.common.AddOrEditApplicantUiState
import com.example.vitesse.ui.screens.common.AddOrEditApplicantViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class EditApplicantViewModel (
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
): AddOrEditApplicantViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[EditApplicantDestination.ApplicantIdArg])
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()

    fun saveEditedApplicant(){
        viewModelScope.launch {
            applicantRepository.updateApplicant(uiState.applicant)
        }
    }

    init {
        viewModelScope.launch {
            getApplicantById.collect {
                uiState = AddOrEditApplicantUiState(applicant = it, isSaveable = true)
            }
        }
    }
}