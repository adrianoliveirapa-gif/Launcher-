package com.example.ui.modals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

data class QuickToggle(
    val id: String,
    val title: String,
    val icon: ImageVector,
    var isEnabled: Boolean
)

@Composable
fun ControlCenterDrawer(
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    var brightness by remember { mutableFloatStateOf(0.75f) }
    val toggles = remember {
        mutableStateListOf(
            QuickToggle("wifi", "Wi-Fi", Icons.Default.Wifi, true),
            QuickToggle("cellular", "Mobil Veri", Icons.Default.SignalCellular4Bar, true),
            QuickToggle("sound", "Sessiz", Icons.Default.VolumeOff, false),
            QuickToggle("torch", "Fener", Icons.Default.FlashlightOn, false),
            QuickToggle("bluetooth", "Bluetooth", Icons.Default.Bluetooth, true),
            QuickToggle("rotate", "Döndürme", Icons.Default.ScreenRotation, false),
            QuickToggle("airplane", "Uçak Modu", Icons.Default.AirplanemodeActive, false),
            QuickToggle("shield", "Gece Kalkanı", Icons.Default.Nightlight, false)
        )
    }

    val dateFormatted = remember {
        SimpleDateFormat("d MMMM EEEE", Locale("tr")).format(Date())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.65f))
            .clickable { onDismiss() }
            .testTag("control_center_drawer")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                .background(Color(0xFF1E293B).copy(alpha = 0.98f))
                .statusBarsPadding()
                .padding(20.dp)
                .clickable { /* prevent click-through */ }
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = dateFormatted,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ColorOS Kontrol Merkezi",
                        color = Color(0xFF38BDF8),
                        fontSize = 12.sp
                    )
                }

                Row {
                    IconButton(onClick = {
                        onDismiss()
                        onOpenSettings()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Ayarlar",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Kapat",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Quick Toggles Grid (4x2)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(toggles) { toggle ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            val idx = toggles.indexOf(toggle)
                            if (idx >= 0) {
                                toggles[idx] = toggle.copy(isEnabled = !toggle.isEnabled)
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    if (toggle.isEnabled) Color(0xFF0284C7) else Color.White.copy(alpha = 0.12f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = toggle.icon,
                                contentDescription = toggle.title,
                                tint = if (toggle.isEnabled) Color.White else Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = toggle.title,
                            color = Color.White,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Brightness Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.BrightnessLow,
                    contentDescription = "Düşük Parlaklık",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
                Slider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF38BDF8),
                        activeTrackColor = Color(0xFF0284C7),
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
                Icon(
                    imageVector = Icons.Default.BrightnessHigh,
                    contentDescription = "Yüksek Parlaklık",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
