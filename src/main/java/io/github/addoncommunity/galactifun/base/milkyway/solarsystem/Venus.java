package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.SimpleAlienWorld;
import io.github.addoncommunity.galactifun.api.universe.world.populators.LakePopulator;
import io.github.addoncommunity.galactifun.api.universe.world.populators.VolcanoPopulator;
import io.github.addoncommunity.galactifun.util.ItemChoice;

/**
 * Class for Venus
 *
 * @author Seggan
 */
public final class Venus extends SimpleAlienWorld {

    public Venus() {
        super("Venus", Orbit.kilometers(108_860_000L), CelestialType.TERRESTRIAL, new ItemChoice(Material.BLACK_TERRACOTTA));
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new VolcanoPopulator(117, Material.OBSIDIAN, Material.LAVA));
        populators.add(new LakePopulator(80, Material.LAVA));
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.days(117);
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder()
                .setNether().addStorm().addThunder()
                .addEffects(AtmosphericEffect.RADIOACTIVE)
                .add(Gas.CARBON_DIOXIDE, 96.5).add(Gas.NITROGEN, 3.5).build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.metersPerSec(8.87);
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
