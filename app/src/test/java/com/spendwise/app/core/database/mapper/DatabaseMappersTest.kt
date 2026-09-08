package com.spendwise.app.core.database.mapper

import com.spendwise.app.core.model.Category
import com.spendwise.app.core.model.SyncState
import com.spendwise.app.core.model.Transaction
import com.spendwise.app.core.model.TransactionType
import java.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Test

class DatabaseMappersTest {
    @Test
    fun transaction_roundTripsThroughEntity() {
        val now = Instant.parse("2026-09-08T12:00:00Z")
        val transaction = Transaction(
            id = "transaction-1",
            userId = "user-1",
            type = TransactionType.EXPENSE,
            amountMinor = 89_000,
            currencyCode = "VND",
            category = Category.FOOD,
            merchantName = "Highlands Coffee",
            note = null,
            occurredAt = now,
            receiptImageUri = "content://receipt/1",
            rawOcrText = "TOTAL 89,000",
            syncState = SyncState.PENDING_UPSERT,
            createdAt = now,
            updatedAt = now,
        )

        assertEquals(transaction, transaction.asEntity().asExternalModel())
    }
}

