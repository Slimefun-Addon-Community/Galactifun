package io.github.addoncommunity.galactifun.base.objects

import io.github.addoncommunity.galactifun.api.objects.planet.PlanetaryWorld
import io.github.addoncommunity.galactifun.api.objects.properties.DayCycle
import io.github.addoncommunity.galactifun.api.objects.properties.Orbit
import io.github.addoncommunity.galactifun.base.BaseUniverse
import io.github.addoncommunity.galactifun.pluginInstance
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.WorldCreator

object Earth : PlanetaryWorld(
    "Earth",
    Material.GRASS_BLOCK,
    BaseUniverse.solarSystem,
    Orbit.kilometers(149600000L, 1.0),
    DayCycle.EARTH_LIKE
) {
    override fun loadWorld(): World {
        val name = pluginInstance.config.getString("worlds.earth") ?: "world"
        return WorldCreator(name).createWorld() // load the world
            ?: error("Failed to read earth world name from config; no default world found!")
    }
}