package com.giuseppe_longhitano.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.ui.ui_model.UIState
import com.giuseppe_longhitano.ui.view.error.ErrorMsgView

@Composable
fun <T> BaseScreen(
    uiState: UIState<T>,
    modifier: Modifier = Modifier,
    loadingView: @Composable BoxScope.() -> Unit = { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) },
    errorView:   @Composable BoxScope.()  -> Unit = {
        ErrorMsgView(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomEnd), uiState.error
        )
    },
    content: @Composable BoxScope.()  -> Unit
) {
    with(uiState) {
        Box(modifier = modifier) {
            if (isLoading) loadingView()
            if (uiState.error != null) errorView()
            content()
        }
    }


}