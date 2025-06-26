package com.example.vitesse.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Represents the UI state of the Home screen.
 */
sealed interface HomeUiState {
    /** State representing successful data load with a list of applicants. */
    data class Success(val applicants: List<Applicant>) : HomeUiState
    /** State representing an error during data loading. */
    data object Error : HomeUiState
    /** State representing that data is currently loading. */
    data object Loading : HomeUiState
    /** State representing that there are no applicants to display. */
    data object Empty : HomeUiState
}

/**
 * ViewModel for the Home screen that manages the list of applicants and UI state.
 *
 * @property applicantRepository Repository for fetching applicants data.
 */
class HomeViewModel(private val applicantRepository: ApplicantRepository): ViewModel() {

    /** Current UI state exposed as a mutable state property. */
    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    /** Current search query for filtering applicants. */
    var query: String by mutableStateOf("")
        private set

    /**
     * Updates the current search query used for filtering applicants.
     *
     * @param query The new search query string.
     */
    fun updateQuery(query: String) { this.query = query }

    /**
     * Returns a [Flow] emitting lists of applicants filtered by the given query.
     *
     * @param query The search query string; empty string returns all applicants.
     * @return A [Flow] emitting lists of applicants matching the query.
     */
    fun getApplicants(query: String = ""): Flow<List<Applicant>> = with(applicantRepository) {
        if (query.isEmpty()) getAllApplicants() else getApplicants(query)
    }

    init {
        viewModelScope.launch {
            try {
                getApplicants().collect { applicants ->
                    delay(1000)
                    uiState = if (applicants.isEmpty()) HomeUiState.Empty else HomeUiState.Success(applicants)
                }
            } catch (e: Exception) {
                uiState = HomeUiState.Error
            }
        }
    }
}