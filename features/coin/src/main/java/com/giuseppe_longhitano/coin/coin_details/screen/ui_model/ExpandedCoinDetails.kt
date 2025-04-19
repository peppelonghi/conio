package com.giuseppe_longhitano.coin.coin_details.screen.ui_model

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState

data class ExpandedCoinDetails(val coinDetails: CoinDetails, val chart: UIState<Chart> = UIState(isLoading = true, data = Chart()))