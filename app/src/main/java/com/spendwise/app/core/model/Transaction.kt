package com.spendwise.app.core.model

import java.time.Instant

data class Transaction(
    val id: String,
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
    val deletedAt: Instant? = null,
)

enum class TransactionType {
    EXPENSE,
    INCOME,
}

enum class SyncState {
    SYNCED,
    PENDING_UPSERT,
    PENDING_DELETE,
    FAILED,
}

