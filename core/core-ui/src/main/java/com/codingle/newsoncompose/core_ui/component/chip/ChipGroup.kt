package com.codingle.newsoncompose.core_ui.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    item: List<String>,
    onItemSelected: (Int) -> Unit = {},
) {
    var selectedItems by remember { mutableStateOf("") }
    val state = rememberLazyListState()

    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item.forEachIndexed { index, data ->
            item {
                Chip(name = data, selected = selectedItems == data, onClick = {
                    onItemSelected(index)
                    selectedItems = data
                })
            }
        }
    }
}