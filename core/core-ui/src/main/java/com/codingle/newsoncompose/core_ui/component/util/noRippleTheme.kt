package com.codingle.newsoncompose.core_ui.component.util

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
internal fun noRippleTheme() = RippleConfiguration(
    color = Color.Unspecified,
    rippleAlpha = RippleAlpha(
        focusedAlpha = 0F,
        draggedAlpha = 0F,
        hoveredAlpha = 0F,
        pressedAlpha = 0F
    )
)