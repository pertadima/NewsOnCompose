package com.codingle.newsoncompose.screen.main

import android.content.Context
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.core_data.data.navigation.Home
import com.codingle.newsoncompose.core_ui.component.navigationbar.NavigationBar
import com.codingle.newsoncompose.core_ui.component.navigationbar.NavigationItem
import com.codingle.newsoncompose.screen.home.HomeRoute

@Composable
fun MainRoute(navController: NavHostController, modifier: Modifier) {
    MainScreen(modifier = modifier, navController)
}

@Composable
fun MainScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val tabNavController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(modifier, createNavigationMenu(context)) {
                tabNavController.navigate(it.route)
            }
        }
    ) { innerPadding ->
        val graph = tabNavController.createGraph(startDestination = Home) {
            composable<Home> { HomeRoute(navController, modifier) }
        }

        NavHost(
            navController = tabNavController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

private fun createNavigationMenu(context: Context) = listOf(
    NavigationItem(
        title = context.getString(R.string.navigation_home),
        icon = Default.Home,
        route = Home
    ),
    NavigationItem(
        title = context.getString(R.string.navigation_favorite),
        icon = Default.Favorite,
        route = Home
    ),
    NavigationItem(
        title = context.getString(R.string.navigation_profile),
        icon = Default.Person,
        route = Home
    )
)