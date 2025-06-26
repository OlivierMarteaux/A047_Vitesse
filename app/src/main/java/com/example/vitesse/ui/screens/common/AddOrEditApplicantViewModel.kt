package com.example.vitesse.ui.screens.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vitesse.data.model.Applicant
import extensions.isValidEmail

/**
 * UI state data class for adding or editing an applicant.
 *
 * @property applicant The current state of the applicant data, wrapped in [GetDataState].
 * @property isSaveable Indicates whether the current applicant data is valid and can be saved.
 */
@RequiresApi(Build.VERSION_CODES.O)
data class AddOrEditApplicantUiState(
    val applicant: GetDataState<Applicant> = GetDataState.Loading,
    val isSaveable: Boolean = false
)

/**
 * Abstract ViewModel base class for managing the state of adding or editing an applicant.
 *
 * Holds the UI state and provides logic to update the state based on input validation.
 */
@RequiresApi(Build.VERSION_CODES.O)
abstract class AddOrEditApplicantViewModel : ViewModel() {

    /**
     * The current UI state, exposed as a mutable state to enable Compose reactivity.
     */
    var uiState by mutableStateOf(AddOrEditApplicantUiState())
        protected set

    /**
     * Updates the [uiState] with the provided [applicant] data and computes
     * if the form is saveable based on validation rules:
     * - first name, last name, phone, email are not blank
     * - email is valid format
     * - birth date is not null
     *
     * @param applicant The current applicant data entered by the user.
     */
    fun updateUiState(applicant: Applicant) {
        uiState = uiState.copy(
            applicant = GetDataState.Success(applicant),
            isSaveable = with(applicant) {
                firstName.isNotBlank() &&
                        lastName.isNotBlank() &&
                        phone.isNotBlank() &&
                        email.isNotBlank() &&
                        email.isValidEmail() &&
                        birthDate != null
            }
        )
    }
}