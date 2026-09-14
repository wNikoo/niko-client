package com.nikoo.nikoclient.inventory.model

import net.minecraft.item.ItemStack

data class InventorySlot(
    val slotIndex: Int,
    val slotType: SlotType,
    var itemMatcher: ItemMatcher? = null
) {
    enum class SlotType {
        HOTBAR,           // Slots 0-8
        MAIN_INVENTORY,   // Slots 9-35
        ARMOR_HEAD,       // Slot 39
        ARMOR_CHEST,      // Slot 38
        ARMOR_LEGS,       // Slot 37
        ARMOR_FEET,       // Slot 36
        OFFHAND          // Slot 40
    }
}
