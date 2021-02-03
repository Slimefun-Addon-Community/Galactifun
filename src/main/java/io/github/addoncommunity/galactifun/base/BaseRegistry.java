package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.base.aliens.AlienCreeper;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Venus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.TheMoon;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Saturn;
import lombok.experimental.UtilityClass;

/**
 * Registry of constants for the base celestial objects
 * 
 * @author Mooy1
 * @author Seggan
 */
@UtilityClass
public final class BaseRegistry {
    
    public static final Planet EARTH = new Earth();
    public static final Planet MARS = new Mars();
    public static final Planet VENUS = new Venus();
    public static final Planet SATURN = new Saturn();

    public static final MoonWorld THE_MOON = new TheMoon(EARTH);

    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", VENUS, EARTH, MARS, SATURN);
    
    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", SOLAR_SYSTEM);

    public static final Martian MARTIAN = new Martian(BaseRegistry.MARS);
    public static final AlienCreeper ALIEN_CREEPER = new AlienCreeper(BaseRegistry.MARS);

    public static void setup() {
        // just loading static fields for now
    }

}
