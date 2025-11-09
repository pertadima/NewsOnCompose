package com.codingle.newsoncompose.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.newsappbar.NewsAppBar

@Composable
fun SettingRoute(modifier: Modifier) {
    SettingScreen(modifier = modifier)
}

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    modifier: Modifier
) = with(viewModel) {
    val context = LocalContext.current
    val isDarkMode = isDarkMode.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NewsAppBar(title = context.getString(R.string.app_name))

        Text(
            context.getString(R.string.setting_preference),
            style = typography.titleMedium.copy(fontWeight = W600),
            color = colorScheme.onBackground,
            modifier = modifier
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            maxLines = 1
        )

        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = SpaceBetween,
            modifier = modifier
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            if (isDarkMode is StateSuccess) {
                Text(
                    context.getString(R.string.setting_dark_mode),
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground,
                    maxLines = 1
                )
                Switch(
                    checked = isDarkMode.data ?: false,
                    onCheckedChange = { updateDarkMode(!(isDarkMode.data ?: false)) },
                    colors = colors(
                        checkedThumbColor = colorScheme.primary,
                        uncheckedThumbColor = colorScheme.onSurfaceVariant,
                        checkedTrackColor = colorScheme.primary.copy(alpha = 0.54f),
                        uncheckedTrackColor = colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 16.dp)
                .background(colorScheme.surfaceDim)
        )
    }
}