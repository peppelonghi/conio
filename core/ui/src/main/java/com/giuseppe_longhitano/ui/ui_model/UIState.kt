package com.giuseppe_longhitano.ui.ui_model

data class UIState<out T>(val data: T? = null, val error: Throwable? = null, val isLoading: Boolean)
