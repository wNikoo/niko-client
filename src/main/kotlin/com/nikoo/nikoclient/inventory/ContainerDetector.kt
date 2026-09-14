package com.nikoo.nikoclient.inventory

import net.minecraft.client.MinecraftClient
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import org.slf4j.LoggerFactory

object ContainerDetector {
    private val LOGGER = LoggerFactory.getLogger("ContainerDetector")

    fun isPlayerInContainer(): Boolean {
        val client = MinecraftClient.getInstance()
        val screen = client.currentScreen

        return screen is net.minecraft.client.gui.screen.ingame.GenericContainerScreen ||
                screen is net.minecraft.client.gui.screen.ingame.ChestScreen ||
                screen is net.minecraft.client.gui.screen.ingame.CrafterScreen ||
                screen is net.minecraft.client.gui.screen.ingame.FurnaceScreen ||
                screen is net.minecraft.client.gui.screen.ingame.DispenserScreen ||
                screen is net.minecraft.client.gui.screen.ingame.HopperScreen
    }

    fun isPlayerInventoryOnly(): Boolean {
        val client = MinecraftClient.getInstance()
        val screen = client.currentScreen

        return screen is net.minecraft.client.gui.screen.ingame.InventoryScreen ||
                screen == null
    }

    fun canExecuteMacro(): Boolean {
        return !isPlayerInContainer() || isPlayerInventoryOnly()
    }
}
