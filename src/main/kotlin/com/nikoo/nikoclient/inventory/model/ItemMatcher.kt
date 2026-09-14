package com.nikoo.nikoclient.inventory.model

import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries

data class ItemMatcher(
    val itemId: String? = null,
    val customName: String? = null,
    val nbtData: String? = null
) {
    fun matches(itemStack: ItemStack): Boolean {
        if (itemStack.isEmpty) return false

        // Match by item ID (most reliable)
        itemId?.let {
            val stackId = Registries.ITEM.getId(itemStack.item).toString()
            if (stackId != it) return false
        }

        // Match by custom name if present
        customName?.let {
            val stackName = itemStack.name.string
            if (stackName != it) return false
        }

        // NBT data matching can be added here for more sophisticated matching
        // This is a placeholder for future enhancement

        return true
    }

    companion object {
        fun fromItemStack(itemStack: ItemStack): ItemMatcher {
            return ItemMatcher(
                itemId = Registries.ITEM.getId(itemStack.item).toString(),
                customName = itemStack.name.string,
                nbtData = itemStack.nbt?.toString()
            )
        }
    }
}
