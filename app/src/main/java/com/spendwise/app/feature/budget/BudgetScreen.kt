package com.spendwise.app.feature.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    viewModel: BudgetViewModel = hiltViewModel(),
) {
    val budget = viewModel.budget.collectAsStateWithLifecycle().value
    var limit by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(budget?.id) {
        budget?.let { limit = it.limitMinor.toString() }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Ngân sách") }) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Ngân sách tháng ${viewModel.month.monthValue}/${viewModel.month.year}",
                style = MaterialTheme.typography.titleMedium,
            )
            OutlinedTextField(
                value = limit,
                onValueChange = { limit = it.filter(Char::isDigit) },
                label = { Text("Hạn mức") },
                suffix = { Text("₫") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = { viewModel.save(limit) },
                enabled = limit.toLongOrNull()?.let { it > 0 } == true,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(if (budget == null) "Đặt ngân sách" else "Cập nhật ngân sách")
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        Icons.Outlined.NotificationsActive,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Text("Quy tắc cảnh báo dự kiến")
                    Text(
                        "Thông báo khi đạt 80% và khi vượt 100% hạn mức.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

