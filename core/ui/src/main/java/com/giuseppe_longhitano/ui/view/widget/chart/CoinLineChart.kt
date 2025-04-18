package com.giuseppe_longhitano.ui.view.widget.chart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.arch.model.ScaleInfo
import com.giuseppe_longhitano.arch.utils.Utils.Companion.formatValue
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.ui.R
import com.giuseppe_longhitano.ui.ui_model.UIState
import com.giuseppe_longhitano.ui.view.atomic_view.ChipGroup
import com.giuseppe_longhitano.ui.view.widget.base.BaseWidget


private const val TAG = "ConioLineChart"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinLineChart(
    state: UIState<Chart>?,
    modifier: Modifier = Modifier,
    handleEvent: (UIEvent) -> Unit,
    hourIntervals : List<String>,
    dayIntervals: List<String>

) {

    BaseWidget(
        uiState = state,
        handleEvent = handleEvent,
        modifier = modifier,
        errorView = {
           ErrorChart { handleEvent.invoke(it) }
        },
        content = {
            state?.let {
                Column {
                    if (state.data?.listChartItems.orEmpty().isEmpty()) ErrorChart(modifier = Modifier.fillMaxWidth()) { handleEvent.invoke(it) }
                    else {
                        val items = state.data?.listChartItems.orEmpty()
                        val titles = items.map { it.title }
                        var selectedItem = remember { mutableStateOf(items.first()) }
                        val lineColor = MaterialTheme.colorScheme.primary.toArgb()
                        SettingsChart(modifier = Modifier.fillMaxWidth(), handleEvent = handleEvent, dayInterval = dayIntervals, hourInterval =  hourIntervals)
                        AndroidView(
                            modifier = Modifier
                                .height(dimensionResource(R.dimen.chart_height))
                                .fillMaxWidth(),
                            factory = { context ->
                                LineChart(context).apply {
                                    description.isEnabled = false
                                }
                            },
                            update = { chart ->
                                val item = selectedItem.value.item.map { it[1].toDouble() }
                                val scaleInfo = ScaleInfo.determineScale(item)
                                val entries = item.mapIndexed { index, item ->
                                    val scaledValue = item / scaleInfo.scale
                                    Entry(index.toFloat(), scaledValue.toFloat())
                                }
                                val dataSet = LineDataSet(entries, selectedItem.value.title)
                                dataSet.apply {
                                    color = lineColor
                                    lineWidth = 2f
                                    setDrawCircles(false)
                                }
                                chart.apply {
                                    data = LineData(dataSet)
                                    axisLeft.valueFormatter = object : ValueFormatter() {
                                        override fun getFormattedValue(value: Float): String {
                                            return formatValue(value, scaleInfo)
                                        }
                                    }
                                    axisRight.isEnabled = false
                                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                                    invalidate()
                                }

                            })
                        ChipGroup(
                            items = titles,
                            selectedItem = selectedItem.value.title,
                            modifier = Modifier.padding(16.dp),
                            onItemSelected = {
                                selectedItem.value =
                                    items.firstOrNull { item -> item.title == it }
                                        ?: selectedItem.value
                            })
                    }
                }
            }
        })

}













