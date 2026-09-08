package com.spendwise.app.core.data

import com.spendwise.app.core.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface TransactionRepository {
    fun observeTransactions(userId: String): Flow<List<Transaction>>

    fun observeExpenseTotal(
        userId: String,
        fromInclusive: Instant,
        toExclusive: Instant,
    ): Flow<Long>

    suspend fun save(transaction: Transaction)
    suspend fun delete(transactionId: String)
}

