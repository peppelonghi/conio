package com.giuseppe_longhitano.ui.view.atomic_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    items: List<String>,
    selectedItem: String,
    handleEvent:  (ChipSelectionEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemSel = remember { mutableStateOf(selectedItem) }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { item -> item }) { item ->
            Chip(isSelected = itemSel.value == item, onClick = {
                itemSel.value = item
                handleEvent.invoke(ChipSelectionEvent(item))
            }, text = item)
        }
    }
}