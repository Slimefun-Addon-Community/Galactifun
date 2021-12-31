package io.github.addoncommunity.galactifun.base.universe.earth;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.SimpleAlienWorld;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;

/**
 * The moon
 *
 * @author Seggan
 * @author Mooy1
 */
public final class TheMoon extends SimpleAlienWorld {

    public TheMoon(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                   DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Override
    protected void getPopulators(@Nonnull List<BlockPopulator> populators) {

    }

    @Nonnull
    @Override
    protected Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top) {
        if (random.nextFloat() > .02) {
            return Material.ANDESITE;
        } else {
            return Material.GOLD_ORE;
        }
    }

    @Nonnull
    @Override
    protected ObjectIntPair<Material> getTop() {
        return new ObjectIntImmutablePair<>(Material.LIGHT_GRAY_CONCRETE_POWDER, 2);
    }

    @Nonnull
    @Override
    protected Biome getBiome() {
        return Biome.BADLANDS;
    }

    @Override
    protected int getAverageHeight() {
        return 50;
    }

}
