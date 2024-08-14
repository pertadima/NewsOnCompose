package com.codingle.newsoncompose.screen.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core.util.Util.openBrowser
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateInitial
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.headline.EmptyHeadline
import com.codingle.newsoncompose.core_ui.component.headline.VerticalHeadlineItem
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState
import com.codingle.newsoncompose.core_ui.component.shimmer.shimmer
import com.codingle.newsoncompose.screen.home.HomeViewModel
import com.codingle.newsoncompose.screen.splash.SplashScreenAttr.LOGO_CONTENT_DESCRIPTION


@Composable
internal fun HeadlineSection(
    viewModel: HomeViewModel = hiltViewModel(),
    isRefreshing: Boolean
) = with(viewModel) {
    val context = LocalContext.current
    val headlines = headlineState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { if (headlines is StateInitial) getHeadlines() }
    LaunchedEffect(isRefreshing) { if (isRefreshing) getHeadlines() }

    LazyColumn {
        item {
            Text(
                context.getString(R.string.headlines_today),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = W600),
                color = colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            when (headlines) {
                is StateFailed -> ReloadState(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(134.dp),
                    onReload = { getHeadlines() }
                )

                is StateSuccess -> {
                    if (headlines.data.isNullOrEmpty()) EmptyHeadline(
                        RawRes(R.raw.not_found),
                        context.getString(R.string.headlines_no_result)
                    )
                    else SuccessHeadlineSection(data = headlines.data.orEmpty())
                }

                else -> LoadingHeadlineSection()
            }
        }
    }
}

@Composable
private fun LoadingHeadlineSection() {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = spacedBy(16.dp)
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
                            .background(colorScheme.surfaceContainerHigh)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .shimmer()
                            .background(colorScheme.surfaceContainerHigh)
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessHeadlineSection(
    data: List<HeadlineArticleDto>,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val state = rememberLazyListState()
    val context = LocalContext.current
    val headerData = data.take(5)
    val bottomData = data.drop(5)

    Column {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = state,
            horizontalArrangement = spacedBy(16.dp)
        ) {
            headerData.take(5).forEach {
                item {
                    Column(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { context.openBrowser(it.url) }
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(context)
                                .allowHardware(true)
                                .data(it.urlToImage)
                                .build(),
                            contentDescription = LOGO_CONTENT_DESCRIPTION,
                            modifier = Modifier
                                .width(140.dp)
                                .height(82.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(colorScheme.surfaceContainerHigh)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            it.title,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = W600),
                            color = colorScheme.onBackground,
                            maxLines = 3,
                            overflow = Ellipsis,
                            modifier = Modifier.width(140.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 16.dp)
                .background(colorScheme.onBackground)
        )

        bottomData.forEach {
            VerticalHeadlineItem(
                title = it.title,
                source = it.source,
                publishedAt = it.publishedAt,
                urlToImage = it.urlToImage
            ) { context.openBrowser(it.url) }
        }
    }
}