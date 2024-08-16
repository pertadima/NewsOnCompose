package com.codingle.newsoncompose.core_ui.component.headline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun HorizontalHeadlineItem(
    title: String,
    urlToImage: String,
    onNewsClicked: () -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onNewsClicked() }
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .allowHardware(true)
                .data(urlToImage)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .width(140.dp)
                .height(82.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W600),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(140.dp)
        )
    }
}