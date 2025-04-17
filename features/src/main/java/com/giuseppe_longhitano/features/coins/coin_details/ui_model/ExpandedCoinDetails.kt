package com.giuseppe_longhitano.features.coins.coin_details.ui_model

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.ui.ui_model.UIState

data class ExpandedCoinDetails(val coinDetails: CoinDetails? = null, val chart: UIState<Chart> = UIState(isLoading = true, data = null))