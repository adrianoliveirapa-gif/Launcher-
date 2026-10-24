package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.LauncherSettings
import com.example.data.WallpaperType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAppDialog(
    settings: LauncherSettings,
    onSettingsChange: (LauncherSettings) -> Unit,
    onDismiss: () -> Unit
) {
    var showAboutPhone by remember { mutableStateOf(false) }
    var showWallpaperPicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .statusBarsPadding()
            .testTag("settings_app_screen")
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
                        tint = Color(0xFF1E293B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ayarlar",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Banner Card - Ahmet Koyak Edition
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0284C7))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "O",
                                    color = Color(0xFF0284C7),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "ColorOS • Android 8 Oreo",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Oppo Launcher by Ahmet Koyak",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // Section 1: Cihaz ve Sistem
                item {
                    Text(
                        text = "Sistem & Cihaz",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                    )
                }

                item {
                    SettingsGroupCard {
                        SettingsRow(
                            icon = Icons.Default.PhoneAndroid,
                            title = "Telefon Hakkında",
                            subtitle = "OPPO R15 • ColorOS v5.2.1 • Android 8.1",
                            onClick = { showAboutPhone = true }
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        SettingsRow(
                            icon = Icons.Default.Wallpaper,
                            title = "Duvar Kağıdı Değiştir",
                            subtitle = when (settings.currentWallpaper) {
                                WallpaperType.OREO_HERO_PAGE_1 -> "Android Oreo Maskotu (Ekran 1)"
                                WallpaperType.OREO_HERO_PAGE_2 -> "Android Oreo Maskotu (Ekran 2)"
                                WallpaperType.COLOROS_BLUE_CANVAS -> "ColorOS Kraliyet Mavisi"
                                WallpaperType.OPPO_EMERALD -> "Oppo Zümrüt Yeşili"
                                WallpaperType.DARK_NEBULA -> "Karanlık Oreo Nebula"
                            },
                            onClick = { showWallpaperPicker = true }
                        )
                    }
                }

                // Section 2: Başlatıcı Görünümü
                item {
                    Text(
                        text = "Başlatıcı Özelleştirme",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                    )
                }

                item {
                    SettingsGroupCard {
                        SettingsSwitchRow(
                            icon = Icons.Default.Speed,
                            title = "RAM & Temizleyici Widget'ı",
                            subtitle = "Ana ekrandaki optimum hızlandırıcı kartı",
                            checked = settings.showCleanerWidget,
                            onCheckedChange = { onSettingsChange(settings.copy(showCleanerWidget = it)) }
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        SettingsSwitchRow(
                            icon = Icons.Default.Search,
                            title = "Alt Arama Çubuğu (\"Ara\")",
                            subtitle = "Dock üzerindeki hızlı arama butonu",
                            checked = settings.showSearchPill,
                            onCheckedChange = { onSettingsChange(settings.copy(showSearchPill = it)) }
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        SettingsSwitchRow(
                            icon = Icons.Default.Navigation,
                            title = "Gezinme Çubuğu",
                            subtitle = "Alt kısımdaki 3 tuşlu ColorOS tuş takımı",
                            checked = settings.showNavBar,
                            onCheckedChange = { onSettingsChange(settings.copy(showNavBar = it)) }
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        SettingsSwitchRow(
                            icon = Icons.Default.NotificationsActive,
                            title = "Bildirim Rozetleri",
                            subtitle = "Uygulama simgeleri üzerinde kırmızı sayı sayaçları",
                            checked = settings.showNotificationBadges,
                            onCheckedChange = { onSettingsChange(settings.copy(showNotificationBadges = it)) }
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        SettingsSwitchRow(
                            icon = Icons.Default.Vibration,
                            title = "Dokunsal Titreşim",
                            subtitle = "Simgelere ve tuşlara dokunulduğunda haptik geri bildirim",
                            checked = settings.hapticsEnabled,
                            onCheckedChange = { onSettingsChange(settings.copy(hapticsEnabled = it)) }
                        )
                    }
                }

                // Ahmet Koyak Special Note
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = Color(0xFF0284C7),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Ahmet Koyak Özel İmzası",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E293B),
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Bu başlatıcı, Oppo ColorOS 5.2.1 ve Android 8 Oreo atmosferini birebir canlandırmak için Ahmet Koyak adına tasarlanmıştır.",
                                color = Color(0xFF64748B),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // About Phone Dialog
        if (showAboutPhone) {
            AlertDialog(
                onDismissRequest = { showAboutPhone = false },
                title = { Text("Telefon Hakkında") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        AboutInfoRow("Cihaz Adı", "OPPO R15")
                        AboutInfoRow("Sürüm Editörü", "Ahmet Koyak")
                        AboutInfoRow("ColorOS Sürümü", "v5.2.1")
                        AboutInfoRow("Android Sürümü", "8.1.0 (Oreo)")
                        AboutInfoRow("İşlemci", "Qualcomm Snapdragon 660 Octa-core")
                        AboutInfoRow("RAM Kapasitesi", "6.00 GB")
                        AboutInfoRow("Dahili Depolama", "128.00 GB (84.2 GB Boş)")
                        AboutInfoRow("Güvenlik Yaması", "5 Ekim 2018")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAboutPhone = false }) {
                        Text("Tamam", color = Color(0xFF0284C7))
                    }
                }
            )
        }

        // Wallpaper Picker Dialog
        if (showWallpaperPicker) {
            AlertDialog(
                onDismissRequest = { showWallpaperPicker = false },
                title = { Text("Duvar Kağıdı Seç") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        WallpaperOption(
                            title = "Android Oreo Kahramanı (Sayfa 1)",
                            selected = settings.currentWallpaper == WallpaperType.OREO_HERO_PAGE_1,
                            onClick = {
                                onSettingsChange(settings.copy(currentWallpaper = WallpaperType.OREO_HERO_PAGE_1))
                                showWallpaperPicker = false
                            }
                        )
                        WallpaperOption(
                            title = "Android Oreo Kahramanı (Sayfa 2)",
                            selected = settings.currentWallpaper == WallpaperType.OREO_HERO_PAGE_2,
                            onClick = {
                                onSettingsChange(settings.copy(currentWallpaper = WallpaperType.OREO_HERO_PAGE_2))
                                showWallpaperPicker = false
                            }
                        )
                        WallpaperOption(
                            title = "ColorOS Kraliyet Mavisi (Saf Tasarım)",
                            selected = settings.currentWallpaper == WallpaperType.COLOROS_BLUE_CANVAS,
                            onClick = {
                                onSettingsChange(settings.copy(currentWallpaper = WallpaperType.COLOROS_BLUE_CANVAS))
                                showWallpaperPicker = false
                            }
                        )
                        WallpaperOption(
                            title = "Oppo Zümrüt Yeşili Gradyan",
                            selected = settings.currentWallpaper == WallpaperType.OPPO_EMERALD,
                            onClick = {
                                onSettingsChange(settings.copy(currentWallpaper = WallpaperType.OPPO_EMERALD))
                                showWallpaperPicker = false
                            }
                        )
                        WallpaperOption(
                            title = "Karanlık Oreo Nebula",
                            selected = settings.currentWallpaper == WallpaperType.DARK_NEBULA,
                            onClick = {
                                onSettingsChange(settings.copy(currentWallpaper = WallpaperType.DARK_NEBULA))
                                showWallpaperPicker = false
                            }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showWallpaperPicker = false }) {
                        Text("Kapat")
                    }
                }
            )
        }
    }
}

@Composable
private fun SettingsGroupCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp), content = content)
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0F2FE)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 12.sp, color = Color(0xFF64748B))
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8))
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(if (checked) Color(0xFFE0F2FE) else Color(0xFFF1F5F9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) Color(0xFF0284C7) else Color(0xFF94A3B8),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
            Text(text = subtitle, fontSize = 12.sp, color = Color(0xFF64748B))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF0284C7)
            )
        )
    }
}

@Composable
private fun AboutInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color(0xFF64748B), fontSize = 13.sp)
        Text(text = value, color = Color(0xFF1E293B), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun WallpaperOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(if (selected) Color(0xFFE0F2FE) else Color.Transparent)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, fontSize = 14.sp, color = Color(0xFF1E293B))
    }
}
