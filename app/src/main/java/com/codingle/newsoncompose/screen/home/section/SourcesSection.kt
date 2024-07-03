package com.codingle.newsoncompose.screen.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.core_data.base.BaseState
import com.codingle.newsoncompose.core_data.base.BaseState.StateFailed
import com.codingle.newsoncompose.core_data.base.BaseState.StateSuccess
import com.codingle.newsoncompose.core_ui.component.shimmer.shimmer

@Composable
internal fun SourceSection(
    sources: BaseState<List<SourceDto>>
) {
    when (sources) {
        is StateFailed -> Unit
        is StateSuccess -> Unit
        else -> LoadingSourceSection()
    }
}

@Composable
private fun LoadingSourceSection() {
    Row {
        for (i in 0..5) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
                    .background(LightGray)
            )
            if (i != 5) Spacer(modifier = Modifier.width(5.dp))
        }
    }
}