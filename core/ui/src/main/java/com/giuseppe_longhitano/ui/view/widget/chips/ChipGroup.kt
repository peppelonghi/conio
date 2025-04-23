package com.giuseppe_longhitano.ui.view.widget.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.ui.view.shared.common_event.SelectionEvent
import com.giuseppe_longhitano.ui.view.shared.common_ui_model.SelectableItem

@Composable
fun <T> ChipGroup(
    modifier: Modifier = Modifier,
    items: List<SelectableItem<T>>,
    selectedItem: String,
    handleEvent: (SelectionEvent<T>) -> Unit,
    title: String? = null,
) {
    val itemSel = remember { mutableStateOf(selectedItem) }
    Column(modifier = modifier) {
        if (title != null) Text(text = title, modifier = Modifier.padding(vertical = 8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                FilterChip(
                    onClick = {
                        itemSel.value = item.label
                        handleEvent.invoke(SelectionEvent(item.model))
                    },
                    label = { Text(item.label) },
                    selected = itemSel.value == item.label,
                )
            }
        }
    }
}