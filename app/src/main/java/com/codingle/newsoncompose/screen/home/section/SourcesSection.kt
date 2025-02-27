package com.codingle.newsoncompose.screen.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codingle.newsoncompose.R
import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.chip.ChipGroup
import com.codingle.newsoncompose.core_ui.component.reload.ReloadState
import com.codingle.newsoncompose.core_ui.component.shimmer.shimmer
import com.codingle.newsoncompose.screen.home.HomeScreenAttr.LOADING_PLACEHOLDER_SIZE

@Composable
internal fun SourceSection(
    sources: BaseState<List<SourceDto>>,
    selectedItemPos: Int,
    onReload: () -> Unit,
    onUpdateSelectedTabPosition: (Int, String) -> Unit
) {
    val context = LocalContext.current

    when (sources) {
        is StateFailed -> ReloadState(modifier = Modifier.padding(horizontal = 16.dp), onReload = { onReload() })
        is StateSuccess -> SuccessSourceSection(sources.data.orEmpty(), selectedItemPos) { pos, item ->
            val source = if (item == context.getString(R.string.sources_all)) "" else item
            onUpdateSelectedTabPosition(pos, source)
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
        repeat(LOADING_PLACEHOLDER_SIZE) {
            item {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .shimmer()
                        .background(colorScheme.surfaceContainerHigh)
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