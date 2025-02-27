package com.willfp.libreforge.triggers.mutators

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.triggers.DataMutator
import com.willfp.libreforge.triggers.TriggerData
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Tameable

class MutatorVictimToOwner : DataMutator("victim_to_owner") {
    override fun mutate(data: TriggerData, config: Config): TriggerData {
        val victim = data.victim as? Tameable ?: return data
        val owner = victim.owner as? LivingEntity ?: return data

        return data.copy(
            victim = owner
        )
    }
}
