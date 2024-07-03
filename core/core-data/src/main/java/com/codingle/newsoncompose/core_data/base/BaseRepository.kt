package com.codingle.newsoncompose.core_data.base

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Error
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Loading
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import com.codingle.newsoncompose.core_data.data.entity.ErrorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class BaseRepository {
    private suspend fun <Plain, Entity> ApiResult<Plain>.transformResult(
        transformData: (Plain?) -> Entity,
        saveCallResult: (suspend (Entity, Boolean) -> Unit)? = null
    ) = try {
        val apiResult = when (this) {
            is Error -> Error(error)
            is Loading -> Loading
            is Success -> {
                saveCallResult?.invoke(transformData.invoke(data), isRemote)
                Success(transformData.invoke(data), isRemote)
            }
        }
        apiResult
    } catch (t: Throwable) {
        Error(ErrorData(t.localizedMessage.orEmpty()))
    }

    protected fun <Plain, Entity> Flow<ApiResult<Plain>>.mapToEntity(
        transformData: (Plain?) -> Entity,
        saveCallResult: (suspend (Entity, Boolean) -> Unit)? = null
    ) = this.map { it.transformResult(transformData, saveCallResult) }
}