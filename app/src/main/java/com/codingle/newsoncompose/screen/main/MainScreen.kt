package com.codingle.newsoncompose.screen.main

import android.content.Context
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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
import com.codingle.newsoncompose.core_data.data.navigation.MainTabDestination
import com.codingle.newsoncompose.core_data.data.navigation.Setting
import com.codingle.newsoncompose.core_ui.component.navigationbar.NavigationBar
import com.codingle.newsoncompose.core_ui.component.navigationbar.NavigationItem
import com.codingle.newsoncompose.screen.home.HomeRoute
import com.codingle.newsoncompose.screen.setting.SettingRoute

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
            composable<Setting> { SettingRoute(navController, modifier) }
        }

        NavHost(
            navController = tabNavController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

private fun createNavigationMenu(context: Context): List<NavigationItem<MainTabDestination>> =
    listOf(
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
            title = context.getString(R.string.navigation_setting),
            icon = Default.Settings,
            route = Setting
        )
    )