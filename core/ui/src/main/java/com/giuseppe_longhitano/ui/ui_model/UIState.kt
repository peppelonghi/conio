package com.giuseppe_longhitano.ui.ui_model

data class UIState<T>(val data: T?, val error: Throwable? = null, val isLoading: Boolean)
