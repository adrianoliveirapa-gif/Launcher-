package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorOSNavigationBar(
    onRecentsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 32.dp, vertical = 6.dp)
            .testTag("coloros_nav_bar"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Recents button (Three horizontal lines)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable { onRecentsClick() }
                .testTag("nav_recents"),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(width = 16.dp, height = 1.8.dp)
                            .background(Color.White.copy(alpha = 0.85f))
                    )
                }
            }
        }

        // Home button (ColorOS circle)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable { onHomeClick() }
                .testTag("nav_home"),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(Color.Transparent)
            ) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.85f),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                    )
                }
            }
        }

        // Back button (ColorOS triangle pointing left)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable { onBackClick() }
                .testTag("nav_back"),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(13.dp)) {
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, size.height / 2f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(
                    path = path,
                    color = Color.White.copy(alpha = 0.85f),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}
