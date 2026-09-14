package com.nikoo.nikoclient.command

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text

object CommandRegistry {
    fun register() {
        ClientCommandManager.DISPATCHER.register(
            ClientCommandManager.literal("nikoclient")
                .executes { context ->
                    handleNikoClientCommand(context)
                }
        )
        
        ClientCommandManager.DISPATCHER.register(
            ClientCommandManager.literal("nc")
                .executes { context ->
                    handleNikoClientCommand(context)
                }
        )
    }

    private fun handleNikoClientCommand(context: CommandContext<FabricClientCommandSource>): Int {
        val source = context.source
        source.sendFeedback(Text.literal("§6Niko Client - Opening GUI..."))
        
        // Open main GUI - will be implemented
        // ScreenManager.openMainScreen()
        
        return 1
    }
}
