package com.example.vitesse.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean? = null,
)
class HomeViewModel(private val applicantRepository: ApplicantRepository): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    var query: String by mutableStateOf("")
        private set

    fun updateQuery(query: String) {
        this.query = query
    }

    fun getApplicants(query: String = ""): Flow<List<Applicant>> = with(applicantRepository) {
        if (query.isEmpty()) getAllApplicants() else getApplicants(query)
//        getApplicants(query)
    }

    init {
        viewModelScope.launch {
            getApplicants().collect { applicants ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isEmpty = applicants.isEmpty(),
                    )
                }
            }
        }
    }
}