package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.LauncherDefaults
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ColorOS Launcher", appName)
  }

  @Test
  fun `verify launcher apps configuration`() {
    val page1Apps = LauncherDefaults.getPage1Apps()
    val page2Apps = LauncherDefaults.getPage2Apps()
    val dockApps = LauncherDefaults.getDockApps()

    assertTrue(page1Apps.isNotEmpty())
    assertTrue(page2Apps.isNotEmpty())
    assertEquals(4, dockApps.size)

    assertNotNull(page1Apps.find { it.id == "notlar" })
    assertNotNull(dockApps.find { it.id == "telefon" })
  }
}
