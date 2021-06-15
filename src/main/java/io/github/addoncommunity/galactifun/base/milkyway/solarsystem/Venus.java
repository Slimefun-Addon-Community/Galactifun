package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.SimpleAlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.populators.LakePopulator;
import io.github.addoncommunity.galactifun.api.worlds.populators.VolcanoPopulator;

/**
 * Class for Venus
 *
 * @author Seggan
 */
public final class Venus extends SimpleAlienWorld {

    public Venus(StarSystem starSystem) {
        super("Venus", PlanetaryType.TERRESTRIAL, Orbit.kilometers(108_860_000L, 225),
                starSystem, new ItemStack(Material.BLACK_TERRACOTTA), DayCycle.days(117),
                new AtmosphereBuilder()
                        .setNether().addStorm().addThunder()
                        .addEffects(AtmosphericEffect.RADIATION)
                        .add(Gas.CARBON_DIOXIDE, 96.5).add(Gas.NITROGEN, 3.5).build(),
                Gravity.metersPerSec(8.87));
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new VolcanoPopulator(117, Material.OBSIDIAN, Material.LAVA));
        populators.add(new LakePopulator(80, Material.LAVA));
    }

    @Nonnull
    @Override
    protected Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top) {
        if (y > 75) {
            return Material.BLACKSTONE;
        } else if (y > 10) {
            return Material.BASALT;
        } else if (y > 8) {
            return Material.YELLOW_TERRACOTTA;
        } else {
            return Material.BASALT;
        }
    }

    @Nonnull
    @Override
    protected Biome getBiome() {
        return Biome.BASALT_DELTAS;
    }

    @Override
    protected int getAverageHeight() {
        return 100;
    }

    @Override
    protected int getMaxDeviation() {
        return 50;
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
