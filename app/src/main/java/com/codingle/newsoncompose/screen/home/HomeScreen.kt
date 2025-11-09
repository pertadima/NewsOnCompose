package com.codingle.newsoncompose.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.navigation.Search
import com.codingle.newsoncompose.core_data.data.navigation.WebView
import com.codingle.newsoncompose.core_ui.component.newsappbar.NewsAppBar
import com.codingle.newsoncompose.screen.home.section.HeadlineSection
import com.codingle.newsoncompose.screen.home.section.SourceSection

@Composable
fun HomeRoute(navController: NavHostController, modifier: Modifier) {
    HomeScreen(
        modifier = modifier,
        onNavigateToSearch = { navController.navigate(Search) },
        onNewsClicked = { navController.navigate(WebView(it.title, it.url, it.isFavorite)) }
    )
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    onNavigateToSearch: () -> Unit,
    onNewsClicked: (HeadlineArticleDto) -> Unit
) = with(viewModel) {
    val sourcesState = sourcesState.collectAsStateWithLifecycle().value
    val headlinesState = headlineState.collectAsStateWithLifecycle().value
    val selectedItemPos = selectedTabPosition.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Header(onNavigateToSearch = onNavigateToSearch)
        Spacer(modifier = Modifier.height(16.dp))
        SourceSection(
            sources = sourcesState,
            selectedItemPos = selectedItemPos,
            onReload = { getSources() },
            onUpdateSelectedTabPosition = { pos, source ->
                updateSelectedTabPosition(pos)
                getHeadlines(source)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        HeadlineSection(
            headlines = headlinesState,
            onReload = { getHeadlines() },
            onNewsClicked = onNewsClicked
        )
    }
}

@Composable
private fun Header(onNavigateToSearch: () -> Unit) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    NewsAppBar(
        title = context.getString(R.string.app_name),
        actions = {
            Row(
                horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp),
                modifier = Modifier.padding(end = 24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "",
                    tint = colorScheme.onBackground,
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onNavigateToSearch() }
                )
            }
        }
    )
}
