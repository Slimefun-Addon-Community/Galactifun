package io.github.addoncommunity.galactifun.api.objects.properties

import io.github.addoncommunity.galactifun.util.Constants
import kotlin.math.PI

class Orbit(kmDistance: Double, years: Double = 0.0, days: Double = 0.0) {

    private val year = EARTH_YEAR * (years + days / 365.25) * 1200000 // why 1200000? it was in the original code

    val distance = kmDistance / Constants.KM_PER_LY

    val position: Double
        get() {
            if (year == 0.0) return 0.0
            return (System.currentTimeMillis() % year) * PI * 2 / year
        }

    init {
        require(year >= 0) { "Year must be positive" }
        require(distance >= 0) { "Distance must be positive" }
    }
}

/**
 * The number of Minecraft days in an Earth year
 */
private const val EARTH_YEAR = 30