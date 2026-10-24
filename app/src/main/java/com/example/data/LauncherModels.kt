package com.example.data

data class AppItem(
    val id: String,
    val name: String,
    val iconKey: String,
    val badgeCount: Int = 0,
    val isFolder: Boolean = false,
    val folderApps: List<AppItem> = emptyList(),
    val appCategory: String = "Genel"
)

enum class WallpaperType {
    OREO_HERO_PAGE_1,
    OREO_HERO_PAGE_2,
    COLOROS_BLUE_CANVAS,
    OPPO_EMERALD,
    DARK_NEBULA
}

data class LauncherSettings(
    val currentWallpaper: WallpaperType = WallpaperType.OREO_HERO_PAGE_1,
    val showCleanerWidget: Boolean = true,
    val showSearchPill: Boolean = true,
    val showNavBar: Boolean = true,
    val showNotificationBadges: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val ramUsedPercent: Int = 54,
    val totalRamGb: Float = 6.0f,
    val junkFilesMb: Int = 0
)

object LauncherDefaults {
    fun getPage1Apps(): List<AppItem> = listOf(
        AppItem(id = "notlar", name = "Notlar", iconKey = "notlar"),
        AppItem(id = "takvim", name = "Takvim", iconKey = "takvim"),
        AppItem(id = "saat", name = "Saat", iconKey = "saat"),
        AppItem(id = "kamera", name = "Kamera", iconKey = "kamera"),
        
        AppItem(id = "chrome", name = "Chrome", iconKey = "chrome"),
        AppItem(
            id = "araclar",
            name = "Araçlar",
            iconKey = "folder_tools",
            badgeCount = 2,
            isFolder = true,
            folderApps = listOf(
                AppItem(id = "hesap_makinesi", name = "Hesap Makinesi", iconKey = "calculator"),
                AppItem(id = "pusula", name = "Pusula", iconKey = "compass"),
                AppItem(id = "kaydedici", name = "Ses Kaydedici", iconKey = "recorder"),
                AppItem(id = "fener", name = "El Feneri", iconKey = "flashlight"),
                AppItem(id = "dosya_yoneticisi", name = "Dosyalar", iconKey = "files"),
                AppItem(id = "yedekleme", name = "Yedekle & Geri Yükle", iconKey = "backup"),
                AppItem(id = "telefon_yoneticisi", name = "Telefon Yöneticisi", iconKey = "manager")
            )
        ),
        AppItem(id = "fotograflar_coloros", name = "Fotoğraflar", iconKey = "photos_coloros"),
        AppItem(id = "fotograflar_google", name = "Fotoğraflar", iconKey = "photos_google"),
        
        AppItem(
            id = "google_folder",
            name = "Google",
            iconKey = "folder_google",
            isFolder = true,
            folderApps = listOf(
                AppItem(id = "g_search", name = "Google", iconKey = "google"),
                AppItem(id = "gmail", name = "Gmail", iconKey = "gmail"),
                AppItem(id = "haritalar", name = "Haritalar", iconKey = "maps"),
                AppItem(id = "youtube", name = "YouTube", iconKey = "youtube"),
                AppItem(id = "drive", name = "Drive", iconKey = "drive"),
                AppItem(id = "play_store", name = "Play Store", iconKey = "playstore")
            )
        ),
        AppItem(id = "hava_durumu", name = "Hava durumu", iconKey = "weather"),
        AppItem(
            id = "indirici",
            name = "İndirici",
            iconKey = "folder_downloads",
            isFolder = true,
            folderApps = listOf(
                AppItem(id = "indirilenler", name = "İndirilenler", iconKey = "downloads"),
                AppItem(id = "apk_yukleyici", name = "Paket Yükleyici", iconKey = "package_installer"),
                AppItem(id = "bulut_senk", name = "Bulut Senkron", iconKey = "cloud_sync")
            )
        ),
        AppItem(id = "gofile", name = "Gofile - Bulut D..", iconKey = "gofile")
    )

    fun getPage2Apps(): List<AppItem> = listOf(
        AppItem(id = "roblox_vn", name = "Roblox VN", iconKey = "roblox_vn"),
        AppItem(id = "secure_vpn", name = "Secure VPN", iconKey = "secure_vpn"),
        AppItem(id = "roblox", name = "Roblox", iconKey = "roblox"),
        AppItem(id = "messenger", name = "Messenger", iconKey = "messenger"),
        
        AppItem(id = "capcut", name = "CapCut", iconKey = "capcut"),
        AppItem(id = "qr_barkod", name = "QR ve Barkod..", iconKey = "qr_barcode"),
        AppItem(id = "sketchware_pro", name = "Sketchware Pro", iconKey = "sketchware"),
        AppItem(id = "telegram", name = "Telegram", iconKey = "telegram"),
        
        AppItem(id = "win7_simu", name = "Win7 Simu", iconKey = "win7_simu"),
        AppItem(id = "fake_update", name = "Fake Update", iconKey = "fake_update"),
        AppItem(id = "virtual_master", name = "Virtual Master", iconKey = "virtual_master"),
        AppItem(
            id = "klasor",
            name = "Klasör",
            iconKey = "folder_custom",
            isFolder = true,
            folderApps = listOf(
                AppItem(id = "oyun_alani", name = "Oyun Alanı", iconKey = "game_space"),
                AppItem(id = "muzik", name = "Müzik", iconKey = "music"),
                AppItem(id = "video", name = "Video Oynatıcı", iconKey = "video"),
                AppItem(id = "radyo", name = "FM Radyo", iconKey = "radio")
            )
        )
    )

    fun getDockApps(): List<AppItem> = listOf(
        AppItem(id = "telefon", name = "Telefon", iconKey = "phone"),
        AppItem(id = "mesajlar", name = "Mesajlar", iconKey = "messages", badgeCount = 6),
        AppItem(id = "whatsapp", name = "WhatsApp", iconKey = "whatsapp", badgeCount = 3),
        AppItem(id = "ayarlar", name = "Ayarlar", iconKey = "settings")
    )
}
