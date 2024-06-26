package com.codingle.core_data.data.entity

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T?) : ApiResult<T>()
    data class Error(val error: ErrorData?) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}

data class ErrorData(
    val message: String,
    val httpCode: Int? = null
)