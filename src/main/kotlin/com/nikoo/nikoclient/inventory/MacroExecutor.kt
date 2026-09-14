package com.nikoo.nikoclient.inventory

import com.nikoo.nikoclient.inventory.model.ItemMatcher
import com.nikoo.nikoclient.inventory.model.Profile
import net.minecraft.client.MinecraftClient
import net.minecraft.item.ItemStack
import org.slf4j.LoggerFactory

object MacroExecutor {
    private val LOGGER = LoggerFactory.getLogger("MacroExecutor")

    fun executeProfile(profile: Profile): Boolean {
        val client = MinecraftClient.getInstance()
        val player = client.player ?: return false

        // Don't execute if player is in container GUI
        if (client.currentScreen != null && client.currentScreen !is net.minecraft.client.gui.screen.ingame.InventoryScreen) {
            LOGGER.warn("Cannot execute macro while in container GUI")
            return false
        }

        try {
            LOGGER.info("Executing profile: ${profile.name}")

            // Capture current inventory state
            val currentState = InventoryStateManager.captureCurrentState()

            // Calculate desired state based on profile
            val desiredState = calculateDesiredState(profile, currentState, player.inventory)

            // Apply the desired state
            applyProfileState(profile, currentState, desiredState, player.inventory)

            LOGGER.info("Profile executed: ${profile.name}")
            return true
        } catch (e: Exception) {
            LOGGER.error("Failed to execute profile: ${profile.name}", e)
            return false
        }
    }

    private fun calculateDesiredState(
        profile: Profile,
        currentState: Map<Int, ItemStack>,
        inventory: net.minecraft.entity.player.PlayerInventory
    ): Map<Int, ItemStack> {
        val desiredState = currentState.toMutableMap()

        for ((slotIndex, itemMatcher) in profile.slots) {
            if (itemMatcher == null) {
                // Slot should be empty
                desiredState[slotIndex] = ItemStack.EMPTY
            } else {
                // Find matching item in current inventory
                val matchingStack = findMatchingItem(itemMatcher, currentState, slotIndex)
                if (matchingStack != null) {
                    desiredState[slotIndex] = matchingStack
                } else {
                    LOGGER.warn("Item not found for profile slot: $slotIndex")
                    // Leave slot as is if item not found
                }
            }
        }

        return desiredState
    }

    private fun findMatchingItem(
        matcher: ItemMatcher,
        currentState: Map<Int, ItemStack>,
        excludeSlot: Int = -1
    ): ItemStack? {
        for ((index, stack) in currentState) {
            if (index != excludeSlot && matcher.matches(stack)) {
                return stack.copy()
            }
        }
        return null
    }

    private fun applyProfileState(
        profile: Profile,
        currentState: Map<Int, ItemStack>,
        desiredState: Map<Int, ItemStack>,
        inventory: net.minecraft.entity.player.PlayerInventory
    ) {
        // Apply main inventory state instantly
        InventoryStateManager.applyInventoryState(desiredState)

        // Queue hotbar-specific synchronization if needed
        for (i in 0..8) {
            val current = currentState[i]
            val desired = desiredState[i]

            if (current != desired && current != null && desired != null) {
                val currentId = current.item.toString()
                val desiredId = desired.item.toString()

                if (currentId != desiredId) {
                    LOGGER.debug("Hotbar slot $i changed: $currentId -> $desiredId")
                    // Queue only if truly necessary for synchronization
                }
            }
        }
    }
}
