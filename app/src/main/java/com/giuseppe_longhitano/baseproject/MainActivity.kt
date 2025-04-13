package com.giuseppe_longhitano.baseproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.baseproject.routing.RouteScreen.CoinDetailScreen
import com.giuseppe_longhitano.baseproject.coin_details.CoinDetailsScreen
import com.giuseppe_longhitano.baseproject.coin_list.CoinListScreen
import com.giuseppe_longhitano.baseproject.routing.RouteScreen
import com.giuseppe_longhitano.baseproject.routing.RouteScreen.CoinListScreen
import com.giuseppe_longhitano.ui.theme.ConioProjectTheme
import com.giuseppe_longhitano.ui.ui_model.TopAppBarState
import com.giuseppe_longhitano.ui.view.ConioTopAppBar


private const val TAG = "MainActivity"

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    var topAppBarState by remember { mutableStateOf<TopAppBarState>(TopAppBarState()) }

    ConioProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    ConioTopAppBar(topAppBarState)
                },
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = CoinListScreen
                ) {
                    composable<CoinListScreen> {
                        topAppBarState = topAppBarState.copy(
                            title = stringResource(R.string.home),
                            onNavigationIconClick = { },
                            navigationIcon = Icons.Default.Home
                        )
                        CoinListScreen(
                            handleEvent = { event ->
                                navController.navigate(event.dest)
                            },
                        )
                    }
                    composable<CoinDetailScreen>
                    { backStackEntry ->
                        val id = backStackEntry.toRoute<CoinDetailScreen>().id
                        topAppBarState = topAppBarState.copy(
                            title = id,
                            onNavigationIconClick = { navController.popBackStack() },
                            navigationIcon = Icons.AutoMirrored.Default.ArrowBack
                        )
                        CoinDetailsScreen {}
                    }

                }

            }
        }
    }

}




