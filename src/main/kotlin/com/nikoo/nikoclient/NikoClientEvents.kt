package com.nikoo.nikoclient

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvType
import org.slf4j.LoggerFactory

@Environment(EnvType.CLIENT)
class NikoClientEvents : ClientModInitializer {
    companion object {
        private val LOGGER = LoggerFactory.getLogger("Niko Client - Client")
    }

    override fun onInitializeClient() {
        LOGGER.info("Niko Client client events initialized!")
        
        // Register commands
        CommandRegistry.register()
        
        // Initialize managers
        ProfileManager.load()
        KeybindManager.register()
        InventoryStateManager.initialize()
    }
}
