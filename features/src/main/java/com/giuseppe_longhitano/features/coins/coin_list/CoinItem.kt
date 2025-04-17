package com.giuseppe_longhitano.features.coins.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.features.R
import com.giuseppe_longhitano.ui.view.atomic_view.LoadingImageView

@Composable
fun CoinItem(modifier: Modifier = Modifier, coin: Coin, handleEvent: (CoinListEvent) -> Unit) {
    with(coin) {
        ListItem(
            modifier = modifier.clickable {
                handleEvent.invoke(CoinListEvent.ClickedCoin(coin.id))
            },
            leadingContent = {
                LoadingImageView(
                        url = urlmage, modifier = Modifier
                            .size(dimensionResource(R.dimen.thumb_size))
                    )
            },
            headlineContent = {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingContent = {
                Text(
                    text = symbol,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            supportingContent = {
                Text(
                    text = stringResource(R.string.value_eur, currentPrice),
                    style = MaterialTheme.typography.labelMedium
                )
            },
        )
    }
}



