package com.spendwise.app.core.database

import androidx.room.TypeConverter
import com.spendwise.app.core.model.Category
import com.spendwise.app.core.model.SyncState
import com.spendwise.app.core.model.TransactionType
import java.time.Instant
import java.time.YearMonth

class DatabaseConverters {
    @TypeConverter
    fun instantToLong(value: Instant?): Long? = value?.toEpochMilli()

    @TypeConverter
    fun longToInstant(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)

    @TypeConverter
    fun yearMonthToString(value: YearMonth?): String? = value?.toString()

    @TypeConverter
    fun stringToYearMonth(value: String?): YearMonth? = value?.let(YearMonth::parse)

    @TypeConverter
    fun categoryToString(value: Category?): String? = value?.name

    @TypeConverter
    fun stringToCategory(value: String?): Category? = value?.let(Category::valueOf)

    @TypeConverter
    fun transactionTypeToString(value: TransactionType?): String? = value?.name

    @TypeConverter
    fun stringToTransactionType(value: String?): TransactionType? = value?.let(TransactionType::valueOf)

    @TypeConverter
    fun syncStateToString(value: SyncState?): String? = value?.name

    @TypeConverter
    fun stringToSyncState(value: String?): SyncState? = value?.let(SyncState::valueOf)
}

