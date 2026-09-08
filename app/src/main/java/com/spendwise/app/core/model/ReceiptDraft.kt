package com.spendwise.app.core.model

import java.time.LocalDate

/** OCR output stays editable and is not persisted as a transaction until confirmed. */
data class ReceiptDraft(
    val merchantName: String?,
    val purchaseDate: LocalDate?,
    val totalMinor: Long?,
    val currencyCode: String = "VND",
    val suggestedCategory: Category? = null,
    val rawText: String,
    val imageUri: String?,
)

