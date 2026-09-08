package com.spendwise.app.core.data

import com.spendwise.app.core.database.dao.MonthlyBudgetDao
import com.spendwise.app.core.database.mapper.asEntity
import com.spendwise.app.core.database.mapper.asExternalModel
import com.spendwise.app.core.model.MonthlyBudget
import com.spendwise.app.core.model.SyncState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Clock
import java.time.YearMonth
import javax.inject.Inject

class OfflineFirstBudgetRepository @Inject constructor(
    private val budgetDao: MonthlyBudgetDao,
    private val clock: Clock,
) : BudgetRepository {
    override fun observeBudget(userId: String, month: YearMonth): Flow<MonthlyBudget?> =
        budgetDao.observeForMonth(userId, month).map { it?.asExternalModel() }

    override suspend fun save(budget: MonthlyBudget) {
        budgetDao.upsert(
            budget.copy(
                syncState = SyncState.PENDING_UPSERT,
                updatedAt = clock.instant(),
            ).asEntity(),
        )
    }
}

