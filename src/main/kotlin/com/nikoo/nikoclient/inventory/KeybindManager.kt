package com.nikoo.nikoclient.inventory

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW
import org.slf4j.LoggerFactory

object KeybindManager {
    private val LOGGER = LoggerFactory.getLogger("KeybindManager")
    private val profileKeybinds = mutableMapOf<String, KeyBinding>()

    fun register() {
        LOGGER.info("KeybindManager initialized")
    }

    fun assignKeybind(profileName: String, key: Int): Boolean {
        try {
            val keyBinding = KeyBinding(
                "key.nikoclient.profile.$profileName",
                key,
                "category.nikoclient.profile"
            )
            KeyBindingHelper.registerKeyBinding(keyBinding)
            profileKeybinds[profileName] = keyBinding
            LOGGER.info("Assigned keybind for profile: $profileName")
            return true
        } catch (e: Exception) {
            LOGGER.error("Failed to assign keybind for profile: $profileName", e)
            return false
        }
    }

    fun getKeybind(profileName: String): KeyBinding? {
        return profileKeybinds[profileName]
    }

    fun isPressed(profileName: String): Boolean {
        return profileKeybinds[profileName]?.isPressed ?: false
    }

    fun removeKeybind(profileName: String): Boolean {
        val removed = profileKeybinds.remove(profileName) != null
        if (removed) {
            LOGGER.info("Removed keybind for profile: $profileName")
        }
        return removed
    }
}
