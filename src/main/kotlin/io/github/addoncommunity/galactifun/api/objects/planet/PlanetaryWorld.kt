package io.github.addoncommunity.galactifun.api.objects.planet

import io.github.addoncommunity.galactifun.api.objects.UniversalObject
import io.github.addoncommunity.galactifun.api.objects.properties.DayCycle
import io.github.addoncommunity.galactifun.api.objects.properties.Orbit
import io.github.addoncommunity.galactifun.core.managers.WorldManager
import io.github.addoncommunity.galactifun.util.getNearbyEntitiesByType
import io.github.addoncommunity.galactifun.util.spawn
import io.github.addoncommunity.galactifun.util.toKey
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Marker
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType


/**
 * A planetary object that has a world. This is for adapting vanilla or plugin worlds to the Galactifun API.
 * If you want to create a custom world, use [AlienWorld]
 *
 * @author Seggan
 */
abstract class PlanetaryWorld(
    name: String,
    baseItem: ItemStack,
    orbiting: UniversalObject,
    orbit: Orbit,
    dayCycle: DayCycle
) : PlanetaryObject(name, baseItem, orbiting, orbit, dayCycle) {

    lateinit var world: World
        private set

    lateinit var worldStorage: PersistentDataHolder
        private set

    fun register() {
        world = loadWorld()

        val markers = world.getNearbyEntitiesByType<Marker>(
            Location(world, 0.0, 0.0, 0.0),
            0.1
        ) { it.persistentDataContainer.has(worldStorageKey, PersistentDataType.STRING) }
        if (markers.isEmpty()) {
            worldStorage = world.spawn(Location(world, 0.0, 0.0, 0.0))
            worldStorage.persistentDataContainer.set(worldStorageKey, PersistentDataType.STRING, "")
        } else {
            worldStorage = markers.first()
        }

        WorldManager.registerWorld(this)
    }

    abstract fun loadWorld(): World
}

private val worldStorageKey = "world_storage".toKey()