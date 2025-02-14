package com.example.inspend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import com.example.inspend.HomePage
import com.example.inspend.AllTransactions
import com.example.inspend.SettingsScreen

sealed class NavigationRoutes(val route: String) {
    object Home : NavigationRoutes("home")
    object Settings : NavigationRoutes("settings")
    object AddTransaction : NavigationRoutes("addtransaction")
    object AllTransactions : NavigationRoutes("alltransactions")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home.route
    ) {
        composable(NavigationRoutes.Home.route) {
            HomePage(navController = navController)
        }
        
        composable(
            route = NavigationRoutes.AllTransactions.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            AllTransactions(navController = navController)
        }

        composable(NavigationRoutes.Settings.route) {
            SettingsScreen()
        }

        composable(NavigationRoutes.AddTransaction.route) {
            // TODO: Add AddTransaction screen when it's created
            // AddTransaction(navController = navController)
        }
    }
} 