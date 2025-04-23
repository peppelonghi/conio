package com.giuseppe_longhitano.ui.view.widget.base.ui_model

data class ListModel<T>(
    val isItemsLoading: Boolean = false,
    val error: Throwable? = null,
    val endReached: Boolean = false,
    val items: List<T> = emptyList(),
    val page: Int = 1
)