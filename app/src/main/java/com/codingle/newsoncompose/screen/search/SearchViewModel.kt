package com.codingle.newsoncompose.screen.search

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.api_search.domain.get.GetKeywordsUseCase
import com.codingle.newsoncompose.api_search.domain.save.SaveKeywordUseCase
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
class SearchViewModel @Inject constructor(
    private val getKeywordsUseCase: GetKeywordsUseCase,
    private val saveKeywordUseCase: SaveKeywordUseCase,
) : BaseViewModel() {
    private val _keywordsState: MutableStateFlow<BaseState<List<SearchKeywordDto>>> =
        MutableStateFlow(StateInitial)
    val keywordsState = _keywordsState.asStateFlow()

    fun insertKeyword(keyword: String) = collectFlow(
        saveKeywordUseCase(keyword),
        onSuccess = {

        }
    )

    fun getKeywords() = collectFlow(
        getKeywordsUseCase(),
        onLoading = { _keywordsState.value = StateLoading },
        onSuccess = { _keywordsState.value = StateSuccess(it) },
        onError = { _keywordsState.value = StateFailed(it) }
    )
}