package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.zIndex
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.R
import com.giuseppe_longhitano.ui.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView

@Composable
fun <T> BaseScreen(
    handleEvent: (UIEvent) -> Unit,
    uiState: UIState<T>,
    modifier: Modifier = Modifier,
    loadingView: @Composable BoxScope.() -> Unit = { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) },
    errorView:   @Composable BoxScope.()  -> Unit = {
        ErrorMsgView(modifier = Modifier.fillMaxWidth().align(Alignment.BottomEnd).zIndex(10f), uiState.error, handleEvent)
    },
    content: @Composable BoxScope.()  -> Unit
) {
    with(uiState) {
        Box(modifier = modifier.fillMaxSize()) {
            if (isLoading) loadingView()
            if (uiState.error != null) errorView()
            if (uiState.data !=null) content()
        }
    }


}