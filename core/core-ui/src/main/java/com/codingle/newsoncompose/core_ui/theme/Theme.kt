package com.codingle.newsoncompose.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.codingle.newsoncompose.core_ui.theme.Red.Red200
import com.codingle.newsoncompose.core_ui.theme.Red.Red700

val DarkColorScheme = darkColorScheme(
    primary = Red700,
    secondary = Red200,
    tertiary = Pink80
)

val LightColorScheme = lightColorScheme(
    primary = Red700,
    secondary = Red200,
    tertiary = Pink40
)

@Composable
fun NewsOnComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}