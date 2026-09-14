package com.nikoo.nikoclient.gui.screen

import com.nikoo.nikoclient.inventory.ProfileManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class InventoryMacrosScreen(private val parentScreen: Screen) : Screen(Text.literal("Inventory Macros")) {
    companion object {
        private const val BUTTON_WIDTH = 150
        private const val BUTTON_HEIGHT = 20
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context, mouseX, mouseY, delta)
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF)
        super.render(context, mouseX, mouseY, delta)
    }

    override fun init() {
        super.init()

        val centerX = this.width / 2
        var buttonY = 50

        // Profiles button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Profiles"),
                { this.client?.setScreen(ProfileListScreen(this)) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
        buttonY += 25

        // Keybinds button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Keybinds"),
                { this.client?.setScreen(KeybindsScreen(this)) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
        buttonY += 25

        // Back button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Back"),
                { this.client?.setScreen(parentScreen) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY + 30, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
    }

    override fun close() {
        this.client?.setScreen(parentScreen)
    }
}
