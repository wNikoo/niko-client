package com.nikoo.nikoclient

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

const val MOD_ID = "nikoclient"

class NikoClient : ModInitializer {
    companion object {
        private val LOGGER = LoggerFactory.getLogger("Niko Client")
    }

    override fun onInitialize() {
        LOGGER.info("Niko Client initialized!")
    }
}
