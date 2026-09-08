package com.spendwise.app.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.spendwise.app.core.model.SyncState
import java.time.Instant
import java.time.YearMonth

@Entity(
    tableName = "monthly_budgets",
    indices = [Index(value = ["userId", "month"], unique = true)],
)
data class MonthlyBudgetEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val month: YearMonth,
    val limitMinor: Long,
    val currencyCode: String,
    val syncState: SyncState,
    val updatedAt: Instant,
)

