package com.codingle.newsoncompose.core_ui.component.reload

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.codingle.core_ui.R
import com.codingle.newsoncompose.core_ui.component.util.noRippleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReloadState(
    modifier: Modifier,
    onReload: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val context = LocalContext.current
    CompositionLocalProvider(
        values = arrayOf(
            LocalMinimumInteractiveComponentEnforcement provides false,
            LocalRippleConfiguration provides noRippleTheme()
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceDim, RoundedCornerShape(10.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onReload
                ),
            contentAlignment = Center
        ) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp, 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, CenterHorizontally),
                verticalAlignment = CenterVertically
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .allowHardware(true)
                        .data(R.drawable.ic_reload)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                )

                Text(
                    text = "Reload",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}