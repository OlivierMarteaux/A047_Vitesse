package com.example.vitesse.ui.screens.addApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.applicantDetail.GetDataState
import com.example.vitesse.ui.screens.common.AddOrEditApplicantUiState
import com.example.vitesse.ui.screens.common.AddOrEditApplicantViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class AddApplicantViewModel(
    private val applicantRepository: ApplicantRepository
): AddOrEditApplicantViewModel() {

init { uiState = AddOrEditApplicantUiState(applicant = GetDataState.Success(Applicant())) }

    fun saveNewApplicant() {
        viewModelScope.launch {
            val applicant = (uiState.applicant as GetDataState.Success<Applicant>).data
            applicantRepository.insertApplicant(applicant)
        }
    }
}