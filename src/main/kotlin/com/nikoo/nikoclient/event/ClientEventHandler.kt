package com.nikoo.nikoclient.event

import com.nikoo.nikoclient.gui.ScreenManager
import com.nikoo.nikoclient.inventory.HotbarActionQueue
import com.nikoo.nikoclient.inventory.ProfileManager
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW
import org.slf4j.LoggerFactory

object ClientEventHandler {
    private val LOGGER = LoggerFactory.getLogger("ClientEventHandler")
    private lateinit var nikoClientKeyBinding: KeyBinding

    fun register() {
        // Register keybinding for opening Niko Client GUI
        nikoClientKeyBinding = KeyBinding(
            "key.nikoclient.open",
            GLFW.GLFW_KEY_N,
            "category.nikoclient.main"
        )
        KeyBindingHelper.registerKeyBinding(nikoClientKeyBinding)

        // Register tick event to process hotbar queue and check keybinds
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            handleClientTick()
        }
    }

    private fun handleClientTick() {
        val client = net.minecraft.client.MinecraftClient.getInstance()
        if (client.player == null) return

        // Process hotbar action queue
        HotbarActionQueue.processQueue()

        // Check if Niko Client keybind was pressed
        if (nikoClientKeyBinding.wasPressed()) {
            ScreenManager.openMainScreen()
        }

        // Check profile keybinds (to be implemented with profile system)
    }
}
