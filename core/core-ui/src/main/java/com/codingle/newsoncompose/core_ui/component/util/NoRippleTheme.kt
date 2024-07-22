package com.codingle.newsoncompose.core_ui.component.util

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object NoRippleTheme : RippleTheme {
    private val alpha = RippleAlpha(
        focusedAlpha = 0F,
        draggedAlpha = 0F,
        hoveredAlpha = 0F,
        pressedAlpha = 0F
    )

    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha() = alpha
}