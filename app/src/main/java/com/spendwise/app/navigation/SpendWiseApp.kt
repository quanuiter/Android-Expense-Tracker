package com.spendwise.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spendwise.app.feature.auth.AuthScreen
import com.spendwise.app.feature.budget.BudgetScreen
import com.spendwise.app.feature.dashboard.DashboardScreen
import com.spendwise.app.feature.profile.ProfileScreen
import com.spendwise.app.feature.receipt.ReceiptCaptureScreen
import com.spendwise.app.feature.transaction.AddTransactionScreen
import com.spendwise.app.feature.transaction.TransactionListScreen

@Composable
fun SpendWiseApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = topLevelDestinations.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    topLevelDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(Routes.DASHBOARD) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.label,
                                )
                            },
                            label = { Text(destination.label) },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.AUTH,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Routes.AUTH) {
                AuthScreen(
                    onContinueLocal = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.AUTH) { inclusive = true }
                        }
                    },
                )
            }
            composable(Routes.DASHBOARD) {
                DashboardScreen()
            }
            composable(Routes.TRANSACTIONS) {
                TransactionListScreen(
                    onAddTransaction = { navController.navigate(Routes.ADD_TRANSACTION) },
                )
            }
            composable(Routes.ADD_TRANSACTION) {
                AddTransactionScreen(onBack = navController::navigateUp)
            }
            composable(Routes.RECEIPT) {
                ReceiptCaptureScreen()
            }
            composable(Routes.BUDGET) {
                BudgetScreen()
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
        }
    }
}

