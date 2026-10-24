package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.data.WallpaperType

@Composable
fun ColorOSWallpaper(
    wallpaperType: WallpaperType,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (wallpaperType) {
            WallpaperType.OREO_HERO_PAGE_1 -> {
                Image(
                    painter = painterResource(id = R.drawable.screenshot_page1),
                    contentDescription = "Oreo Hero Duvar Kağıdı 1",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            WallpaperType.OREO_HERO_PAGE_2 -> {
                Image(
                    painter = painterResource(id = R.drawable.screenshot_page2),
                    contentDescription = "Oreo Hero Duvar Kağıdı 2",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            WallpaperType.COLOROS_BLUE_CANVAS -> {
                // Royal blue with Android 8 Oreo superhero mascot illustration
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF002DE3), Color(0xFF001EC4))
                            )
                        )
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val centerX = size.width / 2f
                        val centerY = size.height * 0.58f

                        // Superhero Green Cape behind
                        drawOval(
                            color = Color(0xFF66BB6A),
                            topLeft = Offset(centerX - 160f, centerY - 140f),
                            size = androidx.compose.ui.geometry.Size(320f, 440f)
                        )

                        // Android Bot Head (white dome with green mask)
                        drawArc(
                            color = Color.White,
                            startAngle = 180f,
                            sweepAngle = 180f,
                            useCenter = true,
                            topLeft = Offset(centerX - 90f, centerY - 240f),
                            size = androidx.compose.ui.geometry.Size(180f, 150f)
                        )
                        // Eyes
                        drawCircle(color = Color(0xFF263238), radius = 8f, center = Offset(centerX - 40f, centerY - 190f))
                        drawCircle(color = Color(0xFF263238), radius = 8f, center = Offset(centerX + 40f, centerY - 190f))

                        // Antennae
                        drawLine(
                            color = Color.White,
                            start = Offset(centerX - 50f, centerY - 230f),
                            end = Offset(centerX - 70f, centerY - 270f),
                            strokeWidth = 10f
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(centerX + 50f, centerY - 230f),
                            end = Offset(centerX + 70f, centerY - 270f),
                            strokeWidth = 10f
                        )

                        // Big OREO Cookie Body
                        // Outer black cookie
                        drawCircle(
                            color = Color(0xFF1E1E1E),
                            radius = 145f,
                            center = Offset(centerX, centerY)
                        )
                        // White cream layer rim
                        drawCircle(
                            color = Color.White,
                            radius = 138f,
                            center = Offset(centerX, centerY),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
                        )
                        // Cookie pattern detail
                        drawCircle(
                            color = Color(0xFF2E2E2E),
                            radius = 110f,
                            center = Offset(centerX, centerY),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 6f)
                        )

                        // Legs
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(centerX - 65f, centerY + 130f),
                            size = androidx.compose.ui.geometry.Size(40f, 150f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f)
                        )
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(centerX + 25f, centerY + 130f),
                            size = androidx.compose.ui.geometry.Size(40f, 150f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f)
                        )

                        // Green chevron leg boots
                        drawRoundRect(
                            color = Color(0xFF4CAF50),
                            topLeft = Offset(centerX - 65f, centerY + 210f),
                            size = androidx.compose.ui.geometry.Size(40f, 70f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f)
                        )
                        drawRoundRect(
                            color = Color(0xFF4CAF50),
                            topLeft = Offset(centerX + 25f, centerY + 210f),
                            size = androidx.compose.ui.geometry.Size(40f, 70f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f)
                        )
                    }
                }
            }

            WallpaperType.OPPO_EMERALD -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF0F766E), Color(0xFF064E3B), Color(0xFF022C22))
                            )
                        )
                )
            }

            WallpaperType.DARK_NEBULA -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0xFF1E1B4B), Color(0xFF0F172A), Color(0xFF020617))
                            )
                        )
                )
            }
        }
    }
}
