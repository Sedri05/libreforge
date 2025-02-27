package com.willfp.libreforge.effects.effects

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.ConfigViolation
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.effects.getEffectAmount
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*

class EffectKeepInventory : Effect("keep_inventory") {
    private val players = mutableMapOf<UUID, MutableList<UUID>>()

    override fun handleEnable(player: Player, config: Config) {
        val existing = players[player.uniqueId] ?: mutableListOf()
        existing.add(this.getUUID(player.getEffectAmount(this)))
        players[player.uniqueId] = existing
    }

    override fun handleDisable(player: Player) {
        val existing = players[player.uniqueId] ?: mutableListOf()
        existing.remove(this.getUUID(player.getEffectAmount(this)))
        players[player.uniqueId] = existing
    }

    @EventHandler(ignoreCancelled = true)
    fun handle(event: PlayerDeathEvent) {
        val player = event.player

        if ((players[player.uniqueId] ?: emptyList()).isNotEmpty()) {
            event.keepInventory = true
        }
    }

    override fun validateConfig(config: Config): List<ConfigViolation> {
        return mutableListOf()
    }
}
