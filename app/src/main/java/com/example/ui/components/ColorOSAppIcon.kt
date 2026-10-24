package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppItem
import kotlinx.coroutines.delay
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ColorOSAppIcon(
    app: AppItem,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    iconSize: Int = 60
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1.0f,
        label = "icon_press_scale"
    )

    Column(
        modifier = modifier
            .scale(scale)
            .pointerInput(app.id) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() },
                    onLongPress = { onLongClick?.invoke() }
                )
            }
            .testTag("app_icon_${app.id}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(iconSize.dp),
            contentAlignment = Alignment.Center
        ) {
            // Main Icon Box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                RenderColorOSIconGraphic(app)
            }

            // Notification Badge if any
            if (app.badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE53935))
                        .size(19.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${app.badgeCount}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // App Label
        Text(
            text = app.name,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width((iconSize + 16).dp)
        )
    }
}

@Composable
private fun RenderColorOSIconGraphic(app: AppItem) {
    when (app.iconKey) {
        "notlar" -> {
            // Yellow notepad with pencil lines
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFFFFDD00), Color(0xFFFFB300))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.EditNote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        "takvim" -> {
            // Calendar with blue header and current day
            val dayOfMonth = remember { Calendar.getInstance().get(Calendar.DAY_OF_MONTH) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(Color(0xFF1E88E5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "EKİM",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$dayOfMonth",
                        color = Color(0xFF1E88E5),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        "saat" -> {
            // Live animated ticking ColorOS analog clock
            LiveAnalogClock()
        }

        "kamera" -> {
            // Silver 3D Camera Lens
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFFE0E0E0), Color(0xFF757575))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF212121)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF00B0FF), Color(0xFF0D47A1))
                                )
                            )
                    )
                }
            }
        }

        "chrome" -> {
            // Google Chrome circular logo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(44.dp)) {
                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    drawCircle(color = Color(0xFFE53935), radius = radius)
                    drawCircle(color = Color(0xFF43A047), radius = radius * 0.8f)
                    drawCircle(color = Color(0xFFFDD835), radius = radius * 0.6f)
                    drawCircle(color = Color.White, radius = radius * 0.44f)
                    drawCircle(color = Color(0xFF1E88E5), radius = radius * 0.36f)
                }
            }
        }

        "photos_coloros" -> {
            // ColorOS Photos (landscape mountains & sun)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF42A5F5), Color(0xFF1976D2))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Landscape,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        "photos_google" -> {
            // Google Photos colorful pinwheel
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FilterVintage,
                    contentDescription = null,
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        "weather" -> {
            // Bright cyan weather with smiling sun & cloud
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF29B6F6), Color(0xFF0288D1))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = Color(0xFFFFEE58),
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = 10.dp, y = (-8).dp)
                )
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                        .offset(x = (-4).dp, y = 4.dp)
                )
            }
        }

        "gofile" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFFFFB300), Color(0xFFF57C00))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudDownload,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }

        // Folders with translucent glass backdrop and mini app icons
        "folder_tools", "folder_google", "folder_downloads", "folder_custom" -> {
            RenderFolderPreview(app)
        }

        // Dock Apps
        "phone" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        "messages" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF81C784), Color(0xFF43A047))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        "whatsapp" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF25D366)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        "settings" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFFEEEEEE), Color(0xFF9E9E9E))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color(0xFF424242),
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        // Page 2 Apps
        "roblox_vn", "roblox" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1E293B)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (app.iconKey.contains("vn")) "R\nVN" else "R",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        "secure_vpn" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF00ACC1), Color(0xFF006064))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.VpnKey,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        "messenger" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0078FF), Color(0xFF00C6FF))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        "capcut" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCut,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        "qr_barcode" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = Color(0xFF1E88E5),
                    modifier = Modifier.size(34.dp)
                )
            }
        }

        "sketchware" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF2196F3), Color(0xFF1565C0))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        "telegram" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF29B6F6), Color(0xFF0288D1))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        "win7_simu" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF4FC3F7), Color(0xFF0277BD))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DesktopWindows,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        "fake_update" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFA4C639)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SystemUpdate,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        "virtual_master" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Layers,
                    contentDescription = null,
                    tint = Color(0xFFFF5722),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Generic fallback icon
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF42A5F5), Color(0xFF1E88E5))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Apps,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
private fun RenderFolderPreview(folder: AppItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.35f))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val previewApps = folder.folderApps.take(9)
            val rows = previewApps.chunked(3)
            rows.forEach { rowApps ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowApps.forEach {
                        Box(
                            modifier = Modifier
                                .size(11.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.White.copy(alpha = 0.85f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E88E5))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LiveAnalogClock() {
    var calendar by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            calendar = Calendar.getInstance()
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1D21)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(46.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f

            // White dial rim
            drawCircle(color = Color.White.copy(alpha = 0.15f), radius = radius)

            // Hours, Minutes, Seconds calculation
            val hours = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)
            val seconds = calendar.get(Calendar.SECOND)

            val hourAngle = Math.toRadians((hours * 30 + minutes * 0.5 - 90))
            val minuteAngle = Math.toRadians((minutes * 6 - 90).toDouble())
            val secondAngle = Math.toRadians((seconds * 6 - 90).toDouble())

            // Hour hand
            val hourEnd = Offset(
                (center.x + (radius * 0.5f) * cos(hourAngle)).toFloat(),
                (center.y + (radius * 0.5f) * sin(hourAngle)).toFloat()
            )
            drawLine(
                color = Color.White,
                start = center,
                end = hourEnd,
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )

            // Minute hand
            val minEnd = Offset(
                (center.x + (radius * 0.75f) * cos(minuteAngle)).toFloat(),
                (center.y + (radius * 0.75f) * sin(minuteAngle)).toFloat()
            )
            drawLine(
                color = Color.White,
                start = center,
                end = minEnd,
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Second hand (red)
            val secEnd = Offset(
                (center.x + (radius * 0.85f) * cos(secondAngle)).toFloat(),
                (center.y + (radius * 0.85f) * sin(secondAngle)).toFloat()
            )
            drawLine(
                color = Color(0xFFE53935),
                start = center,
                end = secEnd,
                strokeWidth = 1.5f,
                cap = StrokeCap.Round
            )

            // Center pivot dot
            drawCircle(color = Color(0xFFE53935), radius = 2.5f)
        }
    }
}
