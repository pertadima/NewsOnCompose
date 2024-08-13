package com.codingle.newsoncompose.screen.searchresult

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core.util.Util.openBrowser
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.data.navigation.Home
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState
import com.codingle.newsoncompose.screen.splash.SplashScreenAttr

@Composable
fun SearchResultRoute(navController: NavHostController, modifier: Modifier, keyword: String) {
    SearchResultScreen(modifier = modifier, keyword = keyword) {
        navController.popBackStack(Home, false)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    viewModel: SearchResultViewModel = hiltViewModel(),
    modifier: Modifier,
    keyword: String,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    onNavigateBack: () -> Unit
) {
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
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W600),
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
                if (headlines.data.orEmpty().isEmpty()) EmptyHeadlineSection()
                else SuccessHeadlineSection(headlines.data.orEmpty())
            }

            is StateFailed -> {
                ReloadState(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(134.dp),
                    onReload = { viewModel.searchHeadline(keyword) }
                )
            }

            else -> {

            }
        }
    }
}

@Composable
private fun SuccessHeadlineSection(data: List<HeadlineArticleDto>) {
    LazyColumn { items(data.size) { HeadlineItem(data[it]) } }
}

@Composable
private fun HeadlineItem(item: HeadlineArticleDto) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { context.openBrowser(item.url) }
    ) {
        Row {
            if (item.urlToImage.isNotEmpty() && item.urlToImage.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .allowHardware(true)
                        .data(item.urlToImage)
                        .build(),
                    contentDescription = SplashScreenAttr.LOGO_CONTENT_DESCRIPTION,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorScheme.surfaceContainerHigh),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1F)) {
                Text(
                    item.source,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = W600),
                    color = colorScheme.onBackground,
                    maxLines = 1,
                    overflow = Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onBackground,
                    maxLines = 3,
                    overflow = Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    item.publishedAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.inverseSurface,
                    maxLines = 3,
                    overflow = Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorScheme.surfaceDim)
        )
    }
}

@Composable
private fun EmptyHeadlineSection() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.not_found))
        LottieAnimation(
            composition,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .padding(top = 100.dp),
            iterations = IterateForever,
        )

        Text(
            context.getString(R.string.headlines_no_result),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = W600),
            color = colorScheme.onBackground,
            overflow = Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}