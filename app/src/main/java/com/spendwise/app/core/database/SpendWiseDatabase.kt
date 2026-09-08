package com.spendwise.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spendwise.app.core.database.dao.MonthlyBudgetDao
import com.spendwise.app.core.database.dao.TransactionDao
import com.spendwise.app.core.database.entity.MonthlyBudgetEntity
import com.spendwise.app.core.database.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, MonthlyBudgetEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(DatabaseConverters::class)
abstract class SpendWiseDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao
}

