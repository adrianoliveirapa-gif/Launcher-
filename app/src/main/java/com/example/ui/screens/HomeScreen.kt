package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppItem
import com.example.data.LauncherDefaults
import com.example.data.LauncherSettings
import com.example.ui.apps.*
import com.example.ui.components.*
import com.example.ui.modals.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })

    var settings by remember { mutableStateOf(LauncherSettings()) }

    val page1Apps = remember { mutableStateListOf<AppItem>().apply { addAll(LauncherDefaults.getPage1Apps()) } }
    val page2Apps = remember { mutableStateListOf<AppItem>().apply { addAll(LauncherDefaults.getPage2Apps()) } }
    val dockApps = remember { mutableStateListOf<AppItem>().apply { addAll(LauncherDefaults.getDockApps()) } }

    val allApps = remember(page1Apps, page2Apps, dockApps) {
        val list = mutableListOf<AppItem>()
        list.addAll(page1Apps)
        list.addAll(page2Apps)
        list.addAll(dockApps)
        page1Apps.filter { it.isFolder }.forEach { list.addAll(it.folderApps) }
        page2Apps.filter { it.isFolder }.forEach { list.addAll(it.folderApps) }
        list
    }

    // Active dialogs & screens
    var activeFolder by remember { mutableStateOf<AppItem?>(null) }
    var showSearchOverlay by remember { mutableStateOf(false) }
    var showRecentApps by remember { mutableStateOf(false) }
    var showControlCenter by remember { mutableStateOf(false) }

    // App Screens
    var activeAppScreen by remember { mutableStateOf<String?>(null) }
    var selectedAppForContextMenu by remember { mutableStateOf<AppItem?>(null) }

    fun triggerHaptic() {
        if (!settings.hapticsEnabled) return
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(30)
                }
            }
        } catch (_: Exception) {}
    }

    fun onAppLaunched(app: AppItem) {
        triggerHaptic()
        if (app.isFolder) {
            activeFolder = app
            return
        }

        when (app.id) {
            "notlar" -> activeAppScreen = "notlar"
            "hava_durumu" -> activeAppScreen = "hava_durumu"
            "saat" -> activeAppScreen = "saat"
            "hesap_makinesi" -> activeAppScreen = "hesap_makinesi"
            "ayarlar" -> activeAppScreen = "ayarlar"
            "telefon" -> activeAppScreen = "telefon"
            else -> {
                Toast.makeText(context, "${app.name} açılıyor...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Back handler: pop any open dialog, or return to page 1
    BackHandler(
        enabled = activeFolder != null || showSearchOverlay || showRecentApps || showControlCenter || activeAppScreen != null || selectedAppForContextMenu != null || pagerState.currentPage != 1
    ) {
        when {
            activeAppScreen != null -> activeAppScreen = null
            activeFolder != null -> activeFolder = null
            showSearchOverlay -> showSearchOverlay = false
            showRecentApps -> showRecentApps = false
            showControlCenter -> showControlCenter = false
            selectedAppForContextMenu != null -> selectedAppForContextMenu = null
            pagerState.currentPage != 1 -> {
                coroutineScope.launch { pagerState.animateScrollToPage(1) }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_root")
    ) {
        // Dynamic Wallpaper
        ColorOSWallpaper(wallpaperType = settings.currentWallpaper)

        // Main Launcher UI (Status Bar + Pager + Dock + Navigation)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Status Bar at Top
            ColorOSStatusBar(
                batteryLevel = 81,
                onStatusBarClick = {
                    triggerHaptic()
                    showControlCenter = true
                }
            )

            // Horizontal Pager (Page 0: Smart Assistant, Page 1: Main Apps + Cleaner Widget, Page 2: Extra Apps)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> {
                            // Smart Assistant
                            SmartAssistantScreen(
                                onOpenWeather = { activeAppScreen = "hava_durumu" },
                                onOpenCalculator = { activeAppScreen = "hesap_makinesi" },
                                onOpenNotes = { activeAppScreen = "notlar" },
                                onOpenSettings = { activeAppScreen = "ayarlar" },
                                onCleanRam = {
                                    triggerHaptic()
                                    Toast.makeText(context, "ColorOS Sistem Hızlandırıldı: %42 kullanımda", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }

                        1 -> {
                            // Main Home Screen (Page 1)
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 14.dp, vertical = 4.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                // 3 Rows of Apps (12 apps)
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(4),
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(page1Apps) { app ->
                                        ColorOSAppIcon(
                                            app = app,
                                            onClick = { onAppLaunched(app) },
                                            onLongClick = {
                                                triggerHaptic()
                                                selectedAppForContextMenu = app
                                            },
                                            iconSize = 60
                                        )
                                    }
                                }

                                // Interactive RAM & Junk Cleaner Widget
                                if (settings.showCleanerWidget) {
                                    ColorOSCleanerWidget(
                                        onCleanRam = {
                                            triggerHaptic()
                                            Toast.makeText(context, "1.4 GB RAM boşaltıldı! Sistem optimize.", Toast.LENGTH_SHORT).show()
                                        },
                                        onCleanJunk = {
                                            triggerHaptic()
                                            Toast.makeText(context, "0B Gereksiz veriler temizlendi!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.padding(start = 6.dp, bottom = 4.dp)
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(1.dp))
                                }
                            }
                        }

                        2 -> {
                            // Page 2 Apps Screen
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 14.dp, vertical = 4.dp)
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(4),
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(page2Apps) { app ->
                                        ColorOSAppIcon(
                                            app = app,
                                            onClick = { onAppLaunched(app) },
                                            onLongClick = {
                                                triggerHaptic()
                                                selectedAppForContextMenu = app
                                            },
                                            iconSize = 60
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Page Indicator Dots
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (isSelected) 6.dp else 5.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color.White else Color.White.copy(alpha = 0.4f)
                            )
                    )
                }
            }

            // Search Bar Pill ("Ara")
            if (settings.showSearchPill) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ColorOSSearchPill(
                        onClick = {
                            triggerHaptic()
                            showSearchOverlay = true
                        }
                    )
                }
            }

            // Bottom Dock
            ColorOSDock(
                dockApps = dockApps,
                onAppClick = { onAppLaunched(it) },
                onAppLongClick = {
                    triggerHaptic()
                    selectedAppForContextMenu = it
                }
            )

            // 3-Button Navigation Bar
            if (settings.showNavBar) {
                ColorOSNavigationBar(
                    onRecentsClick = {
                        triggerHaptic()
                        showRecentApps = true
                    },
                    onHomeClick = {
                        triggerHaptic()
                        activeFolder = null
                        showSearchOverlay = false
                        showRecentApps = false
                        showControlCenter = false
                        activeAppScreen = null
                        if (pagerState.currentPage != 1) {
                            coroutineScope.launch { pagerState.animateScrollToPage(1) }
                        }
                    },
                    onBackClick = {
                        triggerHaptic()
                        when {
                            activeAppScreen != null -> activeAppScreen = null
                            activeFolder != null -> activeFolder = null
                            showSearchOverlay -> showSearchOverlay = false
                            showRecentApps -> showRecentApps = false
                            showControlCenter -> showControlCenter = false
                            pagerState.currentPage != 1 -> {
                                coroutineScope.launch { pagerState.animateScrollToPage(1) }
                            }
                        }
                    }
                )
            }
        }

        // Modals & Fullscreen Overlays
        // 1. Folder Modal
        FolderModal(
            folder = activeFolder,
            onDismiss = { activeFolder = null },
            onAppClick = { onAppLaunched(it) }
        )

        // 2. Global Search Overlay
        if (showSearchOverlay) {
            GlobalSearchOverlay(
                allApps = allApps,
                onDismiss = { showSearchOverlay = false },
                onAppClick = { onAppLaunched(it) }
            )
        }

        // 3. Recent Apps Switcher
        if (showRecentApps) {
            RecentAppsModal(
                onDismiss = { showRecentApps = false },
                onAppSelect = { onAppLaunched(it) },
                onClearAll = {
                    triggerHaptic()
                    Toast.makeText(context, "Tüm uygulamalar kapatıldı. 1.8 GB RAM temizlendi.", Toast.LENGTH_SHORT).show()
                    showRecentApps = false
                }
            )
        }

        // 4. Control Center Drawer (Notification shade)
        if (showControlCenter) {
            ControlCenterDrawer(
                onDismiss = { showControlCenter = false },
                onOpenSettings = {
                    showControlCenter = false
                    activeAppScreen = "ayarlar"
                }
            )
        }

        // 5. App Screens
        when (activeAppScreen) {
            "notlar" -> NotesAppDialog(onDismiss = { activeAppScreen = null })
            "hava_durumu" -> WeatherAppDialog(onDismiss = { activeAppScreen = null })
            "saat" -> ClockAppDialog(onDismiss = { activeAppScreen = null })
            "hesap_makinesi" -> CalculatorAppDialog(onDismiss = { activeAppScreen = null })
            "ayarlar" -> SettingsAppDialog(
                settings = settings,
                onSettingsChange = { settings = it },
                onDismiss = { activeAppScreen = null }
            )
            "telefon" -> PhoneAppDialog(onDismiss = { activeAppScreen = null })
        }

        // 6. App Long-press Context Menu
        selectedAppForContextMenu?.let { app ->
            AlertDialog(
                onDismissRequest = { selectedAppForContextMenu = null },
                title = { Text(app.name) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Uygulama Bilgisi",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedAppForContextMenu = null
                                    Toast.makeText(context, "${app.name} Bilgisi: ColorOS 5.2.1 Sistem Uygulaması", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 8.dp)
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        Text(
                            text = "Ana Ekrana Sabitle",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedAppForContextMenu = null
                                    Toast.makeText(context, "${app.name} sabitlendi.", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 8.dp)
                        )
                        Divider(color = Color(0xFFE2E8F0))
                        Text(
                            text = "Paylaş",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedAppForContextMenu = null
                                    Toast.makeText(context, "${app.name} paylaşıldı.", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 8.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedAppForContextMenu = null }) {
                        Text("Kapat")
                    }
                }
            )
        }
    }
}
