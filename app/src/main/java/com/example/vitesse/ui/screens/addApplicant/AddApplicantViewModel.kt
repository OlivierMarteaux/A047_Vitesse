package com.example.vitesse.ui.screens.addApplicant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.ui.screens.common.AddOrEditApplicantViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class AddApplicantViewModel(
    private val applicantRepository: ApplicantRepository
): AddOrEditApplicantViewModel() {

    fun saveNewApplicant() {
        viewModelScope.launch {
            applicantRepository.insertApplicant(uiState.applicant)
        }
    }
}