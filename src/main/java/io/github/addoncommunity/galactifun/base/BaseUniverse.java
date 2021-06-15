package io.github.addoncommunity.galactifun.base;

import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.TheUniverse;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.api.worlds.WorldManager;
import io.github.addoncommunity.galactifun.base.aliens.Leech;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.aliens.MutantCreeper;
import io.github.addoncommunity.galactifun.base.aliens.Skywhale;
import io.github.addoncommunity.galactifun.base.aliens.TitanAlien;
import io.github.addoncommunity.galactifun.base.aliens.TitanKing;
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

/**
 * Registry of objects, aliens, and worlds in the base universe
 * 
 * @author Mooy1
 * @author Seggan
 */
@Getter
public final class BaseUniverse {

    private final Galaxy milkyWay;

    private final StarSystem solarSystem;

    private final PlanetaryObject jupiter;
    private final PlanetaryObject saturn;

    private final PlanetaryWorld earth;

    private final AlienWorld venus;
    private final AlienWorld earthOrbit;
    private final AlienWorld theMoon;
    private final AlienWorld mars;
    private final AlienWorld io;
    private final AlienWorld europa;
    private final AlienWorld titan;
    private final AlienWorld enceladus;

    private final Alien<?> martian;
    private final Alien<?> mutantCreeper;
    private final Alien<?> titanAlien;
    private final Alien<?> leech;
    private final Alien<?> skywhale;
    private final Alien<?> titanKing;

    public BaseUniverse(Galactifun galactifun) {
        TheUniverse theUniverse = galactifun.getTheUniverse();
        WorldManager worldManager = galactifun.getWorldManager();
        AlienManager alienManager = galactifun.getAlienManager();

        this.milkyWay = new Galaxy("Milky Way", GalaxyType.SPIRAL,
                Orbit.lightYears(12_000_000_000D, 0), theUniverse, new ItemStack(Material.MILK_BUCKET));
        this.solarSystem = new StarSystem("Solar System", StarSystemType.NORMAL,
                Orbit.lightYears(27_000, 250_000_000D), this.milkyWay, new ItemStack(Material.SUNFLOWER));
        this.venus = new Venus(this.solarSystem).register(worldManager);
        this.earth = new Earth(this.solarSystem).register(worldManager);
        this.mutantCreeper = new MutantCreeper();
        this.theMoon = new TheMoon(this.earth).addSpecies(this.mutantCreeper).register(worldManager);
        this.earthOrbit = new EarthOrbit(this.earth);
        this.martian = new Martian();
        this.mars = new Mars(this.solarSystem).addSpecies(this.martian).register(worldManager);
        this.jupiter = new Jupiter(this.solarSystem);
        this.io = new Io(this.jupiter);
        this.europa = new Europa(this.jupiter);
        this.saturn = new Saturn(this.solarSystem);
        this.titanAlien = new TitanAlien();
        this.leech = new Leech();
        this.skywhale = new Skywhale();
        this.titanKing = new TitanKing();
        this.titan = new Titan(this.saturn)
                .addSpecies(this.titanAlien, this.leech, this.skywhale, this.titanKing).register(worldManager);
        this.enceladus = new Enceladus(galactifun, this.saturn).register(worldManager);
    }

}
