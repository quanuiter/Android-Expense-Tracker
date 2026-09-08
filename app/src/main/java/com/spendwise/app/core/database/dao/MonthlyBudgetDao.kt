package com.spendwise.app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.spendwise.app.core.database.entity.MonthlyBudgetEntity
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

@Dao
interface MonthlyBudgetDao {
    @Query(
        """
        SELECT * FROM monthly_budgets
        WHERE userId = :userId AND month = :month
        LIMIT 1
        """,
    )
    fun observeForMonth(userId: String, month: YearMonth): Flow<MonthlyBudgetEntity?>

    @Upsert
    suspend fun upsert(budget: MonthlyBudgetEntity)
}

