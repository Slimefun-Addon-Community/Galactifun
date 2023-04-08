package io.github.addoncommunity.galactifun.base;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.TheUniverse;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.universe.Mars;
import io.github.addoncommunity.galactifun.base.universe.Venus;
import io.github.addoncommunity.galactifun.base.universe.earth.Earth;
import io.github.addoncommunity.galactifun.base.universe.earth.EarthOrbit;
import io.github.addoncommunity.galactifun.base.universe.earth.TheMoon;
import io.github.addoncommunity.galactifun.base.universe.jupiter.Europa;
import io.github.addoncommunity.galactifun.base.universe.jupiter.Io;
import io.github.addoncommunity.galactifun.base.universe.saturn.Enceladus;
import io.github.addoncommunity.galactifun.base.universe.saturn.Titan;

/**
 * Registry of objects, aliens, and worlds in the base universe
 *
 * @author Mooy1
 * @author Seggan
 */
@UtilityClass
public final class BaseUniverse {

    public static final TheUniverse THE_UNIVERSE = new TheUniverse("The Universe");
    public static final Galaxy MILKY_WAY = new Galaxy(
            "Milky Way",
            GalaxyType.SPIRAL,
            Orbit.lightYears(12_000_000_000D, 0),
            THE_UNIVERSE,
            new ItemStack(Material.MILK_BUCKET)
    );
    public static final StarSystem SOLAR_SYSTEM = new StarSystem(
            "Solar System",
            StarSystemType.NORMAL,
            Orbit.lightYears(27_000, 250_000_000D),
            MILKY_WAY,
            new ItemStack(Material.SUNFLOWER)
    );
    public static final PlanetaryObject JUPITER = new PlanetaryObject(
            "&6Jupiter",
            PlanetaryType.GAS_GIANT,
            Orbit.kilometers(778_340_821L, 12D),
            SOLAR_SYSTEM,
            new ItemStack(Material.RED_DYE),
            DayCycle.hours(10),
            new AtmosphereBuilder()
                    .addStorm()
                    .addThunder()
                    .enableWeather()
                    .add(Gas.HYDROGEN, 90)
                    .add(Gas.HELIUM, 10)
                    .build(),
            Gravity.metersPerSec(24.79)
    );
    public static final AlienWorld IO = new Io(
            "&6Io",
            PlanetaryType.TERRESTRIAL,
            Orbit.kilometers(421_800L, 2),
            JUPITER,
            new ItemStack(Material.LAVA_BUCKET),
            DayCycle.hours(42),
            new AtmosphereBuilder()
                    .setPressure(0)
                    .addEffect(AtmosphericEffect.HEAT, 5)
                    .addEffect(AtmosphericEffect.RADIATION, 3)
                    .build(),
            Gravity.metersPerSec(1.796)
    );
    public static final AlienWorld EUROPA = new Europa(
            "&bEuropa",
            PlanetaryType.FROZEN,
            Orbit.kilometers(671_100, 3),
            JUPITER,
            new ItemStack(Material.ICE),
            DayCycle.ETERNAL_NIGHT,
            Atmosphere.NONE,
            Gravity.metersPerSec(1.315)
    );
    public static final PlanetaryObject SATURN = new PlanetaryObject(
            "Saturn",
            PlanetaryType.GAS_GIANT,
            Orbit.kilometers(1_490_500_000, 29D),
            SOLAR_SYSTEM,
            new ItemStack(Material.QUARTZ_BLOCK),
            DayCycle.hours(10),
            new AtmosphereBuilder()
                    .enableWeather()
                    .add(Gas.HYDROGEN, 96.3)
                    .add(Gas.HELIUM, 3.25)
                    .add(Gas.METHANE, 0.45)
                    .build(),
            Gravity.relativeToEarth(1.06)
    );
    public static final PlanetaryWorld EARTH = new Earth("Earth",
            PlanetaryType.TERRESTRIAL,
            Orbit.kilometers(149_600_000L, 1D),
            SOLAR_SYSTEM,
            new ItemStack(Material.GRASS_BLOCK),
            DayCycle.EARTH_LIKE,
            Atmosphere.EARTH_LIKE,
            Gravity.EARTH_LIKE
    );
    public static final AlienWorld EARTH_ORBIT = new EarthOrbit(
            "Earth Orbit",
            PlanetaryType.SPACE,
            Orbit.kilometers(24_000, 1),
            EARTH,
            new ItemStack(Material.OBSIDIAN),
            DayCycle.ETERNAL_NIGHT,
            Atmosphere.NONE, Gravity.ZERO
    );
    public static final AlienWorld VENUS = new Venus(
            "Venus",
            PlanetaryType.TERRESTRIAL,
            Orbit.kilometers(108_860_000L, 225),
            SOLAR_SYSTEM,
            new ItemStack(Material.BLACK_TERRACOTTA),
            DayCycle.days(117),
            new AtmosphereBuilder()
                    .setNether()
                    .addStorm()
                    .addThunder()
                    .addEffect(AtmosphericEffect.HEAT, 10)
                    .add(Gas.CARBON_DIOXIDE, 96.5)
                    .add(Gas.NITROGEN, 3.5)
                    .setPressure(93)
                    .build(),
            Gravity.metersPerSec(8.87)
    );
    public static final AlienWorld THE_MOON = new TheMoon(
            "The Moon",
            PlanetaryType.TERRESTRIAL,
            Orbit.kilometers(382_500L, 27),
            EARTH,
            new ItemStack(Material.ANDESITE),
            DayCycle.EARTH_LIKE,
            Atmosphere.NONE.toBuilder()
                    .addEffect(AtmosphericEffect.COLD, 1)
                    .build(),
            Gravity.MOON_LIKE
    );
    public static final AlienWorld MARS = new Mars(
            "&cMars",
            PlanetaryType.TERRESTRIAL,
            Orbit.kilometers(227_943_824L, 687),
            SOLAR_SYSTEM,
            new ItemStack(Material.RED_SAND),
            DayCycle.of(1, 1),
            new AtmosphereBuilder()
                    .add(Gas.CARBON_DIOXIDE, 94.9)
                    .add(Gas.NITROGEN, 2.6)
                    .add(Gas.ARGON, 1.9)
                    .addEffect(AtmosphericEffect.COLD, 2)
                    .setPressure(0.006)
                    .build(),
            Gravity.metersPerSec(3.711)
    );
    public static final AlienWorld TITAN = new Titan(
            "&6Titan",
            PlanetaryType.TERRESTRIAL,
            Orbit.kilometers(1_200_000L, 16),
            SATURN,
            new ItemStack(Material.SAND),
            DayCycle.EARTH_LIKE,
            new AtmosphereBuilder()
                    .enableWeather()
                    .add(Gas.NITROGEN, 97)
                    .add(Gas.METHANE, 2.7)
                    .add(Gas.HYDROCARBONS, 0.2)
                    .add(Gas.HYDROGEN, 0.1)
                    .addEffect(AtmosphericEffect.COLD, 6)
                    .setPressure(1.5)
                    .build(),
            Gravity.metersPerSec(1.352)
    );
    public static final AlienWorld ENCALADUS = new Enceladus(
            "&bEnceladus",
            PlanetaryType.FROZEN,
            Orbit.kilometers(237_948L, 1),
            SATURN,
            new ItemStack(Material.ICE),
            DayCycle.ETERNAL_NIGHT,
            Atmosphere.NONE,
            Gravity.relativeToEarth(0.0113)
    );


    public static void setup(Galactifun galactifun) {
        VENUS.addSpecies(BaseAlien.FIRESTORM, BaseAlien.SKYWHALE);
        MARS.addSpecies(BaseAlien.MARTIAN);
        THE_MOON.addSpecies(BaseAlien.MUTANT_CREEPER);
        TITAN.addSpecies(BaseAlien.LEECH, BaseAlien.TITAN, BaseAlien.TITAN_KING, BaseAlien.SKYWHALE);

        VENUS.register(galactifun);
        IO.register(galactifun);
        EUROPA.register(galactifun);
        EARTH.register(galactifun);
        EARTH_ORBIT.register(galactifun);
        ENCALADUS.register(galactifun);
        TITAN.register(galactifun);
        MARS.register(galactifun);
        THE_MOON.register(galactifun);

        Gas.setRecipes();
    }

}
