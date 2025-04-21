package com.giuseppe_longhitano.coin.preview.common_view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giuseppe_longhitano.coin.coin_details.view.CoinDetailsHeader
import com.giuseppe_longhitano.coin.preview.mock_model.mockCoin


@Preview
@Composable
fun CoinItemInfoPreview() {
    CoinDetailsHeader(modifier = Modifier.fillMaxWidth(), coin = mockCoin, handleEvent = {})
}