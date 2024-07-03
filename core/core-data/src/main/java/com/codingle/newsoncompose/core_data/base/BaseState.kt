package com.codingle.newsoncompose.core_data.base

import com.codingle.newsoncompose.core_data.data.entity.ErrorData

sealed class BaseState<out T> {
    data object StateInitial : BaseState<Nothing>()
    data class StateSuccess<out T>(val data: T?) : BaseState<T>()
    data class StateFailed(val error: ErrorData?) : BaseState<Nothing>()
    data object StateLoading : BaseState<Nothing>()
}