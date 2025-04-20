package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView


@Composable
fun <T> BaseWidget(
    handleEvent: (UIEvent) -> Unit,
    uiState: UIState<T>?,
    modifier: Modifier = Modifier,
    loadingView: @Composable BoxScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    },
    errorView: @Composable BoxScope.() -> Unit = {
        ErrorMsgView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            uiState?.error,
            handleEvent = handleEvent
        )
    },
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        uiState?.let { state ->
            if (state.isLoading) loadingView()
            if (state.error != null) errorView()
            if (state.data != null) content()

        } ?: errorView()
    }


}