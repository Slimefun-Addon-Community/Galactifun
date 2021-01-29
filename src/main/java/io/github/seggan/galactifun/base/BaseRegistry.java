package io.github.seggan.galactifun.base;

import io.github.seggan.galactifun.api.Galaxy;
import io.github.seggan.galactifun.api.StarSystem;
import io.github.seggan.galactifun.api.Moon;
import io.github.seggan.galactifun.api.Planet;
import io.github.seggan.galactifun.base.milkyway.solarsystem.Mars;
import io.github.seggan.galactifun.base.milkyway.solarsystem.TheMoon;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class BaseRegistry {
    
    public static final Moon THE_MOON = new TheMoon();

    public static final Planet MARS = new Mars();
    
    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", MARS);

    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", SOLAR_SYSTEM);

}
