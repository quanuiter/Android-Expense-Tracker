package com.spendwise.app.core.database.mapper

import com.spendwise.app.core.database.entity.MonthlyBudgetEntity
import com.spendwise.app.core.database.entity.TransactionEntity
import com.spendwise.app.core.model.MonthlyBudget
import com.spendwise.app.core.model.Transaction

fun TransactionEntity.asExternalModel(): Transaction = Transaction(
    id = id,
    userId = userId,
    type = type,
    amountMinor = amountMinor,
    currencyCode = currencyCode,
    category = category,
    merchantName = merchantName,
    note = note,
    occurredAt = occurredAt,
    receiptImageUri = receiptImageUri,
    rawOcrText = rawOcrText,
    syncState = syncState,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
)

fun Transaction.asEntity(): TransactionEntity = TransactionEntity(
    id = id,
    userId = userId,
    type = type,
    amountMinor = amountMinor,
    currencyCode = currencyCode,
    category = category,
    merchantName = merchantName,
    note = note,
    occurredAt = occurredAt,
    receiptImageUri = receiptImageUri,
    rawOcrText = rawOcrText,
    syncState = syncState,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
)

fun MonthlyBudgetEntity.asExternalModel(): MonthlyBudget = MonthlyBudget(
    id = id,
    userId = userId,
    month = month,
    limitMinor = limitMinor,
    currencyCode = currencyCode,
    syncState = syncState,
    updatedAt = updatedAt,
)

fun MonthlyBudget.asEntity(): MonthlyBudgetEntity = MonthlyBudgetEntity(
    id = id,
    userId = userId,
    month = month,
    limitMinor = limitMinor,
    currencyCode = currencyCode,
    syncState = syncState,
    updatedAt = updatedAt,
)

