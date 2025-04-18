package com.giuseppe_longhitano.ui.view.widget.chart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.R

@Composable
fun ErrorChart(modifier: Modifier = Modifier, msg: String = stringResource(R.string.ops_graph), handleEvent: (UIEvent) -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.error)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    Card( modifier = modifier.clickable{
        handleEvent.invoke(CommonEvent.Retry)
    }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieAnimation(
                modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.chart_height)),
                composition = composition,
                progress = { progress }
            )
            Text(
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                text = msg,
            )
        }
    }


}
