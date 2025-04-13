package com.giuseppe_longhitano.ui.ui_model

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.giuseppe_longhitano.arch.routing.Route
import kotlinx.serialization.Serializable
import  kotlinx.serialization.Transient


data class TopAppBarState(
    val title: String = "Home",
    val navigationIcon: ImageVector? = Icons.Default.Home,
    val onNavigationIconClick: (() -> Unit)? = null,
    val actions: @Composable RowScope.() -> Unit = {}
)


