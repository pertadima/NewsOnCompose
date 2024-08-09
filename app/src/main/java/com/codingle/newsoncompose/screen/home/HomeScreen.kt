package com.codingle.newsoncompose.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.core_data.data.navigation.Search
import com.codingle.newsoncompose.screen.home.section.HeadlineSection
import com.codingle.newsoncompose.screen.home.section.SourceSection

@Composable
fun HomeRoute(navController: NavHostController, modifier: Modifier) {
    HomeScreen(modifier = modifier) { navController.navigate(Search) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    onNavigateToSearch: () -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))
        SourceSection()
        Spacer(modifier = Modifier.height(20.dp))
        HeadlineSection()
    }
}