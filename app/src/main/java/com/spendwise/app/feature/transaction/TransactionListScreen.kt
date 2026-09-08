package com.spendwise.app.feature.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendwise.app.core.common.MoneyFormatter
import com.spendwise.app.core.model.Transaction
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    onAddTransaction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TransactionListViewModel = hiltViewModel(),
) {
    val transactions = viewModel.transactions.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Giao dịch") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransaction) {
                Icon(Icons.Outlined.Add, contentDescription = "Thêm giao dịch")
            }
        },
    ) { innerPadding ->
        if (transactions.isEmpty()) {
            EmptyTransactions(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(bottom = 96.dp),
            ) {
                items(transactions, key = { it.id }) { transaction ->
                    TransactionRow(transaction)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
                }
            }
        }
    }
}

@Composable
private fun EmptyTransactions(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.ReceiptLong,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Chưa có giao dịch",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 12.dp),
        )
        Text(
            text = "Nhấn nút + để thêm giao dịch đầu tiên.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun TransactionRow(transaction: Transaction) {
    val date = transaction.occurredAt
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.merchantName?.ifBlank { null } ?: transaction.category.displayName,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "${transaction.category.displayName} · $date",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = "-${MoneyFormatter.format(transaction.amountMinor, transaction.currencyCode)}",
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

