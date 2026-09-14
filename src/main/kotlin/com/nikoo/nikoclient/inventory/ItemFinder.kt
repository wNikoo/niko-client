package com.nikoo.nikoclient.inventory

import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import org.slf4j.LoggerFactory

object ItemFinder {
    private val LOGGER = LoggerFactory.getLogger("ItemFinder")

    /**
     * Find an item in the player's inventory that matches the given criteria
     */
    fun findItemInInventory(
        itemId: String? = null,
        customName: String? = null,
        excludeSlots: Set<Int> = emptySet()
    ): Pair<Int, ItemStack>? {
        val client = net.minecraft.client.MinecraftClient.getInstance()
        val player = client.player ?: return null
        val inventory = player.inventory

        // Search all slots
        for (i in 0..40) {
            if (i in excludeSlots) continue

            val stack = when {
                i <= 35 -> inventory.getStack(i)  // Hotbar + main
                i <= 39 -> inventory.armorSlots[i - 36]  // Armor
                i == 40 -> inventory.offHandSlots[0]  // Offhand
                else -> continue
            }

            if (stack.isEmpty) continue

            // Check item ID
            itemId?.let {
                val stackId = Registries.ITEM.getId(stack.item).toString()
                if (stackId != it) {
                    return@let
                }
            }

            // Check custom name
            customName?.let {
                val stackName = stack.name.string
                if (stackName != it) {
                    return@let
                }
            }

            LOGGER.debug("Found matching item at slot $i: ${Registries.ITEM.getId(stack.item)}")
            return Pair(i, stack)
        }

        LOGGER.debug("No matching item found for: itemId=$itemId, customName=$customName")
        return null
    }

    /**
     * Get all items in inventory matching criteria
     */
    fun findAllItemsInInventory(
        itemId: String? = null,
        customName: String? = null
    ): List<Pair<Int, ItemStack>> {
        val client = net.minecraft.client.MinecraftClient.getInstance()
        val player = client.player ?: return emptyList()
        val inventory = player.inventory

        val results = mutableListOf<Pair<Int, ItemStack>>()

        for (i in 0..40) {
            val stack = when {
                i <= 35 -> inventory.getStack(i)
                i <= 39 -> inventory.armorSlots[i - 36]
                i == 40 -> inventory.offHandSlots[0]
                else -> continue
            }

            if (stack.isEmpty) continue

            var matches = true

            itemId?.let {
                val stackId = Registries.ITEM.getId(stack.item).toString()
                if (stackId != it) matches = false
            }

            customName?.let {
                val stackName = stack.name.string
                if (stackName != it) matches = false
            }

            if (matches) {
                results.add(Pair(i, stack))
            }
        }

        LOGGER.debug("Found ${results.size} matching items")
        return results
    }
}
