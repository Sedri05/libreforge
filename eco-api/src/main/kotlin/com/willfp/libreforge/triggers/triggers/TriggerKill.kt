package com.willfp.libreforge.triggers.triggers

import com.willfp.eco.core.events.EntityDeathByEntityEvent
import com.willfp.eco.core.integrations.antigrief.AntigriefManager
import com.willfp.eco.core.integrations.mcmmo.McmmoManager
import com.willfp.libreforge.triggers.Trigger
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler

class TriggerKill : Trigger(
    "kill", listOf(
        TriggerParameter.PLAYER,
        TriggerParameter.VICTIM,
        TriggerParameter.LOCATION
    )
) {
    @EventHandler(ignoreCancelled = true)
    fun handle(event: EntityDeathByEntityEvent) {
        if (McmmoManager.isFake(event)) {
            return
        }

        var killer: Any? = null
        if (event.killer is Player) {
            killer = event.killer
        } else if (event.killer is Projectile) {
            if ((event.killer as Projectile).shooter is Player) {
                killer = (event.killer as Projectile).shooter
            }
        }

        if (killer !is Player) {
            return
        }

        val victim = event.victim

        if (!AntigriefManager.canInjure(killer, victim)) {
            return
        }

        this.processTrigger(
            killer,
            TriggerData(
                player = killer,
                victim = victim,
                location = victim.location
            )
        )
    }
}
