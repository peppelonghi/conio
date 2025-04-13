package com.giuseppe_longhitano.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import com.giuseppe_longhitano.ui.ui_model.TopAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConioTopAppBar( topAppBarState: TopAppBarState) {
    TopAppBar(
        title = { Text(text = topAppBarState.title, style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            topAppBarState.navigationIcon?.let { icon ->
                IconButton(onClick = { topAppBarState.onNavigationIconClick?.invoke() }) {
                    Icon(imageVector = icon, contentDescription = "Back")
                }
            }
        },
        actions = topAppBarState.actions,
    )
}