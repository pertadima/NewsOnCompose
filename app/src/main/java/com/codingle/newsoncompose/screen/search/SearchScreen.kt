package com.codingle.newsoncompose.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
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
import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
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
    val interactionSource = remember { MutableInteractionSource() }

    val keywords = keywordsState.collectAsStateWithLifecycle().value

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
                        style = typography.titleMedium.copy(fontWeight = W600),
                        color = colorScheme.onBackground,
                        maxLines = 1
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
        Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = CenterVertically) {
            SearchText(viewModel, toSearchResult)

            Text(
                context.getString(R.string.search_cancel),
                style = typography.bodySmall.copy(fontWeight = W600),
                color = colorScheme.primary,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onNavigateBack() }
            )
        }

        Row(
            horizontalArrangement = SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                context.getString(R.string.search_recent),
                style = typography.titleMedium.copy(fontWeight = W600),
                color = colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(top = 15.dp)
            )

            if (keywords is StateSuccess && keywords.data?.isNotEmpty() == true) {
                Text(
                    context.getString(R.string.search_delete),
                    style = typography.titleMedium.copy(fontWeight = W600),
                    color = colorScheme.primary,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .padding(top = 15.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { viewModel.deleteKeywords() }
                )
            }
        }


        if (keywords is StateSuccess) KeywordsStateSuccess(keywords.data.orEmpty(), toSearchResult)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.SearchText(
    viewModel: SearchViewModel,
    toSearchResult: (String) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

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
            if (searchText.isNotEmpty() && searchText.isNotBlank()) viewModel.insertKeyword(
                searchText
            )
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
                    style = typography.bodySmall.copy(fontWeight = W600),
                    color = colorScheme.surfaceContainerHighest,
                    maxLines = 1
                )
            },
            contentPadding = PaddingValues(12.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = colorScheme.onBackground,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent,
                errorIndicatorColor = Transparent,
                focusedContainerColor = colorScheme.surfaceContainer,
                disabledContainerColor = colorScheme.surfaceContainer,
                errorContainerColor = colorScheme.surfaceContainer,
                unfocusedContainerColor = colorScheme.surfaceContainer
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
}

@Composable
private fun KeywordsStateSuccess(
    keywords: List<SearchKeywordDto>,
    toSearchResult: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier = Modifier.height(20.dp))
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(keywords.size) {
            Column(
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { toSearchResult(keywords[it].keyword) }
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    keywords[it].keyword,
                    style = typography.titleSmall,
                    color = colorScheme.onBackground,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(colorScheme.surfaceDim)
                )
            }
        }
    }
}