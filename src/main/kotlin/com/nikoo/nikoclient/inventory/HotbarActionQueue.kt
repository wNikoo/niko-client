package com.nikoo.nikoclient.inventory

import net.minecraft.client.MinecraftClient
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentLinkedQueue

object HotbarActionQueue {
    private val LOGGER = LoggerFactory.getLogger("HotbarActionQueue")
    private val actionQueue = ConcurrentLinkedQueue<HotbarAction>()
    private var isProcessing = false
    private var lastTickProcessed = 0L
    private val TICK_DELAY = 2 // Ticks between actions

    data class HotbarAction(
        val fromSlot: Int,
        val toSlot: Int,
        val timestamp: Long = System.currentTimeMillis()
    )

    fun queueSwap(fromSlot: Int, toSlot: Int) {
        if (fromSlot < 0 || fromSlot > 8 || toSlot < 0 || toSlot > 8) {
            LOGGER.warn("Invalid hotbar slots: $fromSlot -> $toSlot")
            return
        }
        actionQueue.offer(HotbarAction(fromSlot, toSlot))
        LOGGER.debug("Queued hotbar action: $fromSlot -> $toSlot")
    }

    fun processQueue() {
        if (isProcessing || actionQueue.isEmpty()) return

        val client = MinecraftClient.getInstance()
        val player = client.player ?: return

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTickProcessed < TICK_DELAY * 50) { // ~50ms per tick
            return
        }

        val action = actionQueue.poll() ?: return

        try {
            isProcessing = true
            
            // Perform the hotbar swap
            val inventory = player.inventory
            val temp = inventory.getStack(action.fromSlot).copy()
            inventory.setStack(action.fromSlot, inventory.getStack(action.toSlot).copy())
            inventory.setStack(action.toSlot, temp)

            lastTickProcessed = currentTime
            LOGGER.debug("Processed hotbar action: ${action.fromSlot} -> ${action.toSlot}")
        } catch (e: Exception) {
            LOGGER.error("Failed to process hotbar action", e)
        } finally {
            isProcessing = false
        }
    }

    fun clear() {
        actionQueue.clear()
        isProcessing = false
        LOGGER.debug("Cleared hotbar action queue")
    }

    fun isEmpty(): Boolean = actionQueue.isEmpty()

    fun size(): Int = actionQueue.size()
}
