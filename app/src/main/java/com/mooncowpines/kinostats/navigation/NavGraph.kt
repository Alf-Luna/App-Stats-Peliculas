package com.mooncowpines.kinostats.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.mooncowpines.kinostats.ui.screens.home.HomeScreen
import com.mooncowpines.kinostats.ui.screens.login.LoginScreen
import com.mooncowpines.kinostats.ui.screens.register.RegisterScreen
import com.mooncowpines.kinostats.ui.screens.recovery.RecoveryScreen
import com.mooncowpines.kinostats.ui.screens.reset.ResetScreen
import com.mooncowpines.kinostats.ui.screens.profile.ProfileScreen
import com.mooncowpines.kinostats.ui.screens.change.ChangeScreen
import com.mooncowpines.kinostats.ui.screens.listDetail.ListDetailScreen
import com.mooncowpines.kinostats.ui.screens.lists.ListsScreen
import com.mooncowpines.kinostats.ui.screens.log.LogScreen
import com.mooncowpines.kinostats.ui.screens.logDetail.LogDetailScreen
import com.mooncowpines.kinostats.ui.screens.stats.StatsScreen
import com.mooncowpines.kinostats.ui.screens.movieDetail.MovieDetailScreen
import com.mooncowpines.kinostats.ui.screens.search.SearchScreen


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

        composable(Route.Home.path) { backStackEntry ->
            val shouldRefresh = backStackEntry.savedStateHandle.get<Boolean>("refresh_home") ?: false
            HomeScreen(
                shouldRefresh = shouldRefresh,
                onRefreshDone = {
                    backStackEntry.savedStateHandle.set("refresh_home", false)
                },
                onMovieClick = { movieId ->
                    navController.navigate(Route.MovieDetail.createRoute(movieId))
                },
                onSearchSubmit = { query ->
                    navController.navigate(Route.Search.createRoute(query))
                }
            )
        }

        composable(Route.Profile.path) {
            ProfileScreen(
                onNavigateToAccountInfo = {
                    navController.navigate(Route.Change.path)
                },
                onNavigateToLogin = {
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

        composable(Route.Logs.path) {
            LogScreen(
                onNavigateToLogDetail = { movieId, logId ->
                    navController.navigate(Route.LogDetail.createRoute(movieId, logId))
                }
            )
        }

        composable(
            route = Route.MovieDetail.path,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
                MovieDetailScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToLog = { id ->
                        navController.navigate(Route.LogDetail.createRoute(id))
                    },
                    onDataModified = {
                        val homeEntry = try {
                            navController.getBackStackEntry(Route.Home.path)
                        } catch(e: Exception) { null }
                        homeEntry?.savedStateHandle?.set("refresh_home", true)
                    }
                )
        }


        composable(
            route = Route.LogDetail.path,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
                LogDetailScreen(
                    onNavigateBack = { navController.popBackStack() },

                )
            }

        composable(
            route = Route.Search.path,
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) {
            SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Route.MovieDetail.createRoute(movieId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Lists.path) {
            ListsScreen(
                onNavigateToListDetail = { listId ->
                    navController.navigate(Route.ListDetail.createRoute(listId))
                }
            )
        }

        composable(
            route = Route.ListDetail.path,
            arguments = listOf(navArgument("listId") { type = NavType.LongType })
        ) {
            ListDetailScreen(
                onNavigateBack = { navController.popBackStack() },
                onMovieClick = { movieId ->
                    navController.navigate(Route.MovieDetail.createRoute(movieId))
                },
                onDataModified = {
                    val homeEntry = try {
                        navController.getBackStackEntry(Route.Home.path)
                    } catch (e: Exception) { null }

                    homeEntry?.savedStateHandle?.set("refresh_home", true)
                }
            )
        }

        }
    }
