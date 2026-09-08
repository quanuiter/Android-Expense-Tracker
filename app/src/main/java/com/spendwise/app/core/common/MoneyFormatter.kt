package com.spendwise.app.core.common

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object MoneyFormatter {
    private val vietnameseLocale = Locale.forLanguageTag("vi-VN")

    fun format(amountMinor: Long, currencyCode: String = AppConstants.DEFAULT_CURRENCY): String {
        return NumberFormat.getCurrencyInstance(vietnameseLocale).apply {
            currency = Currency.getInstance(currencyCode)
            maximumFractionDigits = 0
        }.format(amountMinor)
    }
}

