package com.giuseppe_longhitano.ui.view.widget.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.arch.model.ScaleInfo
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.ui.R
import com.giuseppe_longhitano.ui.view.atomic_view.ChipGroup
import com.giuseppe_longhitano.ui.view.widget.base.BaseWidget
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState


private const val TAG = "ConioLineChart"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinLineChart(
    state: UIState<Chart>?,
    modifier: Modifier = Modifier,
    handleEvent: (UIEvent) -> Unit,
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
                val selected = remember { mutableStateOf<String?>(null) }
                val items = state.data?.itemsChart.orEmpty()
                val titles = items.map { it.title }
                val lineColor = MaterialTheme.colorScheme.primary.toArgb()
                Column {
                    if (state.data?.itemsChart.orEmpty()
                            .isEmpty()
                    ) ErrorChart(modifier = Modifier.fillMaxWidth()) { handleEvent.invoke(it) }
                    else {
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
                                // Retrieve the data
                                val xValues =
                                    (if (selected.value == null) items.first() else items.firstOrNull { it.title == selected.value })?.coords.orEmpty()
                                        .map { it[0].toDouble() }
                                val yValues =
                                    (if (selected.value == null) items.first() else items.firstOrNull { it.title == selected.value })?.coords.orEmpty()
                                        .map { it[1].toDouble() }

                                val scaleInfo = ScaleInfo.determineScale(yValues)
                                val timestampMap = mutableMapOf<Float, Double>()

                                val entries = yValues.mapIndexed { index, item ->
                                    val scaledValue = item / scaleInfo.scale
                                    timestampMap.put(index.toFloat(), xValues[index])
                                    Entry(index.toFloat(), scaledValue.toFloat())
                                }

                                val dataSet = LineDataSet(entries, items.first().title)
                                dataSet.apply {
                                    color = lineColor
                                    lineWidth = 2f
                                    valueTextColor = lineColor
                                    setDrawCircles(false)
                                }

                                chart.apply {
                                    legend.textColor = lineColor
                                    data = LineData(dataSet)
                                    axisRight.isEnabled = false

                                    axisLeft.apply {
                                        axisLineColor = android.graphics.Color.TRANSPARENT
                                        // Move the labels inside the chart
                                        setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
                                        // Set TextColor
                                        textColor = lineColor
                                        //disable the space
                                        spaceTop = 0f
                                        valueFormatter = ScaleFormatterEur(scaleInfo)

                                    }

                                    xAxis.apply {
                                        position = XAxis.XAxisPosition.BOTTOM
                                        valueFormatter =
                                            IntervalFormatter(
                                                interval = Interval.safeValueOf(
                                                    state.data?.interval
                                                ), timestampMap = timestampMap
                                            )
                                        isGranularityEnabled = true
                                        setGranularity(2f)
                                        textColor = lineColor
                                    }
                                    setScaleEnabled(true)
                                    setPinchZoom(true)
                                    invalidate()
                                }
                            })
                        ChipGroup(
                            items = titles,
                            selectedItem = items.first().title,
                            modifier = Modifier.padding(16.dp),
                            onItemSelected = { selection ->
                                selected.value =
                                    items.firstOrNull { item -> item.title == selection }?.title
                                        ?: selected.value
                            })
                    }
                }
            }
        })

}






