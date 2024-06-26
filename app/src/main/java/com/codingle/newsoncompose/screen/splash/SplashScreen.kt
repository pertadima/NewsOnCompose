package com.codingle.newsoncompose.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest.Builder
import com.codingle.core_data.data.navigation.Home
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.screen.splash.SplashScreenAttr.LOGO_CONTENT_DESCRIPTION
import com.codingle.newsoncompose.screen.splash.SplashScreenAttr.SPLASH_TIME
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(navController: NavHostController) {
    SplashScreen { navController.navigate(Home) }
}

@Composable
fun SplashScreen(onSplashFinish: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        delay(SPLASH_TIME)
        onSplashFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)

    ) {
        SubcomposeAsyncImage(
            model = Builder(context)
                .allowHardware(true)
                .data(R.drawable.newspaper_logo)
                .build(),
            contentDescription = LOGO_CONTENT_DESCRIPTION,
            modifier = Modifier
                .align(Center)
                .width(90.dp)
                .height(90.dp)
        )
    }
}


@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen {

    }
}