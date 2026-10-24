package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ClockAppDialog(
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Saat, 1: Kronometre, 2: Sayaç

    // Stopwatch state
    var stopwatchRunning by remember { mutableStateOf(false) }
    var elapsedMillis by remember { mutableLongStateOf(0L) }
    val laps = remember { mutableStateListOf<Long>() }

    LaunchedEffect(stopwatchRunning) {
        if (stopwatchRunning) {
            val startTime = System.currentTimeMillis() - elapsedMillis
            while (stopwatchRunning) {
                elapsedMillis = System.currentTimeMillis() - startTime
                delay(10)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .statusBarsPadding()
            .testTag("clock_app_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Saat",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Tabs (Saat, Kronometre, Sayaç)
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Color(0xFF38BDF8),
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Dünya Saati", color = if (selectedTab == 0) Color(0xFF38BDF8) else Color.White.copy(alpha = 0.6f)) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Kronometre", color = if (selectedTab == 1) Color(0xFF38BDF8) else Color.White.copy(alpha = 0.6f)) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Sayaç", color = if (selectedTab == 2) Color(0xFF38BDF8) else Color.White.copy(alpha = 0.6f)) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (selectedTab) {
                0 -> {
                    // World Clock
                    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    var nowString by remember { mutableStateOf(timeFormat.format(Date())) }
                    LaunchedEffect(Unit) {
                        while (true) {
                            nowString = timeFormat.format(Date())
                            delay(1000)
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = nowString,
                            color = Color.White,
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Light
                        )
                        Text(
                            text = "İstanbul, Türkiye (GMT+3)",
                            color = Color(0xFF38BDF8),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Londra", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = "-2 saat", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Tokyo", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = "+6 saat", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "New York", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = "-7 saat", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Stopwatch
                    val minutes = (elapsedMillis / 60000) % 60
                    val seconds = (elapsedMillis / 1000) % 60
                    val millis = (elapsedMillis % 1000) / 10

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = String.format("%02d:%02d.%02d", minutes, seconds, millis),
                            color = Color.White,
                            fontSize = 54.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        // Controls
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    stopwatchRunning = false
                                    elapsedMillis = 0L
                                    laps.clear()
                                },
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Sıfırla", tint = Color.White)
                            }

                            IconButton(
                                onClick = { stopwatchRunning = !stopwatchRunning },
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(CircleShape)
                                    .background(if (stopwatchRunning) Color(0xFFE53935) else Color(0xFF0284C7))
                            ) {
                                Icon(
                                    imageVector = if (stopwatchRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (stopwatchRunning) "Durdur" else "Başlat",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    if (stopwatchRunning) laps.add(0, elapsedMillis)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.15f)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text("Tur", color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Laps List
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(laps) { idx, lapMs ->
                                val lMin = (lapMs / 60000) % 60
                                val lSec = (lapMs / 1000) % 60
                                val lMs = (lapMs % 1000) / 10
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Tur ${laps.size - idx}", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                                    Text(
                                        text = String.format("%02d:%02d.%02d", lMin, lSec, lMs),
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Timer
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "05:00",
                            color = Color.White,
                            fontSize = 58.sp,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Hazır Sayaç", color = Color(0xFF38BDF8), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(28.dp))
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Sayacı Başlat (5 Dk)", color = Color.White, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}
