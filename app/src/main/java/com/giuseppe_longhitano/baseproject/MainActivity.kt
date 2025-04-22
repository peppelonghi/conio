package com.giuseppe_longhitano.baseproject

import android.content.Intent
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giuseppe_longhitano.arch.routing.BackRoute
import com.giuseppe_longhitano.arch.routing.ExternalRoute
import com.giuseppe_longhitano.coin.routing.RouteScreen
import com.giuseppe_longhitano.coin.routing.coinFeatureGraph
import com.giuseppe_longhitano.ui.theme.ConioProjectTheme
import com.giuseppe_longhitano.ui.view.widget.topbar.ConioTopAppBar
import com.giuseppe_longhitano.ui.view.widget.topbar.ui_model.TopAppBarModel
import androidx.core.net.toUri


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

@Composable
fun App() {
    val context = LocalContext.current
    val navController = rememberNavController()
    var topAppBarModel by remember { mutableStateOf(TopAppBarModel()) }
    ConioProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    ConioTopAppBar(topAppBarModel)
                },
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = RouteScreen.CoinListScreen
                ) {
                    coinFeatureGraph(onChangeTopBarState = { top ->
                        topAppBarModel = top
                    }, onNavigationEvent = { route ->
                        when (route) {
                            is BackRoute -> navController.popBackStack()
                            is ExternalRoute-> context.startActivity( Intent(Intent.ACTION_VIEW, route.url.value.toUri()))
                            else -> navController.navigate(route)
                        }
                    })

                }

            }
        }
    }

}






