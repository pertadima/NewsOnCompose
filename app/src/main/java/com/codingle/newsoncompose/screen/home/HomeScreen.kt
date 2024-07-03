package com.codingle.newsoncompose.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess

@Composable
fun HomeRoute(navController: NavHostController, modifier: Modifier) {
    HomeScreen(modifier = modifier)
    Log.e("TAG", "HomeRoute: $navController")
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier
) = with(viewModel) {
    val sources = viewModel.sourcesState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { getSources() }
    Column(modifier = modifier.fillMaxSize()) {
        when (sources) {
            is StateFailed -> Unit
            is StateSuccess -> sources.data?.forEach { Text(text = it.name.orEmpty()) }
            else -> Unit
        }
    }
}