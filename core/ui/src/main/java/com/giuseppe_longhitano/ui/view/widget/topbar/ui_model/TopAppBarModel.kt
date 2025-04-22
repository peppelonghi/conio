package com.giuseppe_longhitano.ui.view.widget.topbar.ui_model

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarModel(
    val title: String ? = null,
    val navigationIcon: ImageVector? = null,
    val onNavigationIconClick: (() -> Unit)? = null,
    val actions: @Composable RowScope.() -> Unit = {}
)