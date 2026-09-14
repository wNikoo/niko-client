package com.nikoo.nikoclient.gui

import com.nikoo.nikoclient.gui.screen.NikoClientMainScreen
import net.minecraft.client.gui.screen.Screen

object ScreenManager {
    fun openMainScreen() {
        val client = net.minecraft.client.MinecraftClient.getInstance()
        client.setScreen(NikoClientMainScreen())
    }
}
