package io.github.addoncommunity.galactifun.api.objects

import io.github.addoncommunity.galactifun.api.objects.properties.Orbit
import io.github.bakedlibs.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class UniversalObject private constructor(
    name: String,
    baseItem: ItemStack,
    // Special case for The Universe
    private val _orbiting: UniversalObject?,
    val orbit: Orbit
) {

    val name = ChatUtils.removeColorCodes(name)
    val id = this.name.lowercase().replace(' ', '_')

    val item = CustomItemStack(baseItem, name)

    val orbiting: UniversalObject
        get() = _orbiting ?: error("The Universe does not orbit anything")

    val orbitLevel: Int = _orbiting?.let { it.orbitLevel + 1 } ?: 0

    private val _orbiters = mutableListOf<UniversalObject>()
    val orbiters: List<UniversalObject>
        get() = _orbiters.toList()

    internal constructor() : this("The Universe", ItemStack(Material.NETHER_STAR), null, Orbit(0.0))

    protected constructor(name: String, baseItem: ItemStack, orbiting: UniversalObject, orbit: Orbit) : this(
        name,
        baseItem,
        // Force it to call the private constructor
        orbiting as UniversalObject?,
        orbit
    )

    fun addOrbiter(orbiter: UniversalObject) {
        _orbiters.add(orbiter)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UniversalObject) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}