package com.codingle.newsoncompose.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.codingle.newsoncompose.screen.home.section.SourceSection

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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SourceSection(sources)
    }
}