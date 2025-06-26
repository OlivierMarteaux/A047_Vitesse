package com.example.vitesse.ui.screens.addApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.common.AddOrEditApplicantUiState
import com.example.vitesse.ui.screens.common.AddOrEditApplicantViewModel
import com.example.vitesse.ui.screens.common.GetDataState
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the state and logic related to adding a new applicant.
 *
 * @property applicantRepository Repository to perform CRUD operations on applicants.
 *
 * Initializes [uiState] with an empty [Applicant] wrapped in a [GetDataState.Success].
 */
@RequiresApi(Build.VERSION_CODES.O)
class AddApplicantViewModel(
    private val applicantRepository: ApplicantRepository
): AddOrEditApplicantViewModel() {

init { uiState = AddOrEditApplicantUiState(applicant = GetDataState.Success(Applicant())) }

    /**
     * Saves the new applicant to the repository asynchronously.
     *
     * Extracts the current applicant data from [uiState] and calls [ApplicantRepository.insertApplicant].
     */
    fun saveNewApplicant() {
        viewModelScope.launch {
            val applicant = (uiState.applicant as GetDataState.Success<Applicant>).data
            applicantRepository.insertApplicant(applicant)
        }
    }
}