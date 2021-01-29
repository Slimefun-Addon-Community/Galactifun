package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.Galaxy;
import io.github.addoncommunity.galactifun.api.Moon;
import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.StarSystem;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.TheMoon;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class BaseRegistry {
    
    public static final Moon THE_MOON = new TheMoon();

    public static final Planet MARS = new Mars();
    public static final Planet EARTH = new Earth(THE_MOON);
    
    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", EARTH, MARS);

    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", SOLAR_SYSTEM);

    public static void setup() {
        
    }
    
}
