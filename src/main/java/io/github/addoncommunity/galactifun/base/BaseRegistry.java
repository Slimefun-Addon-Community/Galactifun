package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.CelestialObject;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.Moon;
import io.github.addoncommunity.galactifun.api.universe.Planet;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Saturn;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.TheMoon;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Venus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.mars.Mars;
import lombok.experimental.UtilityClass;

/**
 * Registry of constants for the base celestial objects
 * 
 * @author Mooy1
 * @author Seggan
 */
@UtilityClass
public final class BaseRegistry {
    
    public static final Moon THE_MOON = new TheMoon();

    public static final Planet EARTH = new Earth((TheMoon) THE_MOON);
    public static final Planet MARS = new Mars();
    public static final Planet VENUS = new Venus();

    public static final CelestialObject SATURN = new Saturn();
    
    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", VENUS, EARTH, MARS, SATURN);
    
    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", SOLAR_SYSTEM);
    
    public static void setup() {
        // just loading static fields for now
    }

}
