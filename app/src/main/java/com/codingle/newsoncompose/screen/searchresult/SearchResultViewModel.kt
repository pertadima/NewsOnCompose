package com.codingle.newsoncompose.screen.searchresult

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.api_headlines.domain.search.SearchHeadlineUseCase
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateLoading
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchHeadlineUseCase: SearchHeadlineUseCase
) : BaseViewModel() {

    private val _headlineState: MutableStateFlow<BaseState<List<HeadlineArticleDto>>> =
        MutableStateFlow(BaseState.StateInitial)
    val headlineState = _headlineState.asStateFlow()

    fun searchHeadline(query: String) = collectFlow(
        searchHeadlineUseCase(query),
        onSuccess = { _headlineState.value = StateSuccess(it) },
        onLoading = { _headlineState.value = StateLoading },
        onError = { _headlineState.value = StateFailed(it) }
    )
}