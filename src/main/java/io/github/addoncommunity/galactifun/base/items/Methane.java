package io.github.addoncommunity.galactifun.base.items;

import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.ImmutableSet;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.ExclusiveGEOResource;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

public final class Methane extends SlimefunItem implements ExclusiveGEOResource {

    private final NamespacedKey key = Galactifun.createKey("methane");

    public Methane(SlimefunItemStack item) {
        super(CoreItemGroup.ITEMS, item, RecipeType.GEO_MINER, new ItemStack[]{ BaseUniverse.TITAN.item() });
    }

    @Nonnull
    @Override
    public Set<PlanetaryWorld> getWorlds() {
        return ImmutableSet.of(BaseUniverse.TITAN);
    }

    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        return switch (biome) {
            case COLD_OCEAN, DEEP_LUKEWARM_OCEAN, DEEP_OCEAN, DEEP_WARM_OCEAN, LUKEWARM_OCEAN,
                    DEEP_COLD_OCEAN, FROZEN_OCEAN, DEEP_FROZEN_OCEAN, WARM_OCEAN, OCEAN,
                    BEACH, SNOWY_BEACH -> 17;
            default -> 0;
        };
    }

    @Override
    public int getMaxDeviation() {
        return 6;
    }

    @Nonnull
    @Override
    public String getName() {
        return getItemName();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return false;
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return key;
    }

}
