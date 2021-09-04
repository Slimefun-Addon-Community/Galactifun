package io.github.addoncommunity.galactifun.base.items;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
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
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

public final class AutomaticDoor extends AbstractContainer {

    private static final int[] BACKGROUND = new int[] { 0, 1, 2, 3, 5, 6, 7, 8 };
    private static final int INPUT_SLOT = 4;
    private static final String ACTIVE = "active";

    public AutomaticDoor(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreCategory.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                AutomaticDoor.this.tick(BlockStorage.getInventory(b), b);
            }
        });
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        BlockStorage.addBlockInfo(b, "player", e.getPlayer().getUniqueId().toString());
    }

    private void tick(@Nonnull BlockMenu menu, @Nonnull Block b) {
        Location l = b.getLocation();
        Collection<Player> players = b.getWorld().getNearbyEntitiesByType(
                Player.class,
                l,
                Galactifun.instance().getConfig().getInt("other.auto-door-range", 3)
        );

        if (BSUtils.getStoredBoolean(l, ACTIVE)) {
            if (!players.isEmpty()) {
                BlockFace direction = ((Directional) b.getBlockData()).getFacing();
                Block startBlock = l.clone().add(direction.getDirection()).getBlock();
                if (startBlock.isEmpty()) {
                    BlockStorage.addBlockInfo(l, ACTIVE, "false");
                    return;
                }

                ItemStack item = menu.getItemInSlot(INPUT_SLOT);
                Material mat = startBlock.getType();
                if (item == null || item.getType().isAir() || item.getType() == mat) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(BlockStorage.getLocationInfo(l, "player")));
                    if (!SlimefunPlugin.getProtectionManager().hasPermission(p, l, ProtectableAction.BREAK_BLOCK)) return;

                    int size = item == null || item.getType().isAir() ?
                            mat.getMaxStackSize() :
                            item.getMaxStackSize() - item.getAmount();
                    for (int i = 0; i < size; i++) {
                        if (startBlock.isEmpty() || startBlock.getType() != mat) break;

                        startBlock.setType(Material.AIR);
                        menu.pushItem(new ItemStack(mat), INPUT_SLOT);
                        startBlock = startBlock.getRelative(direction);
                    }

                    BlockStorage.addBlockInfo(l, ACTIVE, "false");
                }
            }
        } else {
             if (players.isEmpty()) {
                 ItemStack stack = menu.getItemInSlot(INPUT_SLOT);
                 if (stack != null && stack.getType().isBlock()) {
                     OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(BlockStorage.getLocationInfo(l, "player")));
                     if (!SlimefunPlugin.getProtectionManager().hasPermission(p, l, ProtectableAction.PLACE_BLOCK)) return;

                     Location start = l.clone();
                     Vector v = ((Directional) b.getBlockData()).getFacing().getDirection();
                     // gotta to do this bc im modifying the stack in the loop
                     int amount = stack.getAmount();
                     for (int i = 0; i < amount; i++) {
                         // add() modifies the current Location as well as returns it
                         Block next = start.add(v).getBlock();
                         if (!next.isEmpty()) break;

                         next.setType(stack.getType());
                         menu.consumeItem(INPUT_SLOT);
                     }

                     BlockStorage.addBlockInfo(l, ACTIVE, "true");
                 }
             }
        }
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

}
