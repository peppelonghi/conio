package com.giuseppe_longhitano.ui.view.widget.base.ui_model

data class UIState<out T>(val data: T? = null, val error: Throwable? = null, val isLoading: Boolean = true)