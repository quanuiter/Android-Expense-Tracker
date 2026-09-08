package com.spendwise.app.core.di

import android.content.Context
import androidx.room.Room
import com.spendwise.app.core.database.SpendWiseDatabase
import com.spendwise.app.core.database.dao.MonthlyBudgetDao
import com.spendwise.app.core.database.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SpendWiseDatabase =
        Room.databaseBuilder(
            context,
            SpendWiseDatabase::class.java,
            "spendwise.db",
        ).build()

    @Provides
    fun provideTransactionDao(database: SpendWiseDatabase): TransactionDao =
        database.transactionDao()

    @Provides
    fun provideBudgetDao(database: SpendWiseDatabase): MonthlyBudgetDao =
        database.monthlyBudgetDao()

    @Provides
    @Singleton
    fun provideClock(): Clock = Clock.systemDefaultZone()
}

