package com.codingle.newsoncompose.core_ui.component.headline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun VerticalHeadlineItem(
    title: String,
    source: String,
    urlToImage: String,
    publishedAt: String,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onItemClick() }
    ) {
        Row {
            if (urlToImage.isNotEmpty() && urlToImage.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .allowHardware(true)
                        .data(urlToImage)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorScheme.surfaceContainerHigh),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1F)) {
                Text(
                    source,
                    style = typography.labelSmall.copy(fontWeight = FontWeight.W600),
                    color = colorScheme.onBackground,
                    maxLines = 1,
                    overflow = Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    title,
                    style = typography.bodyMedium,
                    color = colorScheme.onBackground,
                    maxLines = 3,
                    overflow = Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    publishedAt,
                    style = typography.bodySmall,
                    color = colorScheme.inverseSurface,
                    maxLines = 3,
                    overflow = Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorScheme.surfaceDim)
        )
    }
}