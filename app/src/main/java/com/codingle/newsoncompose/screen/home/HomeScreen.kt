package com.codingle.newsoncompose.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.navigation.Search
import com.codingle.newsoncompose.core_data.data.navigation.WebView
import com.codingle.newsoncompose.screen.home.section.HeadlineSection
import com.codingle.newsoncompose.screen.home.section.SourceSection

@Composable
fun HomeRoute(navController: NavHostController, modifier: Modifier) {
    HomeScreen(modifier = modifier,
        onNavigateToSearch = { navController.navigate(Search) },
        onNewsClicked = { navController.navigate(WebView(it.title, it.url)) }
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

    LaunchedEffect(Unit) {
        getSources()
        getHeadlines()
    }

    Box(modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header(
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    onNavigateToSearch: () -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    TopAppBar(
        windowInsets = windowInsets,
        title = {
            Column {
                Text(
                    context.getString(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W600),
                    color = colorScheme.onBackground,
                    maxLines = 1
                )
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp),
                modifier = Modifier.padding(end = 24.dp)
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .allowHardware(true)
                        .data(R.drawable.ic_search)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onNavigateToSearch() }
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = colorScheme.background,
            actionIconContentColor = colorScheme.onBackground,
            titleContentColor = colorScheme.onBackground,
            scrolledContainerColor = colorScheme.background,
            navigationIconContentColor = colorScheme.onBackground
        )
    )
}
