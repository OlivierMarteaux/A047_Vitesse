package com.example.vitesse.ui.screens.applicantDetail

import android.os.Build
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
import com.example.vitesse.ui.screens.common.GetDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import utils.Logger
import java.util.Locale

/**
 * UI state holder for the applicant detail screen, encapsulating the applicant data
 * and the current exchange rate state.
 *
 * @property applicant The current state of the [Applicant] data, wrapped in [GetDataState].
 * Defaults to [GetDataState.Loading].
 * @property exchangeRate The current state of the [ExchangeRate] data, wrapped in [GetDataState].
 * Defaults to [GetDataState.Loading].
 */
data class ApplicantDetailUiState(
    val applicant: GetDataState<Applicant> = GetDataState.Loading,
    val exchangeRate: GetDataState<ExchangeRate> = GetDataState.Loading
)

/**
 * ViewModel responsible for managing the detailed view of an applicant, including
 * loading applicant data and currency exchange rates, and handling user actions
 * such as toggling favorite status and deleting the applicant.
 *
 * @property savedStateHandle Provides access to saved state and navigation arguments.
 * @property applicantRepository Repository to fetch and update applicant data.
 * @property currencyRepository Repository to fetch currency exchange rates.
 * @property logger Logger for debug and error messages.
 *
 * @throws IllegalStateException if [ApplicantDetailDestination.ApplicantIdArg] is missing in [savedStateHandle].
 */
@RequiresApi(Build.VERSION_CODES.O)
class ApplicantDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicantRepository: ApplicantRepository,
    private val currencyRepository: CurrencyRepository,
    private val logger: Logger
): ViewModel() {

    private val applicantId: Int = checkNotNull(savedStateHandle[ApplicantDetailDestination.ApplicantIdArg])
//    private val applicantId = 99
    private val getApplicantById: Flow<Applicant> = applicantRepository.getApplicantById(this.applicantId).filterNotNull()
    private suspend fun getExchangeRate(currency: String) = currencyRepository.getExchangeRate(currency)

    /** Holds the current UI state for applicant details and exchange rate. */
    var uiState: ApplicantDetailUiState by mutableStateOf(ApplicantDetailUiState())
        private set
    /** Tracks whether the applicant is marked as favorite. */
    var isFavorite by mutableStateOf(false)
        private set
    /** Controls visibility of the call permission alert dialog. */
    var callPermissionAlertDialog by mutableStateOf(false)
        private set
    /** Controls visibility of the delete confirmation dialog. */
    var deleteConfirmationDialog by mutableStateOf(false)
        private set

    /**
     * Shows or hides the call permission alert dialog.
     * @param state True to show, false to hide.
     */
    fun showCallAlertDialog(state: Boolean){ callPermissionAlertDialog = state }
    /**
     * Shows or hides the delete confirmation dialog.
     * @param state True to show, false to hide.
     */
    fun showDeleteConfirmationDialog(state: Boolean){ deleteConfirmationDialog = state }
    /**
     * Toggles the favorite state locally.
     */
    fun toggleFavorite() { isFavorite = !isFavorite }
    /**
     * Updates the applicant's favorite state in the repository asynchronously.
     */
    fun updateApplicantFavoriteState(){
        if (uiState.applicant is GetDataState.Success) {
            viewModelScope.launch {
//                val applicant = uiState.applicant.copy(isFavorite = isFavorite)
                val applicant = (uiState.applicant as GetDataState.Success<Applicant>).data.copy(isFavorite = isFavorite)
                applicantRepository.updateApplicant(applicant)
            }
        }
    }
    /**
     * Deletes the given applicant from the repository asynchronously.
     *
     * @param applicant The applicant to delete.
     */
    fun deleteApplicant(applicant: Applicant){
        viewModelScope.launch {
            logger.d( "DetailViewModel.delete(): $applicant")
            applicantRepository.deleteApplicant(applicant)
        }
    }

    init {
        viewModelScope.launch {
            loadExchangeRate()
            loadApplicant()
        }
    }

    /**
     * Loads the exchange rate for the current locale's currency, updating [uiState].
     * Handles exceptions by setting an error state.
     */
    private suspend fun loadExchangeRate() {
        val state =  try {
            when (Locale.getDefault().language){
                "fr" -> GetDataState.Success(getExchangeRate("eur"))
                "en" -> GetDataState.Success(getExchangeRate("gbp"))
                else -> GetDataState.Error("Unsupported language")
            }
        } catch (e: Exception) {
            logger.d("Api Error: ${e.message}")
            GetDataState.Error(e.message ?: "Unknown error")
        }
        uiState = uiState.copy(exchangeRate = state)
    }

    /**
     * Collects applicant data updates from the repository and updates [uiState].
     * Sets an error state on failure.
     */
    private suspend fun loadApplicant() {
        try {
//            delay(1000) // delay for dev purpose
            getApplicantById.distinctUntilChanged().collect() {
                uiState = uiState.copy(applicant = GetDataState.Success(it))
                isFavorite = it.isFavorite
            }
        } catch (e: Exception) {
            uiState = uiState.copy(applicant = GetDataState.Error(e.message ?: "Unknown error"))
        }
    }
}