package com.spendwise.app.feature.receipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptCaptureScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Quét hóa đơn") }) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CameraAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Text("CameraX + ML Kit", fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "Dependency và quyền camera đã có trong sườn. Luồng preview, chụp ảnh và OCR sẽ được nối ở Sprint 2.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Button(onClick = {}, enabled = false) {
                        Text("Mở camera — sắp triển khai")
                    }
                }
            }

            listOf(
                "Chụp hoặc chọn ảnh hóa đơn",
                "ML Kit đọc nội dung trên thiết bị",
                "Parser lấy cửa hàng, ngày và tổng tiền",
                "Người dùng xác nhận trước khi lưu",
            ).forEachIndexed { index, step ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = if (index == 0) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                    )
                    Text(step)
                }
            }
        }
    }
}

