package io.github.addoncommunity.galactifun.api.objects.properties

import org.bukkit.GameRule
import org.bukkit.World

class DayCycle {

    companion object {

        val ETERNAL_DAY = DayCycle(6000)
        val ETERNAL_NIGHT = DayCycle(18000)
        val EARTH_LIKE = DayCycle(days = 1)

        fun eternal(time: Long) = DayCycle(time)

        fun relativeToEarth(ratio: Double) = DayCycle(days = ratio.toInt(), hours = (ratio * 24).toInt())
    }

    val description: String
    private val perFiveSeconds: Long
    private val startTime: Long

    constructor(days: Int = 0, hours: Int = 0) {
        require(days * 24 + hours > 0) { "A day cycle must last longer than 1 hour" }
        description = buildString {
            if (days > 0) {
                append(days)
                append(" day")
                if (days != 1) {
                    append('s')
                }
                append(' ')
            }
            if (hours > 0) {
                append(hours)
                append(" hour")
                if (hours != 1) {
                    append('s')
                }
            }
        }
        perFiveSeconds = days * 100L + hours * 4L
        startTime = -1
    }

    /**
     * Constructor for eternal day or night
     */
    private constructor(time: Long) {
        description = "Eternal " + (if (time < 12000) "day" else "night")
        perFiveSeconds = 0
        startTime = time
    }

    fun applyEffects(world: World) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        if (startTime != -1L) {
            world.time = startTime
        }
    }

    fun tick(world: World) {
        if (perFiveSeconds != 0L) {
            world.time += perFiveSeconds
        }
    }
}