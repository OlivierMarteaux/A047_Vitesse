package com.example.vitesse.data.model

//sealed class DatabaseState <out T> {
//    data class Success <out T> (val data: T) : DatabaseState<T>()
//    data class Error(val exception: Exception) : DatabaseState<Nothing>()
//    object Loading : DatabaseState<Nothing>()
//}