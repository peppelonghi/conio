package com.giuseppe_longhitano.ui.view.widget.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.atomic_view.DropDownMenu
import com.giuseppe_longhitano.ui.R

@Composable
fun SettingsChart(modifier: Modifier = Modifier, handleEvent: (UIEvent) -> Unit,  hourInterval: List<String>,  dayInterval: List<String>) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        DropDownMenu(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            hourInterval,
            hourInterval.first(),
            onItemSelected = { clicked ->
                handleEvent.invoke(ChartEvent.OnIntervalChange(clicked))
            },
            title = stringResource(R.string.hour_interval),
        )
        DropDownMenu(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            dayInterval,
            dayInterval.first(),
            onItemSelected = { clicked ->
                handleEvent.invoke(ChartEvent.OnDaysChange(clicked))
            },
            title = stringResource(R.string.day_interval),
        )
    }
}