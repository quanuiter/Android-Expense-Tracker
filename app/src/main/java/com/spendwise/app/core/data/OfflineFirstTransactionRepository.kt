package com.spendwise.app.core.data

import com.spendwise.app.core.database.dao.TransactionDao
import com.spendwise.app.core.database.mapper.asEntity
import com.spendwise.app.core.database.mapper.asExternalModel
import com.spendwise.app.core.model.SyncState
import com.spendwise.app.core.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val clock: Clock,
) : TransactionRepository {
    override fun observeTransactions(userId: String): Flow<List<Transaction>> =
        transactionDao.observeActive(userId).map { entities ->
            entities.map { it.asExternalModel() }
        }

    override fun observeExpenseTotal(
        userId: String,
        fromInclusive: Instant,
        toExclusive: Instant,
    ): Flow<Long> = transactionDao.observeExpenseTotal(userId, fromInclusive, toExclusive)

    override suspend fun save(transaction: Transaction) {
        transactionDao.upsert(
            transaction.copy(
                syncState = SyncState.PENDING_UPSERT,
                updatedAt = clock.instant(),
            ).asEntity(),
        )
    }

    override suspend fun delete(transactionId: String) {
        transactionDao.markDeleted(transactionId, clock.instant())
    }
}

