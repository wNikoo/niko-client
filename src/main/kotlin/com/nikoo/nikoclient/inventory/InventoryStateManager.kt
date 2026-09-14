package com.nikoo.nikoclient.inventory

import net.minecraft.client.MinecraftClient
import net.minecraft.item.ItemStack
import org.slf4j.LoggerFactory

object InventoryStateManager {
    private val LOGGER = LoggerFactory.getLogger("InventoryStateManager")
    private var clientInventorySnapshot: Map<Int, ItemStack> = emptyMap()

    fun initialize() {
        LOGGER.info("InventoryStateManager initialized")
    }

    fun captureCurrentState(): Map<Int, ItemStack> {
        val client = MinecraftClient.getInstance()
        val player = client.player ?: return emptyMap()
        val inventory = player.inventory

        val state = mutableMapOf<Int, ItemStack>()
        
        // Capture all inventory slots (0-35: hotbar + main)
        for (i in 0..35) {
            state[i] = inventory.getStack(i).copy()
        }

        // Capture armor slots (36-39)
        for (i in 0..3) {
            state[36 + i] = inventory.armorSlots[i].copy()
        }

        // Capture offhand (40)
        state[40] = inventory.offHandSlots[0].copy()

        clientInventorySnapshot = state
        return state
    }

    fun getSnapshot(): Map<Int, ItemStack> {
        return clientInventorySnapshot
    }

    fun applyInventoryState(desiredState: Map<Int, ItemStack>) {
        val client = MinecraftClient.getInstance()
        val player = client.player ?: return
        val inventory = player.inventory

        try {
            // Apply main inventory + hotbar slots (0-35)
            for (i in 0..35) {
                desiredState[i]?.let {
                    inventory.setStack(i, it.copy())
                }
            }

            // Apply armor slots (36-39)
            for (i in 0..3) {
                desiredState[36 + i]?.let {
                    inventory.armorSlots[i] = it.copy()
                }
            }

            // Apply offhand (40)
            desiredState[40]?.let {
                inventory.offHandSlots[0] = it.copy()
            }

            LOGGER.debug("Applied inventory state")
        } catch (e: Exception) {
            LOGGER.error("Failed to apply inventory state", e)
        }
    }
}
