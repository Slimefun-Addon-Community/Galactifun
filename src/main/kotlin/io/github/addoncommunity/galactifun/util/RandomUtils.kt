package io.github.addoncommunity.galactifun.util

import io.github.addoncommunity.galactifun.pluginInstance
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.entity.Entity

fun String.toKey(): NamespacedKey = NamespacedKey(pluginInstance, this)

inline fun <reified T : Entity> World.getNearbyEntitiesByType(
    location: Location,
    radius: Double,
    crossinline predicate: (T) -> Boolean = { true }
): List<T> = buildList {
    for (entity in getNearbyEntities(location, radius, radius, radius)) {
        if (entity is T && predicate(entity)) {
            add(entity)
        }
    }
}

inline fun <reified T : Entity> World.spawn(location: Location): T = spawn(location, T::class.java)