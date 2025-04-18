package com.giuseppe_longhitano.ui.utils

import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.flow.StateFlow

 fun <T> StateFlow<UIState<T>>.getData() = this.value.data