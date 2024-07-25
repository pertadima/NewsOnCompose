package com.codingle.newsoncompose.core_data.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Error
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Loading
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import com.codingle.newsoncompose.core_data.data.entity.ErrorData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    fun <T : Any> collectFlow(
        flow: Flow<ApiResult<T>>,
        onLoading: (() -> Unit)? = null,
        onError: ((ErrorData?) -> Unit)? = null,
        onSuccess: (T?) -> Unit
    ) = viewModelScope.launch(IO) {
        flow.distinctUntilChanged().collect {
            when (it) {
                is Error -> onError?.invoke(it.error)
                is Loading -> onLoading?.invoke()
                is Success -> onSuccess.invoke(it.data)
            }
        }
    }
}