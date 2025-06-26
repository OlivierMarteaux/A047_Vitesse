package com.example.vitesse.ui.screens.common

/**
 * A sealed interface representing the state of data retrieval operations,
 * encapsulating loading, success, and error states.
 *
 * @param T The type of the successful data.
 */
sealed interface GetDataState<out T> {
    /**
     * Represents the loading state, indicating that data is being fetched or processed.
     */
    data object Loading : GetDataState<Nothing>
    /**
     * Represents a successful data retrieval state.
     *
     * @property data The successfully retrieved data of type [T].
     */
    data class Success<T>(val data: T) : GetDataState<T>
    /**
     * Represents an error state when data retrieval fails.
     *
     * @property errorMessage A descriptive message explaining the error.
     */
    data class Error(val errorMessage: String) : GetDataState<Nothing>
}