package com.spendwise.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val AUTH = "auth"
    const val DASHBOARD = "dashboard"
    const val TRANSACTIONS = "transactions"
    const val ADD_TRANSACTION = "transactions/add"
    const val RECEIPT = "receipt"
    const val BUDGET = "budget"
    const val PROFILE = "profile"
}

data class TopLevelDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

val topLevelDestinations = listOf(
    TopLevelDestination(Routes.DASHBOARD, "Tổng quan", Icons.Outlined.Dashboard),
    TopLevelDestination(Routes.TRANSACTIONS, "Giao dịch", Icons.Outlined.ReceiptLong),
    TopLevelDestination(Routes.RECEIPT, "Quét", Icons.Outlined.DocumentScanner),
    TopLevelDestination(Routes.BUDGET, "Ngân sách", Icons.Outlined.AccountBalanceWallet),
    TopLevelDestination(Routes.PROFILE, "Tài khoản", Icons.Outlined.AccountCircle),
)

