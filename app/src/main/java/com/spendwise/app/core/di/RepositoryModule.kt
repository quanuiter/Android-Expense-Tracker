package com.spendwise.app.core.di

import com.spendwise.app.core.data.BudgetRepository
import com.spendwise.app.core.data.OfflineFirstBudgetRepository
import com.spendwise.app.core.data.OfflineFirstTransactionRepository
import com.spendwise.app.core.data.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        implementation: OfflineFirstTransactionRepository,
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindBudgetRepository(
        implementation: OfflineFirstBudgetRepository,
    ): BudgetRepository
}

