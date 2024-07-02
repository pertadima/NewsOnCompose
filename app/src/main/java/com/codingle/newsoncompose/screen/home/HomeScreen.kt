package com.codingle.newsoncompose.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeRoute(navController: NavHostController, modifier: Modifier) {
    HomeScreen(modifier = modifier)
    Log.e("TAG", "HomeRoute: $navController")
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.getSources()
    }
    Box(modifier = modifier.fillMaxSize())
}