package com.nikoo.nikoclient.gui.widget

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.widget.ClickableWidget
import net.minecraft.text.Text

class SlotWidget(
    x: Int,
    y: Int,
    private val slotIndex: Int,
    private val textRenderer: TextRenderer,
    private val onClicked: (Int) -> Unit
) : ClickableWidget(x, y, 18, 18, Text.literal(slotIndex.toString())) {

    private var isSelected = false
    private var isConfigured = false
    private var itemDisplayName = ""

    override fun renderButton(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val hovered = this.isMouseOver(mouseX.toDouble(), mouseY.toDouble())
        val color = when {
            isSelected -> 0xFF00FF
            isConfigured -> 0x00FF00
            hovered -> 0x888888
            else -> 0x444444
        }

        context.fill(this.x, this.y, this.x + this.width, this.y + this.height, color)
        context.drawBorder(this.x, this.y, this.width, this.height, 0xFFFFFF)

        context.drawTextWithShadow(
            textRenderer,
            Text.literal(slotIndex.toString()),
            this.x + 2,
            this.y + 2,
            0xFFFFFF
        )
    }

    override fun onClick(mouseX: Double, mouseY: Double) {
        onClicked(slotIndex)
    }

    fun setSelected(selected: Boolean) {
        this.isSelected = selected
    }

    fun setConfigured(configured: Boolean) {
        this.isConfigured = configured
    }

    fun setItemDisplay(name: String) {
        this.itemDisplayName = name
    }
}
