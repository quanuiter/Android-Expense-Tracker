package com.spendwise.app.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.spendwise.app.core.model.Category
import com.spendwise.app.core.model.SyncState
import com.spendwise.app.core.model.TransactionType
import java.time.Instant

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["userId", "occurredAt"]),
        Index(value = ["userId", "syncState"]),
    ],
)
data class TransactionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val type: TransactionType,
    val amountMinor: Long,
    val currencyCode: String,
    val category: Category,
    val merchantName: String?,
    val note: String?,
    val occurredAt: Instant,
    val receiptImageUri: String?,
    val rawOcrText: String?,
    val syncState: SyncState,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant?,
)

