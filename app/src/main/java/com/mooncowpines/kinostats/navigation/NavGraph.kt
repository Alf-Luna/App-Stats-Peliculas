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
                }
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateHome = { navController.navigate(Route.Home.path)
                }
            )
        }

        composable(Route.Home.path) {
            HomeScreen()
        }
    }
}