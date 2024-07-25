package com.codingle.newsoncompose.screen.home.section

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
internal fun SourceSection(
    sources: BaseState<List<SourceDto>>,
    onReload: () -> Unit
) = AnimatedVisibility(visible = true) {
    when (sources) {
        is StateFailed -> ReloadState(modifier = Modifier.padding(horizontal = 16.dp), onReload = onReload)
        is StateSuccess -> SuccessSourceSection(sources.data.orEmpty())
        else -> LoadingSourceSection()
    }
}

@Composable
private fun LoadingSourceSection() {
    LazyRow(modifier = Modifier.padding(horizontal = 16.dp)) {
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
                if (i != 5) Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}

@Composable
private fun SuccessSourceSection(data: List<SourceDto>) {
    val context = LocalContext.current
    var selectedItemPos by remember { mutableIntStateOf(0) }

    ChipGroup(
        item = data.map { it.name.orEmpty() }.toMutableList().apply {
            add(0, context.getString(R.string.sources_all))
        },
        selectedItemPos = selectedItemPos
    ) { selectedItemPos = it }
}