package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.UniversalObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.aliens.MutantCreeper;
import io.github.addoncommunity.galactifun.base.aliens.TitanAlien;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Venus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.EarthOrbit;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.TheMoon;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter.Europa;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter.Io;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter.Jupiter;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Enceladus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Saturn;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Titan;
import io.github.addoncommunity.galactifun.util.ItemChoice;
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
    
    public static final AlienWorld VENUS = new Venus();

    public static final CelestialBody EARTH = new Earth();
    public static final AlienWorld EARTH_ORBIT = new EarthOrbit();
    public static final AlienWorld THE_MOON = new TheMoon();
    
    public static final AlienWorld MARS = new Mars();

    public static final CelestialBody JUPITER = new Jupiter();
    public static final AlienWorld IO = new Io();
    public static final AlienWorld EUROPA = new Europa();

    public static final CelestialBody SATURN = new Saturn();
    public static final AlienWorld ENCELADUS = new Enceladus();
    public static final AlienWorld TITAN_MOON = new Titan();

    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", Orbit.lightYears(27_000D),
            StarSystemType.NORMAL, new ItemChoice(Material.SUNFLOWER));
    
    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", Orbit.lightYears(12_000_000_000D),
            GalaxyType.SPIRAL, new ItemChoice(Material.MILK_BUCKET));
    
    public static final Alien MARTIAN = new Martian();
    public static final Alien ALIEN_CREEPER = new MutantCreeper();
    public static final Alien TITAN_ALIEN = new TitanAlien();

    public static void setup() {
        // aliens
        THE_MOON.addSpecies(ALIEN_CREEPER);
        TITAN_MOON.addSpecies(TITAN_ALIEN);
        MARS.addSpecies(MARTIAN);
        
        // orbiters
        EARTH.addOrbiters(THE_MOON, EARTH_ORBIT);
        JUPITER.addOrbiters(IO, EUROPA);
        SATURN.addOrbiters(TITAN_MOON, ENCELADUS);
        SOLAR_SYSTEM.addOrbiters(VENUS, EARTH_ORBIT, MARS, JUPITER, SATURN);
        MILKY_WAY.addOrbiters(SOLAR_SYSTEM);
        
        // register
        UniversalObject.registerAll(THE_MOON, ENCELADUS, EARTH_ORBIT, VENUS, EARTH, MARS, IO,
                EUROPA, JUPITER, TITAN_MOON, SATURN, SOLAR_SYSTEM, MILKY_WAY);
    }

}
