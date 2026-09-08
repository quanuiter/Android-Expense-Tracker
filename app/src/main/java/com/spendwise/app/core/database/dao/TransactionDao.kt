package com.spendwise.app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.spendwise.app.core.database.entity.TransactionEntity
import com.spendwise.app.core.model.SyncState
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface TransactionDao {
    @Query(
        """
        SELECT * FROM transactions
        WHERE userId = :userId AND deletedAt IS NULL
        ORDER BY occurredAt DESC
        """,
    )
    fun observeActive(userId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): TransactionEntity?

    @Query(
        """
        SELECT COALESCE(SUM(amountMinor), 0) FROM transactions
        WHERE userId = :userId
          AND type = 'EXPENSE'
          AND deletedAt IS NULL
          AND occurredAt >= :fromInclusive
          AND occurredAt < :toExclusive
        """,
    )
    fun observeExpenseTotal(
        userId: String,
        fromInclusive: Instant,
        toExclusive: Instant,
    ): Flow<Long>

    @Query(
        """
        SELECT * FROM transactions
        WHERE userId = :userId AND syncState != 'SYNCED'
        ORDER BY updatedAt ASC
        """,
    )
    suspend fun getPendingSync(userId: String): List<TransactionEntity>

    @Upsert
    suspend fun upsert(transaction: TransactionEntity)

    @Query(
        """
        UPDATE transactions
        SET deletedAt = :deletedAt,
            updatedAt = :deletedAt,
            syncState = :syncState
        WHERE id = :id
        """,
    )
    suspend fun markDeleted(
        id: String,
        deletedAt: Instant,
        syncState: SyncState = SyncState.PENDING_DELETE,
    )
}

