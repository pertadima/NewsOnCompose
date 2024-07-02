package com.codingle.newsoncompose.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingle.newsoncompose.api_sources.domain.get.GetSourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase
) : ViewModel() {

    fun getSources() = viewModelScope.launch {
        getSourcesUseCase().distinctUntilChanged().collect {
            Log.e("TAG", "getSources: ${it}", )
        }
    }
}