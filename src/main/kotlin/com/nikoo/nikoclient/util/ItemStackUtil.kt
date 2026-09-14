package com.nikoo.nikoclient.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

object ItemStackUtil {
    fun copyItemStack(stack: ItemStack): ItemStack {
        val copy = ItemStack(stack.item, stack.count)
        if (stack.nbt != null) {
            copy.nbt = stack.nbt?.copy()
        }
        return copy
    }

    fun areStacksEqual(stack1: ItemStack, stack2: ItemStack): Boolean {
        if (stack1.isEmpty && stack2.isEmpty) return true
        if (stack1.isEmpty != stack2.isEmpty) return false
        if (stack1.item != stack2.item) return false
        if (stack1.count != stack2.count) return false
        if (!areNbtEqual(stack1.nbt, stack2.nbt)) return false
        return true
    }

    private fun areNbtEqual(nbt1: NbtCompound?, nbt2: NbtCompound?): Boolean {
        return when {
            nbt1 == null && nbt2 == null -> true
            nbt1 == null || nbt2 == null -> false
            else -> nbt1 == nbt2
        }
    }
}
