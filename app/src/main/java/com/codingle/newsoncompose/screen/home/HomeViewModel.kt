package com.codingle.newsoncompose.screen.home

import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.api_sources.domain.get.GetSourcesUseCase
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
class HomeViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase
) : BaseViewModel() {

    private val _sourcesState: MutableStateFlow<BaseState<List<SourceDto>>> = MutableStateFlow(StateInitial)
    val sourcesState = _sourcesState.asStateFlow()

    fun getSources() = collectFlow(
        getSourcesUseCase(),
        onSuccess = { _sourcesState.value = StateSuccess(it) },
        onLoading = { _sourcesState.value = StateLoading },
        onError = { _sourcesState.value = StateFailed(it) }
    )
}