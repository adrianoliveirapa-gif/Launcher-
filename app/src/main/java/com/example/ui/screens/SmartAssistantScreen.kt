package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmartAssistantScreen(
    onOpenWeather: () -> Unit,
    onOpenCalculator: () -> Unit,
    onOpenNotes: () -> Unit,
    onOpenSettings: () -> Unit,
    onCleanRam: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("smart_assistant_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 120.dp)
    ) {
        // Assistant Header
        item {
            Column {
                Text(
                    text = "ColorOS Akıllı Asistan",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Oppo Android 8 Oreo • Ahmet Koyak",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }
        }

        // Quick Functions Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.88f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Hızlı İşlevler",
                        color = Color(0xFF1E293B),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionItem(
                            icon = Icons.Default.Speed,
                            label = "RAM Hızlandır",
                            color = Color(0xFF0284C7),
                            onClick = onCleanRam
                        )
                        QuickActionItem(
                            icon = Icons.Default.Calculate,
                            label = "Hesapla",
                            color = Color(0xFFF59E0B),
                            onClick = onOpenCalculator
                        )
                        QuickActionItem(
                            icon = Icons.Default.EditNote,
                            label = "Yeni Not",
                            color = Color(0xFF10B981),
                            onClick = onOpenNotes
                        )
                        QuickActionItem(
                            icon = Icons.Default.Settings,
                            label = "Ayarlar",
                            color = Color(0xFF6366F1),
                            onClick = onOpenSettings
                        )
                    }
                }
            }
        }

        // Weather Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenWeather() },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.88f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "İstanbul Hava Durumu",
                            color = Color(0xFF1E293B),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Güneşli ve Açık • En Yüksek 26°",
                            color = Color(0xFF64748B),
                            fontSize = 13.sp
                        )
                    }
                    Text(
                        text = "24°C",
                        color = Color(0xFF0284C7),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }

        // Steps Tracker Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.88f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Adım Takibi",
                            color = Color(0xFF1E293B),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hedef: 8.000",
                            color = Color(0xFF64748B),
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "5.420 Adım",
                        color = Color(0xFF16A34A),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 5420f / 8000f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF16A34A),
                        trackColor = Color(0xFFDCFCE7)
                    )
                }
            }
        }

        // System Health Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.88f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Oppo Donanım Durumu",
                        color = Color(0xFF1E293B),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatusIndicator("RAM", "2.8 GB / 6 GB", 0.46f)
                        StatusIndicator("Depolama", "43.8 GB / 128 GB", 0.34f)
                        StatusIndicator("Pil Sağlığı", "%81 Harika", 0.81f)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, color = Color(0xFF475569), fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun StatusIndicator(title: String, subtitle: String, progress: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, color = Color(0xFF64748B), fontSize = 12.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = subtitle, color = Color(0xFF1E293B), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}
