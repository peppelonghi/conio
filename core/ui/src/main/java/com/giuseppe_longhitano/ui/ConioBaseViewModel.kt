package com.giuseppe_longhitano.ui

import androidx.lifecycle.ViewModel
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ConioBaseViewModel<T>(initialData: T?=null): ViewModel(){

    protected val _uiState = MutableStateFlow(UIState(data = initialData))

    open val uiState: StateFlow<UIState<T>> = _uiState

    abstract fun handleEvent(uiEvent: UIEvent)

}