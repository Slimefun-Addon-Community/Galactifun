package io.github.addoncommunity.galactifun.api.objects

import io.github.addoncommunity.galactifun.api.objects.properties.Orbit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Galaxy(name: String, baseItem: ItemStack, orbiting: UniversalObject, orbit: Orbit) :
    UniversalObject(name, baseItem, orbiting, orbit) {

    constructor(name: String, baseItem: Material, orbiting: UniversalObject, orbit: Orbit) : this(
        name,
        ItemStack(baseItem),
        orbiting,
        orbit
    )
}