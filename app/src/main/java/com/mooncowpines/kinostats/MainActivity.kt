package com.mooncowpines.kinostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mooncowpines.kinostats.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.mooncowpines.kinostats.ui.theme.KinoStatsTheme

import com.mooncowpines.kinostats.navigation.Route
import com.mooncowpines.kinostats.ui.components.KinoBottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KinoStatsTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val screensWithBottomBar = listOf(
                    Route.Home.path,
                    Route.Stats.path,
                    Route.Lists.path,
                    Route.Logs.path,
                    Route.Profile.path)
                val showBottomBar = currentRoute in screensWithBottomBar

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (showBottomBar) {
                        KinoBottomBar(
                            currentRoute = currentRoute ?: "",
                            onNavigate = { route -> navController.navigate(route) {
                                popUpTo(Route.Home.path) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            } })
                    } }
                    ){ innerPadding ->
                    NavGraph(modifier = Modifier.padding(innerPadding), navController = navController)
                }
            }
        }
    }
}

