package io.github.addoncommunity.galactifun.api.objects.planet

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class AlienWorld {

    private val blockMappings = EnumMap<Material, ItemStack>(Material::class.java)
}