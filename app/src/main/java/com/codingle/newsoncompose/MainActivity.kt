package com.codingle.newsoncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.codingle.newsoncompose.core_data.data.navigation.Main
import com.codingle.newsoncompose.core_data.data.navigation.Search
import com.codingle.newsoncompose.core_data.data.navigation.SearchResult
import com.codingle.newsoncompose.core_data.data.navigation.Splash
import com.codingle.newsoncompose.core_data.data.navigation.WebView
import com.codingle.newsoncompose.core_ui.theme.NewsOnComposeTheme
import com.codingle.newsoncompose.screen.main.MainRoute
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
                MainScreen(modifier = Modifier)
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()


    NavHost(navController, startDestination = Splash) {
        composable<Splash> { SplashRoute(navController) }

        composable<Main> { MainRoute(navController, modifier) }

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