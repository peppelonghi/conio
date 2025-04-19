package com.giuseppe_longhitano.coin.routing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giuseppe_longhitano.arch.routing.Back
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.coin.coin_details.screen.CoinDetailsScreen
import com.giuseppe_longhitano.coin.coin_list.screen.CoinListScreen
import com.giuseppe_longhitano.coin.routing.RouteScreen.CoinDetailScreen
import com.giuseppe_longhitano.coin.routing.RouteScreen.CoinListScreen
import com.giuseppe_longhitano.features.coin.R
import com.giuseppe_longhitano.ui.view.widget.topbar.ui_model.TopAppBarModel

fun NavGraphBuilder.coinFeatureGraph(
    onChangeTopBarState: (TopAppBarModel) -> Unit,
    onNavigationEvent: (Route) -> Unit
) {
    composable<CoinListScreen> {
        onChangeTopBarState.invoke(
            TopAppBarModel(
                title = stringResource(R.string.home),
                onNavigationIconClick = { },
                navigationIcon = Icons.Default.Home
            )
        )

        CoinListScreen(
            modifier = Modifier.fillMaxSize(),
            handleEvent = { event ->
                onNavigationEvent.invoke(event.dest)
            },
        )
    }
    composable<CoinDetailScreen>
    { backStackEntry ->
        val id = backStackEntry.toRoute<CoinDetailScreen>().id
        onChangeTopBarState.invoke(
            TopAppBarModel(
                title = stringResource(R.string.crypto, id.toUpperCase(Locale.current)),
                onNavigationIconClick = {
                    onNavigationEvent.invoke(Back)
                },
                navigationIcon = Icons.AutoMirrored.Default.ArrowBack
            )
        )

        CoinDetailsScreen(
         ) {}
    }
}