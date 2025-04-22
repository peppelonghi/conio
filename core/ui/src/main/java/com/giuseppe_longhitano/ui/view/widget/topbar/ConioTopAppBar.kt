package com.giuseppe_longhitano.ui.view.widget.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.giuseppe_longhitano.ui.view.widget.topbar.ui_model.TopAppBarModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConioTopAppBar(topAppBarModel: TopAppBarModel) {
    TopAppBar(
        title = { Text(text = topAppBarModel.title.orEmpty(), style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            topAppBarModel.navigationIcon?.let { icon ->
                IconButton(onClick = { topAppBarModel.onNavigationIconClick?.invoke() }) {
                    Icon(imageVector = icon, contentDescription = "Back")
                }
            }
        },
        actions = topAppBarModel.actions,
    )
}