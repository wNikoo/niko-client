package com.nikoo.nikoclient

import com.nikoo.nikoclient.command.CommandRegistry
import com.nikoo.nikoclient.event.ClientEventHandler
import com.nikoo.nikoclient.inventory.ProfileManager
import com.nikoo.nikoclient.inventory.InventoryStateManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvType
import org.slf4j.LoggerFactory

@Environment(EnvType.CLIENT)
class NikoClientEvents : ClientModInitializer {
    companion object {
        private val LOGGER = LoggerFactory.getLogger("Niko Client")
    }

    override fun onInitializeClient() {
        LOGGER.info("=== Niko Client Initializing ===")

        // Register commands
        CommandRegistry.register()
        LOGGER.info("Commands registered")

        // Initialize managers
        ProfileManager.load()
        LOGGER.info("Profiles loaded")

        InventoryStateManager.initialize()
        LOGGER.info("Inventory state manager initialized")

        // Register event handlers
        ClientEventHandler.register()
        LOGGER.info("Event handlers registered")

        LOGGER.info("=== Niko Client Ready ===")
    }
}
