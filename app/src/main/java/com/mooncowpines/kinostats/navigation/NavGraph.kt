package com.mooncowpines.kinostats.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.mooncowpines.kinostats.data.FakeMovieApi
import com.mooncowpines.kinostats.ui.screens.home.HomeScreen
import com.mooncowpines.kinostats.ui.screens.login.LoginScreen
import com.mooncowpines.kinostats.ui.screens.register.RegisterScreen
import com.mooncowpines.kinostats.ui.screens.recovery.RecoveryScreen
import com.mooncowpines.kinostats.ui.screens.reset.ResetScreen
import com.mooncowpines.kinostats.ui.screens.profile.ProfileScreen
import com.mooncowpines.kinostats.ui.screens.change.ChangeScreen
import com.mooncowpines.kinostats.ui.screens.review.ReviewScreen
import com.mooncowpines.kinostats.ui.screens.stats.StatsScreen
import com.mooncowpines.kinostats.ui.screens.MovieDetail.MovieDetailScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Route.Login.path,
        modifier = modifier
    ) {

        composable(Route.Login.path) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Route.Register.path) },
                onNavigateToHome = {
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onNavigateToRecover = { navController.navigate(Route.Recovery.path)},
                onNavigateToReset = { navController.navigate(Route.Reset.path)},
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Route.Home.path) {
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

        composable(Route.Reset.path) {
            ResetScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Route.Login.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.Change.path) {
            ChangeScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Home.path) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.Home.path) {
            HomeScreen(
                movies = FakeMovieApi.allMoviesSync,
                movie = FakeMovieApi.getMovieByIdSync(5) ?: FakeMovieApi.allMoviesSync.first(),
                onMovieClick = { movieId ->
                    navController.navigate(Route.MovieDetail.createRoute(movieId))
                }
            )
        }

        composable(Route.Profile.path) {
            ProfileScreen(
                onNavigateToAccountInfo = {
                    navController.navigate(Route.Change.path)
                },
                onLogout = {
                    navController.navigate(Route.Login.path) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.Stats.path) {
            StatsScreen(
               onMovieClick =  { movieId ->
                    navController.navigate(Route.MovieDetail.createRoute(movieId))
                }
            )
        }

        composable(
            route = Route.MovieDetail.path,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 1
            val movie = FakeMovieApi.getMovieByIdSync(movieId)

            if (movie != null) {
                MovieDetailScreen(
                    movie = movie,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToLog = { id ->
                        navController.navigate(Route.Review.createRoute(id))
                    }
                )
            }
        }

        composable(
            route = Route.Review.path,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 1
            val movie = FakeMovieApi.getMovieByIdSync(movieId)

            if (movie != null) {
                ReviewScreen(
                    movie = movie,
                    onNavigateBack = { navController.popBackStack() },

                )
            }
        }
    }
}