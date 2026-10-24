package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ColorOSCleanerWidget(
    onCleanRam: () -> Unit,
    onCleanJunk: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var isOptimizingRam by remember { mutableStateOf(false) }
    var isCleaningJunk by remember { mutableStateOf(false) }
    var ramStatusText by remember { mutableStateOf("Optimu...") }
    var junkStatusText by remember { mutableStateOf("0B") }
    var showRamSuccess by remember { mutableStateOf(false) }
    var showJunkSuccess by remember { mutableStateOf(false) }

    // Rocket spin animation
    val infiniteTransition = rememberInfiniteTransition(label = "rocket_spin")
    val rocketAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rocket_angle"
    )

    Card(
        modifier = modifier
            .width(168.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(22.dp))
            .testTag("cleaner_widget"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.88f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 14.dp)
                .fillMaxWidth()
        ) {
            // Row 1: RAM Optimization
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        if (!isOptimizingRam) {
                            isOptimizingRam = true
                            ramStatusText = "Temizleniyor..."
                            coroutineScope.launch {
                                delay(1200)
                                isOptimizingRam = false
                                ramStatusText = "Optimum"
                                showRamSuccess = true
                                onCleanRam()
                                delay(2500)
                                showRamSuccess = false
                                ramStatusText = "Optimu..."
                            }
                        }
                    }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = ramStatusText,
                        color = Color(0xFF1E293B),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = "RAM kullanımı",
                        color = Color(0xFF64748B),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                // Rocket Icon Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFE0F2FE), Color(0xFFBAE6FD))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (showRamSuccess) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Optimize Edildi",
                            tint = Color(0xFF0284C7),
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = "RAM Hızlandır",
                            tint = Color(0xFF0284C7),
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(if (isOptimizingRam) rocketAngle else 0f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.6.dp)
                    .background(Color(0xFFE2E8F0))
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Row 2: Junk Clean
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        if (!isCleaningJunk) {
                            isCleaningJunk = true
                            junkStatusText = "Taranıyor.."
                            coroutineScope.launch {
                                delay(1000)
                                isCleaningJunk = false
                                junkStatusText = "0B Temiz"
                                showJunkSuccess = true
                                onCleanJunk()
                                delay(2500)
                                showJunkSuccess = false
                                junkStatusText = "0B"
                            }
                        }
                    }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = junkStatusText,
                        color = Color(0xFF1E293B),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = "Gereksiz veriler",
                        color = Color(0xFF64748B),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                // Broom / Magic Sparkle Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFDCFCE7), Color(0xFFBBF7D0))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (showJunkSuccess) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Temizlendi",
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Gereksiz Dosyaları Temizle",
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
