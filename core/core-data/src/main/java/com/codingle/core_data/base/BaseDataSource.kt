package com.codingle.core_data.base

import com.codingle.core_data.data.entity.ApiResult
import com.codingle.core_data.data.entity.ApiResult.Error
import com.codingle.core_data.data.entity.ApiResult.Success
import com.codingle.core_data.data.entity.ErrorData
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> getResultWithSingleObject(call: suspend () -> Response<T>): ApiResult<T> =
        try {
            val response = call()
            if (response.isSuccessful) Success(response.body())
            else Error(ErrorData(response.message(), response.code()))
        } catch (e: Exception) {
            Error(ErrorData(e.localizedMessage.orEmpty()))
        }
}