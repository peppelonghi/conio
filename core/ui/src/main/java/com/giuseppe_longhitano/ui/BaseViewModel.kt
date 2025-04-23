package com.giuseppe_longhitano.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.arch.event.NavigationEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "BaseViewModel"
abstract class BaseViewModel<T>(initialData: T? = null) : ViewModel() {


    protected val _uiState = MutableStateFlow(UIState(data = initialData))

    open val uiState: StateFlow<UIState<T>> = _uiState

    private val _navigationEvent = Channel<NavigationEvent>(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()



    open  fun handleEvent(uiEvent: UIEvent) {
        when(uiEvent){
            is NavigationEvent-> navigate(event = uiEvent)
        }
    }

    private fun navigate(event: NavigationEvent) {
         viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

}