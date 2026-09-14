package com.nikoo.nikoclient.notification

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

object NotificationManager {
    fun showNotification(message: String, duration: Int = 3000) {
        val client = MinecraftClient.getInstance()
        val player = client.player ?: return

        // Display as action bar message
        player.sendMessage(Text.literal(message), true)
    }

    fun showError(message: String) {
        showNotification("\u00a7c[Error]\u00a7r $message", 5000)
    }

    fun showSuccess(message: String) {
        showNotification("\u00a7a[Success]\u00a7r $message", 3000)
    }

    fun showInfo(message: String) {
        showNotification("\u00a76[Info]\u00a7r $message", 3000)
    }
}
