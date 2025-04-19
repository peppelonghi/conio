package com.giuseppe_longhitano.ui.utils

import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlinx.coroutines.flow.StateFlow

 fun <T> StateFlow<UIState<T>>.getData() = this.value.data