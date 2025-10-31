package com.codingle.newsoncompose.screen.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.headline.EmptyHeadline
import com.codingle.newsoncompose.core_ui.component.headline.HorizontalHeadlineItem
import com.codingle.newsoncompose.core_ui.component.headline.VerticalHeadlineItem
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState
import com.codingle.newsoncompose.core_ui.component.shimmer.shimmer
import com.codingle.newsoncompose.screen.home.HomeScreenAttr.HEADER_SIZE
import com.codingle.newsoncompose.screen.home.HomeScreenAttr.LOADING_PLACEHOLDER_SIZE


@Composable
internal fun HeadlineSection(
    headlines: BaseState<List<HeadlineArticleDto>>,
    onReload: () -> Unit,
    onNewsClicked: (HeadlineArticleDto) -> Unit
) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
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

        when (headlines) {
            is StateFailed -> {
                item {
                    ReloadState(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(134.dp),
                        onReload = { onReload() }
                    )
                }
            }

            is StateSuccess -> {
                if (headlines.data.isNullOrEmpty()) {
                    item {
                        EmptyHeadline(
                            RawRes(R.raw.not_found),
                            context.getString(R.string.headlines_no_result)
                        )
                    }
                } else {
                    successHeadlineSection(data = headlines.data.orEmpty(), onNewsClicked = onNewsClicked)
                }
            }

            else -> {
                item {
                    LoadingHeadlineSection()
                }
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
        items(LOADING_PLACEHOLDER_SIZE) {
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

private fun LazyListScope.successHeadlineSection(
    data: List<HeadlineArticleDto>,
    onNewsClicked: (HeadlineArticleDto) -> Unit
) {
    val headerData = data.take(HEADER_SIZE)
    val bottomData = data.drop(HEADER_SIZE)

    item {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = spacedBy(16.dp)
        ) {
            items(
                items = headerData,
                key = { it.url.ifEmpty { it.title } }
            ) {
                HorizontalHeadlineItem(
                    title = it.title,
                    urlToImage = it.urlToImage
                ) { onNewsClicked(it) }
            }
        }
    }

    item {
        Spacer(modifier = Modifier.height(25.dp))
    }

    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 16.dp)
                .background(colorScheme.onBackground)
        )
    }

    itemsIndexed(
        items = bottomData,
        key = { _, item -> item.url.ifEmpty { item.title } }
    ) { index, article ->
        VerticalHeadlineItem(
            index = index,
            title = article.title,
            source = article.source,
            publishedAt = article.publishedAt,
            urlToImage = article.urlToImage
        ) { onNewsClicked(article) }
    }
}