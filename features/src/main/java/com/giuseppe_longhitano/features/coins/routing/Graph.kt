package com.giuseppe_longhitano.features.coins.routing

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
import com.giuseppe_longhitano.features.R
import com.giuseppe_longhitano.features.coins.coin_details.view.CoinDetailsScreen
import com.giuseppe_longhitano.features.coins.coin_list.CoinListScreen
import com.giuseppe_longhitano.features.coins.routing.RouteScreen.CoinDetailScreen
import com.giuseppe_longhitano.features.coins.routing.RouteScreen.CoinListScreen
import com.giuseppe_longhitano.ui.ui_model.TopAppBarState

fun NavGraphBuilder.coinFeatureGraph(
    onChangeTopBarState: (TopAppBarState) -> Unit,
    onNavigationEvent: (Route) -> Unit
) {
    composable<CoinListScreen> {
        onChangeTopBarState.invoke(
            TopAppBarState(
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
            TopAppBarState(
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