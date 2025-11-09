package com.codingle.newsoncompose.screen.webview

import com.codingle.newsoncompose.api_headlines.domain.update.UpdateIsFavoriteHeadlineUseCase
import com.codingle.newsoncompose.api_settings.domain.update.UpdateIsDarkModeUseCase
import com.codingle.newsoncompose.core_data.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val updateIsFavoriteHeadlineUseCase: UpdateIsFavoriteHeadlineUseCase,
) : BaseViewModel() {

    fun updateIsFavoriteHeadline(isFavorite: Boolean, title: String) {
        collectFlow(
            updateIsFavoriteHeadlineUseCase(isFavorite, title),
            onSuccess = { },
            onLoading = { },
            onError = { }
        )
    }
}