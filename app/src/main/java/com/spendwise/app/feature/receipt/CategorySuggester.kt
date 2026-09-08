package com.spendwise.app.feature.receipt

import com.spendwise.app.core.model.Category

data class CategorySuggestion(
    val category: Category,
    val confidence: Float,
    val reason: String,
)

/** Starts as keyword rules and can later be replaced without changing the receipt UI. */
fun interface CategorySuggester {
    fun suggest(merchantName: String?, rawText: String): CategorySuggestion
}

