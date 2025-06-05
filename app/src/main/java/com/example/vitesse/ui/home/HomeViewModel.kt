package com.example.vitesse.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.model.Applicant
import com.example.vitesse.data.repository.ApplicantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val allApplicantList: List<Applicant> = listOf(),
    val isLoading: Boolean = true,
    val isEmpty: Boolean? = null,
    val searchQuery: String = "",
)
class HomeViewModel(private val applicantRepository: ApplicantRepository): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val getAllApplicants = applicantRepository.getAllApplicants()

    init {
        viewModelScope.launch {
            getAllApplicants.collect { allApplicants ->
                _uiState.update {
                    it.copy(
                        allApplicantList = allApplicants,
                        isLoading = false,
                        isEmpty = allApplicants.isEmpty(),
                    )
                }
            }
        }
    }
}