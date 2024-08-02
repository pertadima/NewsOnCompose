package com.codingle.newsoncompose.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.codingle.newsoncompose.screen.home.section.HeadlineSection
import com.codingle.newsoncompose.screen.home.section.SourceSection

@Composable
fun HomeRoute(navController: NavHostController, modifier: Modifier) {
    HomeScreen(modifier = modifier)
    Log.e("TAG", "HomeRoute: $navController")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets
) = with(viewModel) {
    val sources = viewModel.sourcesState.collectAsStateWithLifecycle().value
    val headlines = viewModel.headlineState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        getSources()
        getHeadlines()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        TopAppBar(
            windowInsets = windowInsets,
            title = {
                Text(
                    context.getString(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W600),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            },
            actions = {
                Row(
                    horizontalArrangement = spacedBy(8.dp),
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
                    )
                }
            },
            colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground
            )
        )
        LazyColumn {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                SourceSection(
                    sources,
                    onReload = { getSources() },
                    onTabChanged = {
                        resetHeadlineState()
                        val source = if (it == context.getString(R.string.sources_all)) "" else it
                        getHeadlines(source)
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { HeadlineSection(headlines, onReload = { getHeadlines() }) }
            item { Spacer(modifier = Modifier.height(1200.dp)) }
        }
    }
}