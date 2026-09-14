package com.nikoo.nikoclient.gui.screen

import com.nikoo.nikoclient.inventory.MacroExecutor
import com.nikoo.nikoclient.inventory.model.Profile
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

class ProfileEditorScreen(private val parentScreen: Screen, val profile: Profile) : Screen(Text.literal("Edit Profile: ${profile.name}")) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger("ProfileEditorScreen")
        private const val BUTTON_WIDTH = 120
        private const val BUTTON_HEIGHT = 20
        private const val SLOT_SIZE = 18
    }

    private var selectedSlot: Int = -1

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context, mouseX, mouseY, delta)
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF)

        // Draw inventory grid representation
        drawInventoryGrid(context, mouseX, mouseY)

        super.render(context, mouseX, mouseY, delta)
    }

    private fun drawInventoryGrid(context: DrawContext, mouseX: Int, mouseY: Int) {
        val startX = 20
        val startY = 60
        val columns = 9

        // Draw hotbar (slots 0-8)
        context.drawTextWithShadow(this.textRenderer, Text.literal("Hotbar:"), startX, startY - 15, 0xFFFFFF)
        for (i in 0..8) {
            val x = startX + (i * (SLOT_SIZE + 2))
            val y = startY
            drawSlot(context, x, y, i, mouseX, mouseY)
        }

        // Draw main inventory (slots 9-35)
        var slotIndex = 9
        context.drawTextWithShadow(this.textRenderer, Text.literal("Main Inventory:"), startX, startY + 35, 0xFFFFFF)
        for (row in 0..2) {
            for (col in 0..8) {
                val x = startX + (col * (SLOT_SIZE + 2))
                val y = startY + 50 + (row * (SLOT_SIZE + 2))
                drawSlot(context, x, y, slotIndex, mouseX, mouseY)
                slotIndex++
            }
        }

        // Draw armor slots (36-39)
        val armorStartX = this.width - 100
        val armorStartY = startY
        context.drawTextWithShadow(this.textRenderer, Text.literal("Armor:"), armorStartX, armorStartY - 15, 0xFFFFFF)
        val armorLabels = listOf("Head", "Chest", "Legs", "Feet")
        for (i in 0..3) {
            val x = armorStartX
            val y = armorStartY + (i * (SLOT_SIZE + 2))
            context.drawTextWithShadow(this.textRenderer, Text.literal(armorLabels[i]), x - 40, y + 2, 0xAAAAAA)
            drawSlot(context, x, y, 36 + i, mouseX, mouseY)
        }

        // Draw offhand slot (40)
        val offhandStartX = armorStartX
        val offhandStartY = armorStartY + 120
        context.drawTextWithShadow(this.textRenderer, Text.literal("Offhand:"), offhandStartX - 40, offhandStartY - 5, 0xAAAAAA)
        drawSlot(context, offhandStartX, offhandStartY, 40, mouseX, mouseY)
    }

    private fun drawSlot(context: DrawContext, x: Int, y: Int, slotIndex: Int, mouseX: Int, mouseY: Int) {
        val isConfigured = profile.isSlotConfigured(slotIndex)
        val isSelected = slotIndex == selectedSlot
        val isHovered = mouseX >= x && mouseX < x + SLOT_SIZE && mouseY >= y && mouseY < y + SLOT_SIZE

        val color = when {
            isSelected -> 0xFF00FF
            isConfigured -> 0x00FF00
            isHovered -> 0x888888
            else -> 0x444444
        }

        context.fill(x, y, x + SLOT_SIZE, y + SLOT_SIZE, color)
        context.drawBorder(x, y, SLOT_SIZE, SLOT_SIZE, 0xFFFFFF)

        // Draw slot index
        val label = slotIndex.toString()
        context.drawTextWithShadow(this.textRenderer, Text.literal(label), x + 2, y + 2, 0xFFFFFF)
    }

    override fun init() {
        super.init()

        val buttonY = this.height - 60

        // Test/Execute button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Execute Profile"),
                { MacroExecutor.executeProfile(profile) }
            ).dimensions(20, buttonY, 120, 20)
                .build()
        )

        // Save button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Save"),
                {
                    // Save is automatic, but can add explicit save feedback here
                    LOGGER.info("Profile saved: ${profile.name}")
                }
            ).dimensions(150, buttonY, 80, 20)
                .build()
        )

        // Back button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Back"),
                { this.client?.setScreen(parentScreen) }
            ).dimensions(this.width - 100, buttonY, 80, 20)
                .build()
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        // Handle slot selection
        val startX = 20
        val startY = 60

        // Check hotbar slots
        for (i in 0..8) {
            val x = startX + (i * 20)
            if (mouseX >= x && mouseX < x + SLOT_SIZE && mouseY >= startY && mouseY < startY + SLOT_SIZE) {
                selectedSlot = i
                LOGGER.info("Selected slot: $i")
                return true
            }
        }

        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun close() {
        this.client?.setScreen(parentScreen)
    }
}
