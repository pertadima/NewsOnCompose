package com.codingle.newsoncompose.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.data.navigation.WebView
import com.codingle.newsoncompose.core_ui.component.headline.EmptyHeadline
import com.codingle.newsoncompose.core_ui.component.headline.VerticalHeadlineItem
import com.codingle.newsoncompose.core_ui.component.newsappbar.NewsAppBar


@Composable
fun FavoriteRoute(navController: NavHostController, modifier: Modifier) {
    FavoriteScreen(
        modifier = modifier,
        onNewsClicked = { navController.navigate(WebView(it.title, it.url, it.isFavorite)) }
    )
}

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    modifier: Modifier,
    onNewsClicked: (HeadlineArticleDto) -> Unit
) = with(viewModel) {
    val context = LocalContext.current
    val headlinesState = headlineState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { getFavoriteHeadlines() }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NewsAppBar(title = context.getString(R.string.app_name))
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            when (headlinesState) {

                is StateSuccess -> if (headlinesState.data.isNullOrEmpty()) {
                    item {
                        EmptyHeadline(
                            RawRes(R.raw.not_found),
                            context.getString(R.string.headlines_no_result)
                        )
                    }
                } else {
                    itemsIndexed(
                        items = headlinesState.data.orEmpty(),
                        key = { _, item -> item.title }
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

                else -> item {
                    EmptyHeadline(
                        RawRes(R.raw.not_found),
                        context.getString(R.string.headlines_no_result)
                    )
                }
            }
        }
    }
}