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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import utils.debugLog
import java.util.Locale

sealed interface GetDataState<out T> {
    object Loading : GetDataState<Nothing>
    data class Success<T>(val data: T) : GetDataState<T>
    data class Error(val errorMessage: String) : GetDataState<Nothing>
}

data class ApplicantDetailUiState(
    val applicant: GetDataState<Applicant> = GetDataState.Loading,
    val exchangeRate: GetDataState<ExchangeRate> = GetDataState.Loading
)

//@RequiresApi(Build.VERSION_CODES.O)
//data class ApplicantDetailUiState(
//    val applicant: Applicant = Applicant(),
//    val exchangeRate: ExchangeRate = ExchangeRate()
//)

@RequiresApi(Build.VERSION_CODES.O)
class ApplicantDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
    private val currencyRepository: CurrencyRepository,
): ViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[ApplicantDetailDestination.ApplicantIdArg])
//    private val applicantId = 99
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()
    private suspend fun getEurExchangeRate() = currencyRepository.getEurExchangeRate()
    private suspend fun getGbpExchangeRate() = currencyRepository.getGbpExchangeRate()

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
            try {
                getApplicantById.collect {
//                    uiState = ApplicantDetailUiState(it)
//                    delay(2000)
                    uiState = ApplicantDetailUiState(applicant = GetDataState.Success(it))
                    when (Locale.getDefault().language){
//                        "fr" -> uiState = uiState.copy(exchangeRate = getEurExchangeRate())
//                        "en" -> uiState = uiState.copy(exchangeRate = getGbpExchangeRate())
                        "fr" -> uiState = uiState.copy(exchangeRate = try{
                            GetDataState.Success(getEurExchangeRate())
                        } catch (e: Exception){
                            debugLog("Api Error: ${e.message}")
                            GetDataState.Error(e.message ?: "Unknown error")
                        })
                        "en" -> uiState = uiState.copy(exchangeRate = try{GetDataState.Success(getGbpExchangeRate())} catch (e: Exception){GetDataState.Error(e.message ?: "Unknown error")})
                    }
                    isFavorite = it.isFavorite
                }
            } catch (e: Exception) {
                uiState = ApplicantDetailUiState(applicant = GetDataState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}