package com.willfp.libreforge.triggers.triggers

import com.willfp.eco.core.integrations.mcmmo.McmmoManager
import com.willfp.libreforge.triggers.Trigger
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.wrappers.WrappedHungerEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.FoodLevelChangeEvent

class TriggerGainHunger : Trigger(
    "gain_hunger", listOf(
        TriggerParameter.PLAYER,
        TriggerParameter.EVENT
    )
) {
    @EventHandler(ignoreCancelled = true)
    fun handle(event: FoodLevelChangeEvent) {
        if (McmmoManager.isFake(event)) {
            return
        }

        val player = event.entity

        if (player !is Player) {
            return
        }

        if (event.foodLevel < player.foodLevel) {
            return
        }

        this.processTrigger(
            player,
            TriggerData(
                player = player,
                event = WrappedHungerEvent(event)
            )
        )
    }
}
