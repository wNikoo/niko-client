package com.nikoo.nikoclient.gui.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class NikoClientMainScreen : Screen(Text.literal("Niko Client")) {
    companion object {
        private const val BUTTON_WIDTH = 200
        private const val BUTTON_HEIGHT = 20
        private const val BUTTON_SPACING = 25
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

        // Inventory Macros button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Inventory Macros"),
                { this.client?.setScreen(InventoryMacrosScreen(this)) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
        buttonY += BUTTON_SPACING

        // Settings button (placeholder)
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Settings"),
                { /* TODO: Implement settings */ }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
        buttonY += BUTTON_SPACING

        // Back button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Back"),
                { this.client?.setScreen(null) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY + 20, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
    }

    override fun close() {
        this.client?.setScreen(null)
    }
}
