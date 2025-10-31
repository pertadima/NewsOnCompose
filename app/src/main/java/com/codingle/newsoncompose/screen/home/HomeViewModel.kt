package com.codingle.newsoncompose.screen.home

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.api_headlines.domain.get.GetHeadlineUseCase
import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.api_sources.domain.get.GetSourcesUseCase
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateInitial
import com.codingle.newsoncompose.core_data.base.BaseState.StateLoading
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHeadlineUseCase: GetHeadlineUseCase,
    private val getSourcesUseCase: GetSourcesUseCase,
) : BaseViewModel() {

    private val _sourcesState: MutableStateFlow<BaseState<List<SourceDto>>> = MutableStateFlow(StateInitial)
    val sourcesState = _sourcesState.asStateFlow()

    private val _headlineState: MutableStateFlow<BaseState<List<HeadlineArticleDto>>> = MutableStateFlow(StateInitial)
    val headlineState = _headlineState.asStateFlow()

    private val _selectedTabPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTabPosition = _selectedTabPosition.asStateFlow()

    private var headlineJob: Job? = null

    init {
        getSources()
        getHeadlines()
    }

    fun getSources() = collectFlow(
        getSourcesUseCase(),
        onSuccess = { _sourcesState.value = StateSuccess(it) },
        onLoading = { _sourcesState.value = StateLoading },
        onError = { _sourcesState.value = StateFailed(it) }
    )

    fun getHeadlines(source: String = "") {
        headlineJob?.cancel()
        headlineJob = collectFlow(
            getHeadlineUseCase(source),
            onSuccess = { _headlineState.value = StateSuccess(it) },
            onLoading = { _headlineState.value = StateLoading },
            onError = { _headlineState.value = StateFailed(it) }
        )
    }

    fun updateSelectedTabPosition(position: Int) = _selectedTabPosition.update { position }
}