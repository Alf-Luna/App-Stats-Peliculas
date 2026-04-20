package com.mooncowpines.kinostats.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.mooncowpines.kinostats.ui.screens.home.HomeScreen
import com.mooncowpines.kinostats.ui.screens.login.LoginScreen
import com.mooncowpines.kinostats.ui.screens.register.RegisterScreen
import com.mooncowpines.kinostats.ui.screens.recovery.RecoveryScreen
import com.mooncowpines.kinostats.ui.screens.change.ChangeScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Login.path) {

        composable(Route.Login.path) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Route.Register.path) },
                onNavigateToHome = {
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onNavigateToRecover = { navController.navigate(Route.Recovery.path)},
                onNavigateToChange = { navController.navigate(Route.Change.path)}
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Route.Home.path) {
                    popUpTo(Route.Login.path) { inclusive = true }
                }
                }
            )
        }

        composable(Route.Recovery.path) {
            RecoveryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Route.Change.path) {
            ChangeScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = { navController.navigate(Route.Login.path) {
                    popUpTo(Route.Login.path) {inclusive = true}
                }
                }
            )
        }

        composable(Route.Home.path) {
            HomeScreen()
        }
    }
}