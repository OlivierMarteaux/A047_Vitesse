package com.example.vitesse.ui.screens.editApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.common.AddOrEditApplicantUiState
import com.example.vitesse.ui.screens.common.AddOrEditApplicantViewModel
import com.example.vitesse.ui.screens.common.GetDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * ViewModel for editing an existing applicant.
 *
 * Fetches the applicant by ID and manages UI state for editing.
 * Extends [AddOrEditApplicantViewModel] to reuse common add/edit logic and validation.
 *
 * @property savedStateHandle Provides access to saved state, including the applicant ID argument.
 * @property applicantRepository Repository to perform data operations on applicants.
 */
@RequiresApi(Build.VERSION_CODES.O)
class EditApplicantViewModel (
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
): AddOrEditApplicantViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[EditApplicantDestination.ApplicantIdArg])
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()

    /**
     * Saves the edited applicant by updating the repository with the current UI state data.
     */
    fun saveEditedApplicant(){
        viewModelScope.launch {
            applicantRepository.updateApplicant((uiState.applicant as GetDataState.Success<Applicant>).data )
        }
    }

    init {
        viewModelScope.launch {
            try {
                getApplicantById.collect {
                    uiState = AddOrEditApplicantUiState(
                        applicant = GetDataState.Success(it),
                        isSaveable = true
                    )
                }
            } catch (e: Exception) {
                uiState = AddOrEditApplicantUiState(
                    applicant = GetDataState.Error(e.message ?: "Unknown error")
                )
            }
        }
    }
}