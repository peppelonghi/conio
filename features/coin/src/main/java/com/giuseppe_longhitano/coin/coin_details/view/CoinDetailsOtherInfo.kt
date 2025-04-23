package com.giuseppe_longhitano.coin.coin_details.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.features.coin.R
import com.giuseppe_longhitano.ui.view.widget.extra.LabelValueItem
import com.giuseppe_longhitano.ui.view.widget.text_with_url.ClickableUrl

@Composable
fun CoinDetailsOtherInfo(modifier: Modifier = Modifier, coinDetails: CoinDetails?, handleEvent: (UIEvent) -> Unit) {
    if (coinDetails == null) {
        Text(text = stringResource(R.string.no_info), style = MaterialTheme.typography.titleMedium, modifier = modifier)
    } else {
        with(coinDetails) {
            Column(
                modifier = modifier
                    ,
            ) {
                Card {
                    LabelValueItem(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp, horizontal = 16.dp),
                        label = stringResource(R.string.description),
                        value = description
                    )
                }
                 ClickableUrl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    url = url,
                    handleEvent = handleEvent,
                    textBeforeLink = stringResource(R.string.to_have_more_info)
                )
            }

        }
    }

}