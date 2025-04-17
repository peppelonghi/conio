package com.giuseppe_longhitano.coin.coin_details.screen.ui_model

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.ui.ui_model.UIState

data class ExpandedCoinDetails(val coinDetails: CoinDetails? = null, val chart: UIState<Chart> = UIState(isLoading = true, data = null))