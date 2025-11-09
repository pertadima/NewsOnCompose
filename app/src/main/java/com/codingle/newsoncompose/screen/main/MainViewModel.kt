package com.codingle.newsoncompose.screen.main

import com.codingle.newsoncompose.api_settings.domain.get.GetIsDarkModeUseCase
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateInitial
import com.codingle.newsoncompose.core_data.base.BaseState.StateLoading
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsDarkModeUseCase: GetIsDarkModeUseCase,
) : BaseViewModel() {
    private val _isDarkMode: MutableStateFlow<BaseState<Boolean>> =
        MutableStateFlow(StateInitial)
    val isDarkMode = _isDarkMode.asStateFlow()

    init {
        getDarkMode()
    }

    fun getDarkMode() = collectFlow(
        getIsDarkModeUseCase(),
        onSuccess = { _isDarkMode.value = StateSuccess(it) },
        onLoading = { _isDarkMode.value = StateLoading },
        onError = { _isDarkMode.value = StateFailed(it) }
    )
}