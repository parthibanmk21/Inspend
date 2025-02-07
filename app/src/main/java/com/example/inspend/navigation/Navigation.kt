package com.example.inspend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import com.example.inspend.HomePage
import com.example.inspend.AllTransactions

object NavigationRoutes {
    const val HOME = "home"
    const val ALL_TRANSACTIONS = "alltransactions"
    const val ADD_TRANSACTION = "addtransaction"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.HOME
    ) {
        composable(NavigationRoutes.HOME) {
            HomePage(navController = navController)
        }
        
        composable(
            route = NavigationRoutes.ALL_TRANSACTIONS,
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

        composable(NavigationRoutes.ADD_TRANSACTION) {
            // TODO: Add AddTransaction screen when it's created
            // AddTransaction(navController = navController)
        }
    }
} 