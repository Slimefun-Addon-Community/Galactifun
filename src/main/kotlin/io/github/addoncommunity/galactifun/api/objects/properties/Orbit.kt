package io.github.addoncommunity.galactifun.api.objects.properties

import io.github.addoncommunity.galactifun.util.Constants
import kotlin.math.PI

class Orbit private constructor(val distance: Double, year: Double) {

    private val year = EARTH_YEAR * year * 1200000 // why 1200000? it was in the original code

    val position: Double
        get() {
            if (year == 0.0) return 0.0
            return (System.currentTimeMillis() % year) * PI * 2 / year
        }

    init {
        require(year >= 0) { "Year must be positive" }
        require(distance >= 0) { "Distance must be positive" }
    }

    companion object {
        fun lightYears(distance: Double, years: Double, days: Double = 0.0) = Orbit(distance, years + days / 365.25)
        fun kilometers(distance: Long, years: Double, days: Double = 0.0) =
            Orbit(distance / Constants.KM_PER_LY, years + days / 365.25)
    }
}

/**
 * The number of Minecraft days in an Earth year
 */
private const val EARTH_YEAR = 30