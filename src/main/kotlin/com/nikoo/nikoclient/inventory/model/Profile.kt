package com.nikoo.nikoclient.inventory.model

data class Profile(
    val name: String,
    val slots: MutableMap<Int, ItemMatcher?> = mutableMapOf(),
    var keybind: String? = null
) {
    fun configureSlot(slotIndex: Int, itemMatcher: ItemMatcher?) {
        slots[slotIndex] = itemMatcher
    }

    fun getSlotConfiguration(slotIndex: Int): ItemMatcher? {
        return slots[slotIndex]
    }

    fun isSlotConfigured(slotIndex: Int): Boolean {
        return slots.containsKey(slotIndex) && slots[slotIndex] != null
    }
}
