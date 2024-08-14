package com.codingle.newsoncompose.screen.searchresult

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.data.navigation.Home
import com.codingle.newsoncompose.core_data.data.navigation.WebView
import com.codingle.newsoncompose.core_ui.component.headline.EmptyHeadline
import com.codingle.newsoncompose.core_ui.component.headline.VerticalHeadlineItem
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState

@Composable
fun SearchResultRoute(navController: NavHostController, modifier: Modifier, keyword: String) {
    SearchResultScreen(
        modifier = modifier,
        keyword = keyword,
        onNavigateBack = { navController.popBackStack(Home, false) },
        onNewsClicked = { navController.navigate(WebView(it.title, it.url)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    viewModel: SearchResultViewModel = hiltViewModel(),
    modifier: Modifier,
    keyword: String,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    onNavigateBack: () -> Unit,
    onNewsClicked: (HeadlineArticleDto) -> Unit
) = with(viewModel) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val headlines = viewModel.headlineState.collectAsStateWithLifecycle().value

    LaunchedEffect(keyword) { viewModel.searchHeadline(keyword) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        TopAppBar(
            windowInsets = windowInsets,
            title = {
                Text(
                    context.getString(R.string.searchresult_title, keyword),
                    style = typography.titleMedium.copy(fontWeight = FontWeight.W600),
                    color = colorScheme.onBackground,
                    maxLines = 1
                )
            },
            colors = TopAppBarColors(
                containerColor = colorScheme.background,
                actionIconContentColor = colorScheme.onBackground,
                titleContentColor = colorScheme.onBackground,
                scrolledContainerColor = colorScheme.background,
                navigationIconContentColor = colorScheme.onBackground
            ),
            navigationIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onNavigateBack() }
                        .padding(horizontal = 16.dp)
                )
            }
        )

        when (headlines) {
            is StateSuccess -> {
                if (headlines.data.orEmpty().isEmpty()) {
                    EmptyHeadline(RawRes(R.raw.not_found), context.getString(R.string.headlines_no_result))
                } else SuccessHeadlineSection(headlines.data.orEmpty(), onNewsClicked)
            }

            is StateFailed -> ReloadState(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(134.dp),
                onReload = { searchHeadline(keyword) }
            )

            else -> {

            }
        }
    }
}

@Composable
private fun SuccessHeadlineSection(data: List<HeadlineArticleDto>, onNewsClicked: (HeadlineArticleDto) -> Unit) {
    LazyColumn {
        items(data.size) {
            VerticalHeadlineItem(
                title = data[it].title,
                source = data[it].source,
                publishedAt = data[it].publishedAt,
                urlToImage = data[it].urlToImage
            ) { onNewsClicked(data[it]) }
        }
    }
}