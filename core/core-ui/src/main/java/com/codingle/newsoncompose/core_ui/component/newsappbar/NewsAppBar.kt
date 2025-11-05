package com.codingle.newsoncompose.core_ui.component.newsappbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.W600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    title: String,
    actions: @Composable RowScope.() -> Unit = {}
) = TopAppBar(
    windowInsets = windowInsets,
    title = {
        Column {
            Text(
                title,
                style = typography.titleMedium.copy(fontWeight = W600),
                color = colorScheme.onBackground,
                maxLines = 1
            )
        }
    },
    actions = actions,
    colors = TopAppBarColors(
        containerColor = colorScheme.background,
        actionIconContentColor = colorScheme.onBackground,
        titleContentColor = colorScheme.onBackground,
        scrolledContainerColor = colorScheme.background,
        navigationIconContentColor = colorScheme.onBackground
    )
)