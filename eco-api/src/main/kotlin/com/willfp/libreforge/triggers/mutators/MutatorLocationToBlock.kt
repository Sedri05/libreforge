package com.willfp.libreforge.triggers.mutators

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.triggers.DataMutator
import com.willfp.libreforge.triggers.TriggerData

class MutatorLocationToBlock : DataMutator(
    "location_to_block"
) {
    override fun mutate(data: TriggerData, config: Config): TriggerData {
        val block = data.block ?: return data
        return data.copy(
            location = block.location
        )
    }
}
