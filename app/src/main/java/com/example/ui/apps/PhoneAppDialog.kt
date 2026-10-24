package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
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

@Composable
fun PhoneAppDialog(
    onDismiss: () -> Unit
) {
    var dialNumber by remember { mutableStateOf("") }
    var isInCall by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .testTag("phone_app_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
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
                        tint = Color(0xFF1E293B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Telefon",
                    color = Color(0xFF1E293B),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isInCall) {
                // In-call screen
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dialNumber.take(1).ifEmpty { "A" },
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = dialNumber.ifEmpty { "Bilinmeyen Numara" },
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Aranıyor...",
                        fontSize = 15.sp,
                        color = Color(0xFF16A34A)
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    IconButton(
                        onClick = { isInCall = false },
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDC2626))
                    ) {
                        Icon(
                            imageVector = Icons.Default.CallEnd,
                            contentDescription = "Aramayı Sonlandır",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            } else {
                // Dialed Number Display
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dialNumber.ifEmpty { "Numara tuşlayın" },
                        fontSize = if (dialNumber.isEmpty()) 20.sp else 32.sp,
                        fontWeight = if (dialNumber.isEmpty()) FontWeight.Normal else FontWeight.Bold,
                        color = if (dialNumber.isEmpty()) Color(0xFF94A3B8) else Color(0xFF1E293B)
                    )
                }

                // Keypad
                val dialKeys = listOf(
                    listOf("1" to "", "2" to "ABC", "3" to "DEF"),
                    listOf("4" to "GHI", "5" to "JKL", "6" to "MNO"),
                    listOf("7" to "PQRS", "8" to "TUV", "9" to "WXYZ"),
                    listOf("*" to "", "0" to "+", "#" to "")
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    dialKeys.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            row.forEach { (digit, letters) ->
                                Box(
                                    modifier = Modifier
                                        .size(72.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .clickable { dialNumber += digit },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = digit,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF1E293B)
                                        )
                                        if (letters.isNotEmpty()) {
                                            Text(
                                                text = letters,
                                                fontSize = 9.sp,
                                                color = Color(0xFF94A3B8),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Bottom Row: Empty, Call Button, Backspace
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.size(64.dp))

                        // Green Call Button
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E))
                                .clickable {
                                    if (dialNumber.isNotEmpty()) {
                                        isInCall = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Ara",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Backspace
                        IconButton(
                            onClick = {
                                if (dialNumber.isNotEmpty()) {
                                    dialNumber = dialNumber.dropLast(1)
                                }
                            },
                            modifier = Modifier.size(64.dp)
                        ) {
                            if (dialNumber.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Backspace,
                                    contentDescription = "Geri Al",
                                    tint = Color(0xFF64748B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
