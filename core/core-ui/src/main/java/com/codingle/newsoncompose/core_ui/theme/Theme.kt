package com.codingle.newsoncompose.core_ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.codingle.newsoncompose.core_ui.theme.Amber.Amber100
import com.codingle.newsoncompose.core_ui.theme.Amber.Amber500
import com.codingle.newsoncompose.core_ui.theme.Amber.Amber800
import com.codingle.newsoncompose.core_ui.theme.BnW.Black
import com.codingle.newsoncompose.core_ui.theme.BnW.White
import com.codingle.newsoncompose.core_ui.theme.Error.ErrorContainerRed
import com.codingle.newsoncompose.core_ui.theme.Error.ErrorRed
import com.codingle.newsoncompose.core_ui.theme.Gray.Gray100
import com.codingle.newsoncompose.core_ui.theme.Gray.Gray300
import com.codingle.newsoncompose.core_ui.theme.Gray.Gray400
import com.codingle.newsoncompose.core_ui.theme.Gray.Gray600
import com.codingle.newsoncompose.core_ui.theme.Gray.Gray700
import com.codingle.newsoncompose.core_ui.theme.Gray.Gray800
import com.codingle.newsoncompose.core_ui.theme.Green.Green100
import com.codingle.newsoncompose.core_ui.theme.Green.Green500
import com.codingle.newsoncompose.core_ui.theme.Green.Green800
import com.codingle.newsoncompose.core_ui.theme.Red.Red100
import com.codingle.newsoncompose.core_ui.theme.Red.Red200
import com.codingle.newsoncompose.core_ui.theme.Red.Red700
import com.codingle.newsoncompose.core_ui.theme.Red.Red900

val DarkColorScheme = darkColorScheme(
    primary = Red200,
    onPrimary = Black,
    primaryContainer = Red900,
    onPrimaryContainer = Red100,
    inversePrimary = Red700,

    secondary = Amber500,
    onSecondary = Black,
    secondaryContainer = Amber800,
    onSecondaryContainer = Amber100,

    tertiary = Green500,
    onTertiary = Black,
    tertiaryContainer = Green800,
    onTertiaryContainer = Green100,

    background = Gray800,
    onBackground = White,
    surface = Gray700,
    onSurface = White,
    surfaceVariant = Gray600,
    onSurfaceVariant = Gray100,
    surfaceTint = Red200,

    inverseSurface = White,
    inverseOnSurface = Black,

    error = ErrorRed,
    onError = White,
    errorContainer = Red900,
    onErrorContainer = Red100,

    outline = Gray400,
    outlineVariant = Gray600,
    scrim = Black,

    surfaceBright = Gray700,
    surfaceDim = Gray800,
    surfaceContainer = Gray700,
    surfaceContainerHigh = Gray600,
    surfaceContainerHighest = Gray400,
    surfaceContainerLow = Gray800,
    surfaceContainerLowest = Gray800
)

val LightColorScheme = lightColorScheme(
    primary = Red700,
    onPrimary = White,
    primaryContainer = Red100,
    onPrimaryContainer = Red900,
    inversePrimary = Red200,
    secondary = Amber500,
    onSecondary = Black,
    secondaryContainer = Amber100,
    onSecondaryContainer = Amber800,
    tertiary = Green500,
    onTertiary = White,
    tertiaryContainer = Green100,
    onTertiaryContainer = Green800,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = Red100,
    onSurfaceVariant = Red700,
    surfaceTint = Red700,
    inverseSurface = Gray800,
    inverseOnSurface = White,
    error = ErrorRed,
    onError = White,
    errorContainer = ErrorContainerRed,
    onErrorContainer = Red900,
    outline = Gray400,
    outlineVariant = Gray300,
    scrim = Black,
    surfaceBright = White,
    surfaceDim = Gray100,
    surfaceContainer = Gray100,
    surfaceContainerHigh = Gray300,
    surfaceContainerHighest = Gray400,
    surfaceContainerLow = Gray100,
    surfaceContainerLowest = White
)

@Composable
fun NewsOnComposeTheme(
    darkTheme: Boolean,
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