package com.willfp.libreforge.triggers.triggers

import com.willfp.eco.core.integrations.mcmmo.McmmoManager
import com.willfp.libreforge.triggers.GenericCancellableEvent
import com.willfp.libreforge.triggers.Trigger
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerToggleSprintEvent

class TriggerToggleSprint : Trigger(
    "toggle_sprint", listOf(
        TriggerParameter.PLAYER,
        TriggerParameter.LOCATION,
        TriggerParameter.EVENT
    )
) {
    @EventHandler(ignoreCancelled = true)
    fun handle(event: PlayerToggleSprintEvent) {
        if (McmmoManager.isFake(event)) {
            return
        }
        val player = event.player

        this.processTrigger(
            player,
            TriggerData(
                player = player,
                location = player.location,
                event = GenericCancellableEvent(event)
            )
        )
    }
}
