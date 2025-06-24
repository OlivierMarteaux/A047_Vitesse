package com.example.vitesse.ui.screens.common

sealed interface GetDataState<out T> {
    data object Loading : GetDataState<Nothing>
    data class Success<T>(val data: T) : GetDataState<T>
    data class Error(val errorMessage: String) : GetDataState<Nothing>
}