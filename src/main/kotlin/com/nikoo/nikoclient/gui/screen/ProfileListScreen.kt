package com.nikoo.nikoclient.gui.screen

import com.nikoo.nikoclient.inventory.ProfileManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Text
import org.slf4j.LoggerFactory

class ProfileListScreen(private val parentScreen: Screen) : Screen(Text.literal("Profiles")) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger("ProfileListScreen")
        private const val BUTTON_WIDTH = 150
        private const val BUTTON_HEIGHT = 20
    }

    private lateinit var profileNameInput: TextFieldWidget

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context, mouseX, mouseY, delta)
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF)
        profileNameInput.render(context, mouseX, mouseY, delta)
        super.render(context, mouseX, mouseY, delta)
    }

    override fun init() {
        super.init()

        val centerX = this.width / 2
        var elementY = 50

        // Profile name input
        profileNameInput = TextFieldWidget(this.textRenderer, centerX - 100, elementY, 200, 20, Text.literal("Profile Name"))
        profileNameInput.setMaxLength(32)
        this.addSelectableChild(profileNameInput)
        this.setInitialFocus(profileNameInput)
        elementY += 30

        // Create profile button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Create Profile"),
                {
                    val profileName = profileNameInput.text.trim()
                    if (profileName.isNotEmpty()) {
                        if (ProfileManager.profileExists(profileName)) {
                            LOGGER.warn("Profile already exists: $profileName")
                        } else {
                            ProfileManager.createProfile(profileName)
                            profileNameInput.text = ""
                            LOGGER.info("Created profile: $profileName")
                        }
                    }
                }
            ).dimensions(centerX - BUTTON_WIDTH / 2, elementY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
        elementY += 30

        // List existing profiles
        val profiles = ProfileManager.getAllProfiles()
        if (profiles.isNotEmpty()) {
            context.drawTextWithShadow(this.textRenderer, Text.literal("Existing Profiles:"), centerX - 75, elementY, 0xAAAAAA)
            elementY += 15

            for (profile in profiles) {
                this.addDrawableChild(
                    net.minecraft.client.gui.widget.ButtonWidget.builder(
                        Text.literal(profile.name),
                        { this.client?.setScreen(ProfileEditorScreen(this, profile)) }
                    ).dimensions(centerX - BUTTON_WIDTH / 2, elementY, BUTTON_WIDTH - 30, BUTTON_HEIGHT)
                        .build()
                )

                // Delete button
                this.addDrawableChild(
                    net.minecraft.client.gui.widget.ButtonWidget.builder(
                        Text.literal("X"),
                        {
                            ProfileManager.deleteProfile(profile.name)
                            this.init()
                        }
                    ).dimensions(centerX + BUTTON_WIDTH / 2 - 25, elementY, 25, BUTTON_HEIGHT)
                        .build()
                )
                elementY += 25
            }
        }

        elementY += 20

        // Back button
        this.addDrawableChild(
            net.minecraft.client.gui.widget.ButtonWidget.builder(
                Text.literal("Back"),
                { this.client?.setScreen(parentScreen) }
            ).dimensions(centerX - BUTTON_WIDTH / 2, elementY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build()
        )
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (profileNameInput.keyPressed(keyCode, scanCode, modifiers)) {
            return true
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun close() {
        this.client?.setScreen(parentScreen)
    }
}
