package com.codingle.newsoncompose.core_data.base

import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Error
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import com.codingle.newsoncompose.core_data.data.entity.ErrorData
import retrofit2.Response

open class BaseDataSource {
    suspend fun <T> getResultWithSingleObject(call: suspend () -> Response<T>) = try {
        val response = call()
        if (response.isSuccessful) Success(response.body(), true)
        else Error(ErrorData(response.message(), response.code()))
    } catch (e: Exception) {
        Error(ErrorData(e.localizedMessage.orEmpty()))
    }

    suspend fun <T> getLocalDataWithSingleObject(call: suspend () -> T) = try {
        val data = call()
        Success(data, false)
    } catch (e: Exception) {
        Error(ErrorData(e.localizedMessage.orEmpty()))
    }
}