package com.codingle.newsoncompose.screen.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateInitial
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.chip.ChipGroup
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState
import com.codingle.newsoncompose.core_ui.component.shimmer.shimmer
import com.codingle.newsoncompose.screen.home.HomeViewModel

@Composable
internal fun SourceSection(
    viewModel: HomeViewModel = hiltViewModel()
) = with(viewModel) {
    val context = LocalContext.current
    val selectedItemPos = selectedTabPosition.collectAsStateWithLifecycle().value
    val sources = sourcesState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { if (sources is StateInitial) getSources() }

    when (sources) {
        is StateFailed -> ReloadState(modifier = Modifier.padding(horizontal = 16.dp), onReload = { getSources() })
        is StateSuccess -> SuccessSourceSection(sources.data.orEmpty(), selectedItemPos) { pos, item ->
            updateSelectedTabPosition(pos)
            val source = if (item == context.getString(R.string.sources_all)) "" else item
            getHeadlines(source)
        }

        else -> LoadingSourceSection()
    }
}

@Composable
private fun LoadingSourceSection() {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = spacedBy(5.dp),
    ) {
        for (i in 0..5) {
            item {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .shimmer()
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                )
            }
        }
    }
}

@Composable
private fun SuccessSourceSection(
    data: List<SourceDto>,
    selectedItemPos: Int,
    onTabChanged: (Int, String) -> Unit
) {
    val context = LocalContext.current

    val mappedData = remember(data) {
        data.map { it.name.orEmpty() }.toMutableList().apply {
            add(0, context.getString(R.string.sources_all))
        }.toList()
    }

    ChipGroup(mappedData, selectedItemPos) { onTabChanged(it, mappedData[it]) }
}