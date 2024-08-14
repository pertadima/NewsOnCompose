package com.codingle.newsoncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.codingle.newsoncompose.core_data.data.navigation.Home
import com.codingle.newsoncompose.core_data.data.navigation.Search
import com.codingle.newsoncompose.core_data.data.navigation.SearchResult
import com.codingle.newsoncompose.core_data.data.navigation.Splash
import com.codingle.newsoncompose.core_data.data.navigation.WebView
import com.codingle.newsoncompose.core_ui.theme.NewsOnComposeTheme
import com.codingle.newsoncompose.screen.home.HomeRoute
import com.codingle.newsoncompose.screen.search.SearchRoute
import com.codingle.newsoncompose.screen.searchresult.SearchResultRoute
import com.codingle.newsoncompose.screen.splash.SplashRoute
import com.codingle.newsoncompose.screen.webview.WebViewRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsOnComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Splash) {
        composable<Splash> { SplashRoute(navController) }

        composable<Home> { HomeRoute(navController, modifier) }

        composable<Search> { SearchRoute(navController, modifier) }

        composable<SearchResult> {
            val searchResult: SearchResult = it.toRoute()
            SearchResultRoute(navController, modifier, searchResult.keyword)
        }

        composable<WebView> {
            val webView: WebView = it.toRoute()
            WebViewRoute(navController, modifier, webView.title, webView.url)
        }
    }
}