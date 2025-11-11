package com.example.courseapp.domain

sealed class FetchedResult <T>(
    val data: T? = null, val errorMessage: String? = null
) {
    class Success<T>(fetchedData: T): FetchedResult<T>(fetchedData)
    class Error<T>(message: String): FetchedResult<T>(errorMessage = message)
}