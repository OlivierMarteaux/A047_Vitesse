package com.example.vitesse.ui.screens.applicantDetail

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
import com.example.vitesse.data.model.ExchangeRate
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import utils.debugLog
import java.util.Locale

sealed interface GetDataState<out T> {
    data object Loading : GetDataState<Nothing>
    data class Success<T>(val data: T) : GetDataState<T>
    data class Error(val errorMessage: String) : GetDataState<Nothing>
}

data class ApplicantDetailUiState(
    val applicant: GetDataState<Applicant> = GetDataState.Loading,
    val exchangeRate: GetDataState<ExchangeRate> = GetDataState.Loading
)

@RequiresApi(Build.VERSION_CODES.O)
class ApplicantDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
    private val currencyRepository: CurrencyRepository,
): ViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[ApplicantDetailDestination.ApplicantIdArg])
//    private val applicantId = 99
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()
    private suspend fun getExchangeRate(currency: String) = currencyRepository.getExchangeRate(currency)

    var uiState: ApplicantDetailUiState by mutableStateOf(ApplicantDetailUiState())
        private set
    var isFavorite by mutableStateOf(false)
        private set
    var callAlertDialog by mutableStateOf(false)
        private set

    fun showCallAlertDialog(state: Boolean){ callAlertDialog = state }
    fun toggleFavorite() { isFavorite = !isFavorite }

    fun updateApplicantFavoriteState(){
        if (uiState.applicant is GetDataState.Success) {
            viewModelScope.launch {
//                val applicant = uiState.applicant.copy(isFavorite = isFavorite)
                val applicant = (uiState.applicant as GetDataState.Success<Applicant>).data.copy(isFavorite = isFavorite)
                applicantRepository.updateApplicant(applicant)
            }
        }
    }

    fun deleteApplicant(applicant: Applicant){
        viewModelScope.launch {
            Log.d("OM_TAG", "DetailViewModel.delete(): $applicant")
            applicantRepository.deleteApplicant(applicant)
        }
    }

    init {
        viewModelScope.launch {
            loadExchangeRate()
            loadApplicant()
        }
    }

    private suspend fun loadExchangeRate() {
        val state =  try {
            when (Locale.getDefault().language){
                "fr" -> GetDataState.Success(getExchangeRate("eur"))
                "en" -> GetDataState.Success(getExchangeRate("gbp"))
                else -> GetDataState.Error("Unsupported language")
            }
        } catch (e: Exception) {
            debugLog("Api Error: ${e.message}")
            GetDataState.Error(e.message ?: "Unknown error")
        }
        uiState = uiState.copy(exchangeRate = state)
    }

    private suspend fun loadApplicant() {
        try {
            getApplicantById.distinctUntilChanged().collect() {
                uiState = uiState.copy(applicant = GetDataState.Success(it))
                isFavorite = it.isFavorite
            }
        } catch (e: Exception) {
            uiState = uiState.copy(applicant = GetDataState.Error(e.message ?: "Unknown error"))
        }
    }
}