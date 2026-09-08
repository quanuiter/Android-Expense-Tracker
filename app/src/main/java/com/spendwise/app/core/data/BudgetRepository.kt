package com.spendwise.app.core.data

import com.spendwise.app.core.model.MonthlyBudget
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface BudgetRepository {
    fun observeBudget(userId: String, month: YearMonth): Flow<MonthlyBudget?>
    suspend fun save(budget: MonthlyBudget)
}

