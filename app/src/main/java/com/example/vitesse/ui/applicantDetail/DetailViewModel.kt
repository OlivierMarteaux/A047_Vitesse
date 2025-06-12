package com.example.vitesse.ui.applicantDetail

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
data class ApplicantDetailUiState(
    val applicant: Applicant = Applicant(),
)

@RequiresApi(Build.VERSION_CODES.O)
class ApplicantDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository
): ViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[ApplicantDetailDestination.ApplicantIdArg])
    val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()

    var uiState: ApplicantDetailUiState by mutableStateOf(ApplicantDetailUiState())
        private set

    init {
        viewModelScope.launch {
            getApplicantById.collect {
                uiState = ApplicantDetailUiState(it)
            }
        }
    }
}