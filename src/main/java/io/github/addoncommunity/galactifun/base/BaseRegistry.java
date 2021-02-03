package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.base.aliens.AlienCreeper;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Saturn;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Venus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.TheMoon;
import lombok.experimental.UtilityClass;

/**
 * Registry of constants for the base celestial objects
 * 
 * @author Mooy1
 * @author Seggan
 */
@UtilityClass
public final class BaseRegistry {
    
    public static final CelestialBody THE_MOON = new TheMoon();
    public static final CelestialBody EARTH = new Earth((TheMoon) THE_MOON);
    public static final CelestialBody MARS = new Mars();
    public static final CelestialBody VENUS = new Venus();
    public static final CelestialBody SATURN = new Saturn();

    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", null, VENUS, EARTH, MARS, SATURN);
    
    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", null, SOLAR_SYSTEM);

    public static final Martian MARTIAN = new Martian();
    public static final AlienCreeper ALIEN_CREEPER = new AlienCreeper();

    public static void setup() {
        // just loading static fields for now
    }

}
