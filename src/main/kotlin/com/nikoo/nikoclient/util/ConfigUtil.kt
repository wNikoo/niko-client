package com.nikoo.nikoclient.util

import java.io.File

object ConfigUtil {
    private val configDir: File by lazy {
        val dir = File(System.getProperty("user.home"), ".minecraft/config/nikoclient")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir
    }

    fun getConfigDir(): File = configDir

    fun getProfilesFile(): File = File(configDir, "profiles.json")

    fun getKeybindsFile(): File = File(configDir, "keybinds.json")

    fun getSettingsFile(): File = File(configDir, "settings.json")
}
