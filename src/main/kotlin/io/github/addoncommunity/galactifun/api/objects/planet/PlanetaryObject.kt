package io.github.addoncommunity.galactifun.api.objects.planet

import io.github.addoncommunity.galactifun.api.objects.UniversalObject
import io.github.addoncommunity.galactifun.api.objects.properties.DayCycle
import io.github.addoncommunity.galactifun.api.objects.properties.Orbit
import org.bukkit.inventory.ItemStack

open class PlanetaryObject(
    name: String,
    baseItem: ItemStack,
    orbiting: UniversalObject,
    orbit: Orbit,
    val dayCycle: DayCycle
) : UniversalObject(name, baseItem, orbiting, orbit) {
}