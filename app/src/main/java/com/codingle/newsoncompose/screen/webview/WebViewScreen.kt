package com.codingle.newsoncompose.screen.webview

import android.annotation.SuppressLint
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager.LayoutParams.MATCH_PARENT
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.codingle.newsoncompose.R

@Composable
fun WebViewRoute(
    navController: NavHostController,
    modifier: Modifier,
    title: String,
    url: String,
    isFavorite: Boolean
) {
    WebViewScreen(
        modifier = modifier,
        title = title,
        url = url,
        isFavorite = isFavorite,
        onNavigateBack = { navController.popBackStack() }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WebViewScreen(
    viewModel: WebViewViewModel = hiltViewModel(),
    modifier: Modifier,
    title: String,
    url: String,
    isFavorite: Boolean,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    onNavigateBack: () -> Unit
) = with(viewModel) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    var isLoading by remember { mutableStateOf(true) }
    var isHeadlineFavorite by remember { mutableStateOf(isFavorite) }

    val webView = remember(context) {
        WebView(context).apply {
            setLayerType(LAYER_TYPE_HARDWARE, null)
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    isLoading = false
                }
            }
            settings.javaScriptEnabled = true
        }
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
                    title,
                    style = typography.titleMedium.copy(fontWeight = W600),
                    color = colorScheme.onBackground,
                    maxLines = 2,
                    overflow = Ellipsis
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
            },
            actions = {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id =
                            if (isHeadlineFavorite) R.drawable.ic_favorite
                            else R.drawable.ic_outline_favorite
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            makeText(
                                context,
                                context.getString(
                                    if (isHeadlineFavorite) R.string.favorite_removed
                                    else R.string.favorite_saved
                                ),
                                LENGTH_SHORT
                            ).show()
                            isHeadlineFavorite = isHeadlineFavorite.not()
                            updateIsFavoriteHeadline(isHeadlineFavorite, title)
                        }
                        .padding(horizontal = 16.dp)
                )
            }
        )

        Box {
            AndroidView(
                factory = { webView },
                update = { webView -> webView.loadUrl(url) }
            )

            if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Center))
        }
    }
}