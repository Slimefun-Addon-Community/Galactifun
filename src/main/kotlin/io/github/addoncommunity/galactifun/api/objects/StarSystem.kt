package io.github.addoncommunity.galactifun.api.objects

import io.github.addoncommunity.galactifun.api.objects.properties.Orbit
import org.bukkit.Material
import kotlin.math.abs

class StarSystem(name: String, orbiting: UniversalObject, orbit: Orbit) :
    UniversalObject(name, Material.SUNFLOWER, orbiting, orbit) {

    override fun distanceTo(other: UniversalObject): Double {
        return if (orbitLevel > other.orbitLevel) {
            other.orbit.distance + distanceTo(other.orbiting)
        } else if (orbitLevel == other.orbitLevel) {
            abs(this.orbit.distance - other.orbit.distance)
        } else {
            super.distanceTo(other)
        }
    }
}