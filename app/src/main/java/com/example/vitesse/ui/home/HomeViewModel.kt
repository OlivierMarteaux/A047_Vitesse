package com.example.vitesse.ui.home

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

//data class HomeUiState(
//    val isLoading: Boolean = true,
//    val isEmpty: Boolean? = null,
//    val isError: String? = null,
//)

sealed interface HomeUiState {
    data class Success(val applicants: List<Applicant>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
    data object Empty : HomeUiState
}


class HomeViewModel(private val applicantRepository: ApplicantRepository): ViewModel() {

//    private val _uiState = MutableStateFlow(HomeUiState())
//    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    var query: String by mutableStateOf("")
        private set

    fun updateQuery(query: String) {
        this.query = query
    }

    fun getApplicants(query: String = ""): Flow<List<Applicant>> = with(applicantRepository) {
        if (query.isEmpty()) getAllApplicants() else getApplicants(query)
//        getApplicants(query)
    }
//fun getApplicants(query: String = ""): Flow<List<Applicant>> =
////    with(applicantRepository) {
////    if (query.isEmpty()) getAllApplicants() else getApplicants(query)
////        getApplicants(query)
////}
//    when (val result = applicantRepository.getAllApplicants()) {
//        is DatabaseState.Success -> result.data
//        is DatabaseState.Error ->{ _uiState.update { it.copy(isError = result.exception.message ?: "Unknown error") };emptyFlow() }
//        is DatabaseState.Loading -> { _uiState.update { it.copy(isLoading = true) };emptyFlow() }
//    }

//    init {
//        viewModelScope.launch {
//            getApplicants().collect { applicants ->
//                _uiState.update {
//                    it.copy(
//                        isLoading = false,
//                        isEmpty = applicants.isEmpty(),
//                    )
//                }
//            }
//        }
//    }
    init {
        viewModelScope.launch {
            getApplicants().collect { applicants ->
                uiState = try {
                    HomeUiState.Loading
                    delay(1000) // delay for dev purpose
                    if (applicants.isEmpty()) HomeUiState.Empty else HomeUiState.Success(applicants)
                } catch (e: Exception) {
                    HomeUiState.Error
                }
            }
        }
    }
}