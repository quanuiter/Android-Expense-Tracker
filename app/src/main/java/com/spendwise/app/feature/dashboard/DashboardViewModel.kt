package com.spendwise.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spendwise.app.core.common.AppConstants
import com.spendwise.app.core.data.BudgetRepository
import com.spendwise.app.core.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

data class DashboardUiState(
    val month: YearMonth = YearMonth.now(),
    val spentMinor: Long = 0,
    val budgetMinor: Long? = null,
) {
    val remainingMinor: Long?
        get() = budgetMinor?.minus(spentMinor)

    val budgetProgress: Float
        get() = if (budgetMinor == null || budgetMinor <= 0) 0f
        else (spentMinor.toFloat() / budgetMinor).coerceIn(0f, 1f)
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    budgetRepository: BudgetRepository,
) : ViewModel() {
    private val month = YearMonth.now()
    private val zoneId = ZoneId.systemDefault()
    private val from = month.atDay(1).atStartOfDay(zoneId).toInstant()
    private val to = month.plusMonths(1).atDay(1).atStartOfDay(zoneId).toInstant()

    val uiState = combine(
        transactionRepository.observeExpenseTotal(AppConstants.DEMO_USER_ID, from, to),
        budgetRepository.observeBudget(AppConstants.DEMO_USER_ID, month),
    ) { spent, budget ->
        DashboardUiState(
            month = month,
            spentMinor = spent,
            budgetMinor = budget?.limitMinor,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState(month = month),
    )
}

