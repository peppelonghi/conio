package com.giuseppe_longhitano.ui.view.widget.extra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.ui.R


enum class Orientation {
    VERTICAL, HORIZONTAL
}

@Composable
fun LabelValueItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    labelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
    orientation: Orientation = Orientation.VERTICAL
) {

    when (orientation) {
        Orientation.VERTICAL -> Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
            Text(
                text = label,
                color = labelColor,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = value.takeIf { it.isNotEmpty() } ?: stringResource(R.string.no_info),
                color = valueColor,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Orientation.HORIZONTAL -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = label,
                color = labelColor,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(0.5f)
            )
            Text(
                textAlign = TextAlign.End,
                text = value.takeIf { it.isNotEmpty() } ?: stringResource(R.string.no_info),
                color = valueColor,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(0.5f)
            )
        }
    }

}