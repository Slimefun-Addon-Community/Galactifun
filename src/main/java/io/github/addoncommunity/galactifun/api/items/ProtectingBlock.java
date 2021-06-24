package io.github.addoncommunity.galactifun.api.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.Validate;

public abstract class ProtectingBlock extends AbstractContainer {

    protected static final String PROTECTION_AMOUNT = "protection_amount";
    protected static final String PROTECTION_LEVEL = "protection_level";
    protected static final String ENABLED = "enabled";

    private static final Map<Location, Map<AtmosphericEffect, Integer>> protectedBlocks = new HashMap<>();
    private static final Set<Location> allBlocks = new HashSet<>();
    private static int counter = 0;

    private static final ItemStack ENABLED_ITEM = new CustomItem(
            Material.STRUCTURE_VOID,
            "&aEnabled",
            "",
            "&7Click to disable"
    );
    private static final ItemStack DISABLED_ITEM = new CustomItem(
            Material.BARRIER,
            "&cDisabled",
            "",
            "&7Click to enable"
    );

    @Getter
    private final AtmosphericEffect effect;
    /**
     * <b>Must</b> be even and be <= 8
     */
    private final int upgradeSlots;

    @ParametersAreNonnullByDefault
    public ProtectingBlock(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, AtmosphericEffect effect, int upgradeSlots) {
        super(CoreCategory.MACHINES, item, recipeType, recipe);
        this.effect = effect;
        this.upgradeSlots = upgradeSlots;

        Validate.isTrue(this.upgradeSlots %2 == 0 && this.upgradeSlots <= 8,
                "upgradeSlots must be even and be smaller than or equal to 8");

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                allBlocks.add(b.getLocation());
                ProtectingBlock.this.tick(b);
            }

            @Override
            public void uniqueTick() {
                // to prevent memory leaks if something happens (block breaks aren't the only thing that can)
                allBlocks.removeIf(l -> !(BlockStorage.check(l) instanceof ProtectingBlock));

                // every 6 slimefun ticks (every 3 seconds)
                if (counter < 6) {
                    counter++;
                } else {
                    counter = 0;

                    protectedBlocks.clear();
                    for (Location l : allBlocks) {
                        if (!Boolean.parseBoolean(BlockStorage.getLocationInfo(l, ENABLED))) continue;

                        // removed all non-instances before, so safe cast
                        ProtectingBlock inst = Objects.requireNonNull((ProtectingBlock) BlockStorage.check(l));

                        // check if sealed using flood fill
                        Optional<Set<Location>> returned = Util.floodFill(l, getStoredInteger(l, PROTECTION_AMOUNT));
                        // not sealed; continue on to the next block
                        if (returned.isEmpty()) continue;

                        int level = getStoredInteger(l, PROTECTION_LEVEL);
                        for (Location b : returned.get()) {
                            // add a protection to the location
                            protectedBlocks.computeIfAbsent(b, k -> new HashMap<>()).put(inst.effect, level);
                        }
                    }
                }
            }
        });
    }

    private static int getStoredInteger(@Nonnull Location l, @Nonnull String key) {
        String s = BlockStorage.getLocationInfo(l, key);
        if (s == null || s.isEmpty() || s.isBlank()) return 0;

        return Integer.parseInt(s);
    }

    @Nonnull
    public static Map<AtmosphericEffect, Integer> getProtectionsFor(@Nonnull Location l) {
        return protectedBlocks.getOrDefault(l, new HashMap<>());
    }

    public static int getProtectionFor(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return getProtectionsFor(l).getOrDefault(effect, 0);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        BlockStorage.addBlockInfo(b, ENABLED, "true");
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        int onEachSide = (9 - this.upgradeSlots) / 2;
        for (int i = 0, j = 8; i < onEachSide; i++, j--) {
            preset.addItem(i, MenuPreset.BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
            preset.addItem(j, MenuPreset.BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (Boolean.parseBoolean(BlockStorage.getLocationInfo(b.getLocation(), ENABLED))) {
            menu.replaceExistingItem(4, ENABLED_ITEM);
            menu.addMenuClickHandler(4, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, ENABLED, Boolean.toString(false));
                this.onNewInstance(menu, b);
                return false;
            });
        } else {
            menu.replaceExistingItem(4, DISABLED_ITEM);
            menu.addMenuClickHandler(4, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, ENABLED, Boolean.toString(true));
                this.onNewInstance(menu, b);
                return false;
            });
        }
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        return new int[0];
    }

    protected void tick(@Nonnull Block b) {
    }
}
