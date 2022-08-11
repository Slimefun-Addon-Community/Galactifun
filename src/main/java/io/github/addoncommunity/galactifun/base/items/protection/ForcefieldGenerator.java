package io.github.addoncommunity.galactifun.base.items.protection;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;

public final class ForcefieldGenerator extends SlimefunItem implements EnergyNetComponent {

    private static final String ACTIVE = "active";
    private static final int ENERGY_CONSUMPTION = 64;

    public ForcefieldGenerator(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                ForcefieldGenerator.this.tick(b, data);
            }
        });

        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                activate(e.getBlock());
            }
        });

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            @ParametersAreNonnullByDefault
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                deactivate(e.getBlock());
            }
        });

        addItemHandler((BlockUseHandler) e -> {
            e.cancel();
            Block b = e.getClickedBlock().orElseThrow();
            if (b.getType() != Material.DISPENSER) return;
            Player p = e.getPlayer();
            if (BSUtils.getStoredBoolean(b, ACTIVE)) {
                deactivate(b);
                p.sendMessage("Deactivated");
            } else {
                activate(b);
                p.sendMessage("Activated");
            }
        });
    }

    private void tick(Block b, Config data) {
        boolean active = Boolean.parseBoolean(data.getString(ACTIVE));
        if (active && getCharge(b.getLocation()) < ENERGY_CONSUMPTION) {
            deactivate(b);
        } else {
            removeCharge(b.getLocation(), ENERGY_CONSUMPTION);
        }
    }

    private void activate(Block b) {
        if (BSUtils.getStoredBoolean(b, ACTIVE)) return;

        BlockFace direction = ((Directional) b.getBlockData()).getFacing();
        Block next = b.getRelative(direction);
        int steps = 0;
        while (!next.getType().isSolid() && steps < 64) {
            next.setType(Material.LIGHT);
            Light light = (Light) next.getBlockData();
            light.setLevel(6);
            next.setBlockData(light);

            next = next.getRelative(direction);
            steps++;
        }
        BSUtils.addBlockInfo(b, ACTIVE, true);
    }

    private void deactivate(Block b) {
        if (!BSUtils.getStoredBoolean(b, ACTIVE)) return;

        BlockFace direction = ((Directional) b.getBlockData()).getFacing();
        Block next = b.getRelative(direction);
        int steps = 0;
        while (next.getType() == Material.LIGHT && steps < 64) {
            next.setType(Material.AIR);

            next = next.getRelative(direction);
            steps++;
        }
        BSUtils.addBlockInfo(b, ACTIVE, false);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 256;
    }

}
