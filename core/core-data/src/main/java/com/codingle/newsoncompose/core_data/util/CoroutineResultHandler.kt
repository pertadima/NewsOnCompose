package com.codingle.newsoncompose.core_data.util

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Error
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Loading
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import com.codingle.newsoncompose.core_data.data.entity.ErrorData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object CoroutineResultHandler {
    fun <T> remoteResultFlow(networkCall: suspend () -> ApiResult<T>) = flow {
        emit(Loading)
        emit(callRemote(networkCall))
    }.flowOn(IO)

    fun <T> localApiResultFlow(localCall: suspend () -> ApiResult<T>) = flow {
        emit(Loading)
        emit(
            when (val localData = localCall()) {
                is Error -> Error(localData.error)
                is Loading -> Loading
                is Success -> Success(localData.data, false)
            }
        )
    }.flowOn(IO)

    fun <A, B> resultFlow(
        localCall: suspend () -> ApiResult<A>,
        networkCall: suspend () -> ApiResult<B>
    ) = flow {
        emit(Loading)
        emit(
            when (val localData = localCall()) {
                is Error -> Error(localData.error)
                is Loading -> Loading
                is Success -> {
                    if (localData.data == null || (localData.data is List<*> && localData.data.isEmpty())) {
                        callRemote(networkCall)
                    } else Success(localData.data, false)
                }
            }
        )
    }.flowOn(IO)

    private suspend fun <T> callRemote(
        networkCall: suspend () -> ApiResult<T>
    ): ApiResult<T> = when (val response = networkCall()) {
        is Error -> Error(response.error)
        is Loading -> Loading
        is Success -> response.data?.let { Success(it, true) } ?: Error(ErrorData())
    }
}