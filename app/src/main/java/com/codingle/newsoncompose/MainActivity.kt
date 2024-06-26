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
import com.codingle.core_data.data.navigation.Home
import com.codingle.core_data.data.navigation.Splash
import com.codingle.newsoncompose.screen.home.HomeScreen
import com.codingle.newsoncompose.screen.splash.SplashScreen
import com.codingle.newsoncompose.ui.theme.NewsOnComposeTheme

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
        composable<Splash> { SplashScreen() }

        composable<Home> { HomeScreen(modifier) }
    }
}