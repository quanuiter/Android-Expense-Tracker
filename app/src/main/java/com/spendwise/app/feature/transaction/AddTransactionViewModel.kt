package com.spendwise.app.feature.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spendwise.app.core.common.AppConstants
import com.spendwise.app.core.data.TransactionRepository
import com.spendwise.app.core.model.Category
import com.spendwise.app.core.model.SyncState
import com.spendwise.app.core.model.Transaction
import com.spendwise.app.core.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Clock
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val clock: Clock,
) : ViewModel() {
    fun save(
        amountText: String,
        merchantName: String,
        note: String,
        category: Category,
        onSaved: () -> Unit,
    ) {
        val amount = amountText.filter(Char::isDigit).toLongOrNull() ?: return
        if (amount <= 0) return

        viewModelScope.launch {
            val now = clock.instant()
            repository.save(
                Transaction(
                    id = UUID.randomUUID().toString(),
                    userId = AppConstants.DEMO_USER_ID,
                    type = TransactionType.EXPENSE,
                    amountMinor = amount,
                    currencyCode = AppConstants.DEFAULT_CURRENCY,
                    category = category,
                    merchantName = merchantName.trim().ifBlank { null },
                    note = note.trim().ifBlank { null },
                    occurredAt = now,
                    receiptImageUri = null,
                    rawOcrText = null,
                    syncState = SyncState.PENDING_UPSERT,
                    createdAt = now,
                    updatedAt = now,
                ),
            )
            onSaved()
        }
    }
}

