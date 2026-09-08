package com.spendwise.app.core.model

import java.time.Instant
import java.time.YearMonth

data class MonthlyBudget(
    val id: String,
    val userId: String,
    val month: YearMonth,
    val limitMinor: Long,
    val currencyCode: String,
    val syncState: SyncState,
    val updatedAt: Instant,
)

