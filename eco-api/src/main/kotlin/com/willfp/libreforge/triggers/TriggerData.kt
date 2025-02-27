package com.willfp.libreforge.triggers

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.util.Vector

data class TriggerData(
    val player: Player? = null,
    val victim: LivingEntity? = null,
    val block: Block? = null,
    val event: WrappedEvent<*>? = null,
    val location: Location? = victim?.location ?: player?.location,
    val projectile: Projectile? = null,
    val damageCause: EntityDamageEvent.DamageCause? = null,
    val velocity: Vector? = null
)

enum class TriggerParameter {
    PLAYER,
    VICTIM,
    BLOCK,
    EVENT,
    LOCATION,
    PROJECTILE,
    DAMAGE_CAUSE,
    VELOCITY
}
