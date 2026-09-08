package com.spendwise.app.feature.receipt

import com.spendwise.app.core.model.ReceiptDraft

/** Pure Kotlin boundary: ML Kit extracts text; this parser extracts receipt fields. */
fun interface ReceiptTextParser {
    fun parse(rawText: String, imageUri: String?): ReceiptDraft
}

