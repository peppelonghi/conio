package com.giuseppe_longhitano.coin.common_view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.features.coin.R
import com.giuseppe_longhitano.ui.view.widget.extra.LabelValueItem

@Composable
fun CoinDetailsInfo(modifier : Modifier= Modifier, coinDetails: CoinDetails?) {
    coinDetails?.let {
        Card(modifier = modifier) {
            LabelValueItem(
                modifier = Modifier.padding(16.dp),
                label = stringResource(R.string.description),
                value = coinDetails.description

            )
        }
    } ?: Text(text = stringResource(R.string.no_info), style = MaterialTheme.typography.titleMedium)
}


