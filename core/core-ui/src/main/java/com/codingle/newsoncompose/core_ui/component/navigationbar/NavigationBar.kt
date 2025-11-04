package com.codingle.newsoncompose.core_ui.component.navigationbar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White

@Composable
fun <T> NavigationBar(
    modifier: Modifier,
    navigationItems: List<NavigationItem<T>>,
    onNavigationClicked: (NavigationItem<T>) -> Unit
) {
    val selectedNavigationIndex = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = White,
        modifier = modifier
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    onNavigationClicked(item)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        text = item.title,
                        color = when (index) {
                            selectedNavigationIndex.intValue -> colorScheme.onBackground
                            else -> colorScheme.outline
                        }
                    )
                },
                colors = colors(
                    selectedIconColor = colorScheme.surface,
                    indicatorColor = colorScheme.primary
                )
            )
        }
    }
}