package com.codingle.newsoncompose.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_data.data.navigation.SearchResult

@Composable
fun SearchRoute(navController: NavHostController, modifier: Modifier) {
    SearchScreen(
        modifier = modifier,
        toSearchResult = { navController.navigate(SearchResult(it)) },
        onNavigateBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    toSearchResult: (String) -> Unit,
    onNavigateBack: () -> Unit
) = with(viewModel) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val interactionSource = remember { MutableInteractionSource() }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { getKeywords() }

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
                        context.getString(R.string.search_title),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = W600),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
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
        Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = CenterVertically) {
            BasicTextField(
                modifier = Modifier.weight(1F),
                value = searchText,
                singleLine = true,
                onValueChange = { searchText = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = Text,
                    imeAction = Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboard?.hide()
                    if (searchText.isNotEmpty() && searchText.isNotBlank()) insertKeyword(searchText)
                    toSearchResult(searchText)
                    searchText = ""
                })
            ) {
                TextFieldDefaults.DecorationBox(
                    enabled = true,
                    interactionSource = interactionSource,
                    singleLine = true,
                    visualTransformation = None,
                    value = searchText,
                    innerTextField = it,
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            context.getString(R.string.search_placeholder),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = W600),
                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                            maxLines = 1
                        )
                    },
                    contentPadding = PaddingValues(12.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        errorContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    leadingIcon = {
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
                )
            }
            Text(
                context.getString(R.string.search_cancel),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = W600),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onNavigateBack() }
            )
        }

        Text(
            context.getString(R.string.search_recent),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = W600),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 15.dp)
        )

        val keywords = keywordsState.collectAsStateWithLifecycle().value
        if (keywords is StateSuccess) {
            Box(modifier = Modifier.height(20.dp))
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(keywords.data.orEmpty().size) {
                    Column(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { toSearchResult(keywords.data?.get(it)?.keyword.orEmpty()) }
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            keywords.data?.get(it)?.keyword.orEmpty(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.surfaceDim)
                        )
                    }
                }
            }
        }
    }
}