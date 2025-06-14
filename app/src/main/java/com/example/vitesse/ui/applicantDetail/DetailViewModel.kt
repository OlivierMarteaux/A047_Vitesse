package com.example.vitesse.ui.applicantDetail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.model.Currency
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
data class ApplicantDetailUiState(
    val applicant: Applicant = Applicant(),
    val currency: Currency = Currency()
)

@RequiresApi(Build.VERSION_CODES.O)
class ApplicantDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
    private val currencyRepository: CurrencyRepository
): ViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[ApplicantDetailDestination.ApplicantIdArg])
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()
    private suspend fun getCurrency() = currencyRepository.getCurrency()

    var uiState: ApplicantDetailUiState by mutableStateOf(ApplicantDetailUiState())
        private set

    fun update(applicant: Applicant){
        viewModelScope.launch {
            applicantRepository.upsertApplicant(applicant)
        }
    }

    fun delete(applicant: Applicant){
        viewModelScope.launch {
            applicantRepository.deleteApplicant(applicant)
        }
    }

    init {
        Log.d("OM_TAG", "ApplicantDetailViewModel init")
        viewModelScope.launch {
            getApplicantById.collect {
                uiState = ApplicantDetailUiState(it)
                uiState = uiState.copy(currency = getCurrency())
                Log.d("OM_TAG", "uiState: $uiState")
            }
        }
    }
}