package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.R
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView

@Composable
fun <T> BaseScreen(
    handleEvent: (UIEvent) -> Unit,
    uiState: UIState<T>,
    modifier: Modifier = Modifier,
    loadingView: @Composable BoxScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    },
    errorMsgView: @Composable BoxScope.() -> Unit = {
        ErrorMsgView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .zIndex(10f), uiState.error, handleEvent
        )
    },
    errorPageView: @Composable BoxScope.() -> Unit = {
        ErrorPageScreen(
            throwable = uiState.error,
            handleEvent = handleEvent,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
    },
    content: @Composable BoxScope.() -> Unit
) {
    with(uiState) {
        Box(modifier = modifier.fillMaxSize()) {
            when {
                data == null && error != null && !isLoading -> errorPageView()
                isLoading -> loadingView()
                error != null -> errorMsgView()
                data != null -> content()
            }
        }
    }


}


@Composable
private fun ErrorPageScreen(
    modifier: Modifier,
    handleEvent: (UIEvent) -> Unit,
    throwable: Throwable? = null
) {
    val msg = stringResource(R.string.default_msg_error)

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.error)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    Box(
        modifier
            .clickable { handleEvent.invoke(CommonEvent.Retry) }
            .padding(dimensionResource(R.dimen.screen_padding))) {

        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            LottieAnimation(
                modifier = Modifier.heightIn(200.dp, 300.dp),
                composition = composition,
                progress = { progress }
            )
            Text(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(16.dp),
                text = msg,
            )

        }
    }

}