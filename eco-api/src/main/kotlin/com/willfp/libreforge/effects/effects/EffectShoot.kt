package com.willfp.libreforge.effects.effects

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.core.entities.Entities
import com.willfp.eco.util.runExempted
import com.willfp.libreforge.ConfigViolation
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.Triggers
import com.willfp.libreforge.triggers.wrappers.WrappedShootBowEvent
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Projectile

@Suppress("UNCHECKED_CAST")
class EffectShoot : Effect(
    "shoot",
    applicableTriggers = Triggers.withParameters(
        TriggerParameter.PLAYER
    )
) {
    override fun handle(data: TriggerData, config: Config) {
        val player = data.player ?: return
        val velocity = data.velocity
        val fire = (data.event as? WrappedShootBowEvent)?.hasFire
        val projectileClass = Entities.lookup(config.getString("projectile"))::class.java

        player.runExempted {
            val projectile = if (velocity == null || !config.getBool("inherit_velocity")) {
                player.launchProjectile(projectileClass as Class<out Projectile>)
            } else {
                player.launchProjectile(projectileClass as Class<out Projectile>, velocity)
            }

            if (projectile is AbstractArrow) {
                projectile.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
            }
            if (fire == true) {
                projectile.fireTicks = Int.MAX_VALUE
            }
        }
    }

    override fun validateConfig(config: Config): List<ConfigViolation> {
        val violations = mutableListOf<ConfigViolation>()

        if (!config.has("projectile")) violations.add(
            ConfigViolation(
                "projectile",
                "You must specify the projectile!"
            )
        )

        return violations
    }
}
