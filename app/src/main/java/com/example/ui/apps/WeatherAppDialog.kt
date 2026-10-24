package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HourlyWeather(val time: String, val temp: String, val isSunny: Boolean)

@Composable
fun WeatherAppDialog(
    onDismiss: () -> Unit
) {
    val hourlyData = listOf(
        HourlyWeather("Şimdi", "24°", true),
        HourlyWeather("14:00", "25°", true),
        HourlyWeather("15:00", "24°", false),
        HourlyWeather("16:00", "22°", false),
        HourlyWeather("17:00", "20°", false),
        HourlyWeather("18:00", "19°", true)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0284C7), Color(0xFF0369A1), Color(0xFF0C4A6E))
                )
            )
            .statusBarsPadding()
            .testTag("weather_app_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Hava Durumu",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Yenile",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Main City & Temperature
            Text(
                text = "İstanbul",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Güneşli ve Açık",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "24°",
                color = Color.White,
                fontSize = 72.sp,
                fontWeight = FontWeight.Light
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "En Yüksek: 26°", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                Text(text = "En Düşük: 16°", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Hourly Forecast Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Saatlik Tahmin",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(hourlyData) { item ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(text = item.time, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Icon(
                                    imageVector = if (item.isSunny) Icons.Default.WbSunny else Icons.Default.Cloud,
                                    contentDescription = null,
                                    tint = if (item.isSunny) Color(0xFFFFD54F) else Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = item.temp, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Details Card: Humidity, Wind, Pressure, Air Quality
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Nem", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                            Text(text = "%54", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text(text = "Rüzgar", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                            Text(text = "12 km/s GB", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text(text = "Hava Kalitesi", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                            Text(text = "İyi (AQI 32)", color = Color(0xFF4ADE80), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
