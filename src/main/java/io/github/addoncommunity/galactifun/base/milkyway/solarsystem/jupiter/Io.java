package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter;

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
import io.github.addoncommunity.galactifun.api.worlds.populators.LakePopulator;
import io.github.addoncommunity.galactifun.api.worlds.populators.VolcanoPopulator;

public final class Io extends SimpleAlienWorld {

    public Io(PlanetaryObject jupiter) {
        super("&6Io", PlanetaryType.TERRESTRIAL, Orbit.kilometers(421_800L, 2), jupiter,
                new ItemStack(Material.LAVA_BUCKET), DayCycle.hours(42), Atmosphere.NONE, Gravity.metersPerSec(1.796));
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new VolcanoPopulator(115, Material.OBSIDIAN, Material.LAVA));
        populators.add(new LakePopulator(75, Material.LAVA));
    }

    @Nonnull
    @Override
    protected Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top) {
        if (y > 75) {
            return Material.BLACKSTONE;
        } else {
            return Material.YELLOW_TERRACOTTA;
        }
    }

    @Nonnull
    @Override
    protected Biome getBiome() {
        return Biome.DESERT_HILLS;
    }

    @Override
    protected int getAverageHeight() {
        return 80;
    }

    @Override
    protected int getMaxDeviation() {
        return 45;
    }

    @Override
    protected double getScale() {
        return .02;
    }

    @Override
    protected double getFrequency() {
        return .3;
    }
    
}
