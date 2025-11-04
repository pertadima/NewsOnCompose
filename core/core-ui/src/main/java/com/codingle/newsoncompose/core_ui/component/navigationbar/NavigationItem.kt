package com.codingle.newsoncompose.core_ui.component.navigationbar

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem<T>(
    val title: String,
    val icon: ImageVector,
    val route: T
)