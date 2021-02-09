package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.type.GalaxyType;
import io.github.addoncommunity.galactifun.api.universe.type.StarSystemType;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.aliens.MutantCreeper;
import io.github.addoncommunity.galactifun.base.aliens.TitanAlien;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Venus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.EarthOrbit;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.TheMoon;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Enceladus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Saturn;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Titan;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;

/**
 * Registry of constants for the base celestial objects
 * 
 * @author Mooy1
 * @author Seggan
 */
@UtilityClass
public final class BaseRegistry {
    
    public static final AlienWorld THE_MOON = new TheMoon();
    public static final AlienWorld ENCELADUS = new Enceladus();
    public static final AlienWorld EARTH_ORBIT = new EarthOrbit();
    
    public static final CelestialBody EARTH = new Earth(EARTH_ORBIT, THE_MOON);
    public static final AlienWorld MARS = new Mars();
    public static final AlienWorld VENUS = new Venus();
    public static final AlienWorld TITAN_MOON = new Titan();
    public static final CelestialBody SATURN = new Saturn(TITAN_MOON, ENCELADUS);

    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", new Orbit(27_000D),
            StarSystemType.NORMAL, new ItemChoice(Material.SUNFLOWER), VENUS, EARTH, MARS, SATURN) {
    };
    
    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", new Orbit(12_000_000_000D),
            GalaxyType.SPIRAL, new ItemChoice(Material.MILK_BUCKET), SOLAR_SYSTEM) {
    };
    
    public static final Martian MARTIAN = new Martian(MARS);
    public static final MutantCreeper ALIEN_CREEPER = new MutantCreeper(THE_MOON);
    public static final TitanAlien TITAN_ALIEN = new TitanAlien(TITAN_MOON);

    public static void setup() {
        // just loading static fields for now
    }

}
