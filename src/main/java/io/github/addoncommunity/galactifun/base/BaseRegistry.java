package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.aliens.MutantCreeper;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Venus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.TheMoon;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Enceladus;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Saturn;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn.Titan;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Registry of constants for the base celestial objects
 * 
 * @author Mooy1
 * @author Seggan
 */
@UtilityClass
public final class BaseRegistry {
    
    public static final CelestialWorld THE_MOON = new TheMoon();
    public static final CelestialBody EARTH = new Earth((TheMoon) THE_MOON);
    public static final CelestialWorld MARS = new Mars();
    public static final CelestialWorld VENUS = new Venus();
    public static final CelestialBody SATURN = new Saturn(new Titan(), new Enceladus());

    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", new ItemStack(Material.SUNFLOWER), VENUS, EARTH, MARS, SATURN);
    
    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", new ItemStack(Material.MILK_BUCKET), SOLAR_SYSTEM);

    public static final Martian MARTIAN = new Martian(MARS);
    public static final MutantCreeper ALIEN_CREEPER = new MutantCreeper(THE_MOON);

    public static void setup() {
        // just loading static fields for now
    }

}
