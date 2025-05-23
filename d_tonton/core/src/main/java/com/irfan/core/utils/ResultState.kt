package com.irfan.core.utils

sealed class ResultState<out T> private constructor() {
    data object Initial : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data class NoData(val data: SingleEvent<Unit>) : ResultState<Nothing>()
    data class HasData<out T>(val data: T) : ResultState<T>()
    data class Error(val message: SingleEvent<String>) : ResultState<Nothing>()
}