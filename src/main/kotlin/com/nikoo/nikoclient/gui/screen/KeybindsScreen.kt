package com.nikoo.nikoclient.gui.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class KeybindsScreen(private val parentScreen: Screen) : Screen(Text.literal("Keybinds")) {
    companion object {
        private const val BUTTON_WIDTH = 150
        private const val BUTTON_HEIGHT = 20
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context, mouseX, mouseY, delta)
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF)
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Keybind management coming soon"), this.width / 2, this.height / 2, 0xAAAAAA)
        super.render(context, mouseX, mouseY, delta)
    }

    override fun init() {
        super.init()

        val centerX = this.width / 2
        val buttonY = this.height - 40

        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Back"),
                { this.client?.setScreen(parentScreen) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
    }

    override fun close() {
        this.client?.setScreen(parentScreen)
    }
}
