package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.ui.BaseViewModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView

private const val TAG = "BaseScreen"
@Composable
 fun <T> PageScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel<T>,
    handleEvent: (UIEvent) -> Unit,
    onChangeRoute: (Route) -> Unit,
    contentScreen: @Composable (UIState<T>) -> Unit,
    loadingView: (@Composable BoxScope.() -> Unit)?=null,
    errorMsgView: (@Composable BoxScope.() -> Unit)?=null,
    errorPageView:( @Composable BoxScope.() -> Unit )?=null,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationEvent.collect { event ->
            onChangeRoute.invoke(event.route)
        }
    }
        with(state) {
            Box(modifier = modifier.fillMaxSize()) {
                when {
                    data == null && error != null && !isLoading -> if (errorPageView!=null) errorPageView() else ErrorPageScreen(modifier = modifier, handleEvent = handleEvent, throwable = error)
                    isLoading -> if ( loadingView !=null) loadingView() else CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    error != null -> if (errorMsgView!=null) errorMsgView() else   ErrorMsgView(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd)
                        .zIndex(10f), state.error, handleEvent)
                    data != null ->    contentScreen.invoke(state)
                }
            }
        }

}



