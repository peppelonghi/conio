package com.giuseppe_longhitano.coin.coin_details.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.giuseppe_longhitano.coin.coin_list.screen.CoinListEvent
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.features.coin.R
import com.giuseppe_longhitano.ui.view.atomic_view.LoadingImageView

@Composable
fun CoinItemInfo(modifier: Modifier = Modifier, coin: Coin?, handleEvent: (CoinListEvent) -> Unit) {
    coin?.let {
        ListItem(
            modifier = modifier.clickable {
                handleEvent.invoke(CoinListEvent.ClickedCoin(coin.id))
            },
            leadingContent = {
                LoadingImageView(
                    url = it.urlmage, modifier = Modifier
                        .size(dimensionResource(R.dimen.thumb_size))
                )
            },
            headlineContent = {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            trailingContent = {
                Text(
                    text = it.symbol,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            supportingContent = {
                Text(
                    text = stringResource(R.string.value_eur, it.currentPrice),
                    style = MaterialTheme.typography.labelMedium
                )
            },
        )
    }?: Text(
        text = stringResource(R.string.no_info),
        style = MaterialTheme.typography.headlineMedium
    )
}