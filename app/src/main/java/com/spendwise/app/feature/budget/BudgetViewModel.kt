package com.spendwise.app.feature.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spendwise.app.core.common.AppConstants
import com.spendwise.app.core.data.BudgetRepository
import com.spendwise.app.core.model.MonthlyBudget
import com.spendwise.app.core.model.SyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Clock
import java.time.YearMonth
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository,
    private val clock: Clock,
) : ViewModel() {
    val month: YearMonth = YearMonth.now()

    val budget = repository.observeBudget(AppConstants.DEMO_USER_ID, month)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun save(limitText: String) {
        val limit = limitText.filter(Char::isDigit).toLongOrNull() ?: return
        if (limit <= 0) return

        viewModelScope.launch {
            repository.save(
                MonthlyBudget(
                    id = budget.value?.id ?: UUID.randomUUID().toString(),
                    userId = AppConstants.DEMO_USER_ID,
                    month = month,
                    limitMinor = limit,
                    currencyCode = AppConstants.DEFAULT_CURRENCY,
                    syncState = SyncState.PENDING_UPSERT,
                    updatedAt = clock.instant(),
                ),
            )
        }
    }
}

