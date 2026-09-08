package com.spendwise.app.feature.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spendwise.app.core.common.AppConstants
import com.spendwise.app.core.data.TransactionRepository
import com.spendwise.app.core.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    repository: TransactionRepository,
) : ViewModel() {
    val transactions: StateFlow<List<Transaction>> = repository
        .observeTransactions(AppConstants.DEMO_USER_ID)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )
}

