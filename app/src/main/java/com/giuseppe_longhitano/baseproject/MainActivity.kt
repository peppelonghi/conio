package com.giuseppe_longhitano.baseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.giuseppe_longhitano.arch.routing.Back
import com.giuseppe_longhitano.features.coins.routing.coinFeatureGraph

import com.giuseppe_longhitano.features.coins.routing.RouteScreen.CoinListScreen
import com.giuseppe_longhitano.ui.theme.ConioProjectTheme
import com.giuseppe_longhitano.ui.ui_model.TopAppBarState
import com.giuseppe_longhitano.ui.view.widget.ConioTopAppBar


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
                    coinFeatureGraph(onChangeTopBarState = {top->
                        topAppBarState = top
                    }, onNavigationEvent = {route->
                        when(route) {
                            is Back -> navController.popBackStack()
                            else -> navController.navigate(route)
                        }
                    })

                }

            }
        }
    }

}






