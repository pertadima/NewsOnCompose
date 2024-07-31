package com.codingle.newsoncompose.screen.home.section

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState
import com.codingle.newsoncompose.core_ui.component.shimmer.shimmer
import com.codingle.newsoncompose.screen.splash.SplashScreenAttr

@Composable
internal fun HeadlineSection(
    sources: BaseState<List<HeadlineArticleDto>>,
    onReload: () -> Unit
) = Column {
    val context = LocalContext.current

    Text(
        context.getString(R.string.headlines_today),
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W600),
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 1,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(
        visible = sources is StateSuccess,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        if (sources is StateSuccess) SuccessHeadlineSection(sources.data.orEmpty())
    }

    when (sources) {
        is StateFailed -> ReloadState(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(134.dp),
            onReload = onReload
        )

        is StateSuccess -> Unit
        else -> LoadingHeadlineSection()
    }

    Spacer(modifier = Modifier.height(25.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
private fun LoadingHeadlineSection() {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in 0..5) {
            item {
                Column {
                    Box(
                        modifier = Modifier
                            .height(82.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .shimmer()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .shimmer()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessHeadlineSection(data: List<HeadlineArticleDto>) {
    val state = rememberLazyListState()
    val context = LocalContext.current

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        data.forEach {
            item {
                Column {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(context)
                            .allowHardware(true)
                            .data(it.urlToImage)
                            .build(),
                        contentDescription = SplashScreenAttr.LOGO_CONTENT_DESCRIPTION,
                        modifier = Modifier
                            .width(140.dp)
                            .height(82.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        it.title,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W600),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 3,
                        overflow = Ellipsis,
                        modifier = Modifier.width(140.dp)
                    )
                }
            }
        }
    }
}