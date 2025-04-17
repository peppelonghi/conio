package com.giuseppe_longhitano.ui.view.atomic_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    isSelected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colorText =
        if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Gray

    val colorBg = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    Box(
        modifier = modifier
            .border(1.dp, colorBg, RoundedCornerShape(16.dp))
            .background(
                colorBg.copy(alpha = 0.8f),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = colorText)
    }
}