package com.codingle.newsoncompose.core_ui.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import com.codingle.newsoncompose.core_ui.component.util.noRippleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(
    name: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        CompositionLocalProvider(LocalRippleConfiguration provides noRippleTheme()) {
            OutlinedButton(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.surfaceVariant,
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(18.dp, 0.dp),
                interactionSource = interactionSource,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = name,
                    color = if (selected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = W600)
                )
            }
        }
    }
}