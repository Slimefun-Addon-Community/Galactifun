package io.github.addoncommunity.galactifun.implementation.rockets;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.UniversalObject;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.Heads;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public enum Rocket {
    ONE(1, 10, 9, new ItemStack[9]),
    TWO(2, 100, 18, new ItemStack[9]),
    ;

    private final int tier;
    private final int fuelCapacity;
    private final int storageCapacity;
    @Nonnull
    private final ItemStack[] recipe;
    @Nonnull
    private final SlimefunItemStack item;

    Rocket(int tier, int fuelCapacity, int storageCapacity, @Nonnull ItemStack... recipe) {
        this.tier = tier;
        this.fuelCapacity = fuelCapacity;
        this.storageCapacity = storageCapacity;
        this.recipe = recipe;
        this.item = new SlimefunItemStack(
            "ROCKET_TIER_" + this.name(),
            Heads.ROCKET.getTexture(),
            "&4Rocket Tier " + tier,
            "",
            "&7Fuel Capacity: " + fuelCapacity,
            "&7Cargo Capacity: " + storageCapacity
        );
    }

    public static void setup(Galactifun addon) {
        for (Rocket rocket : Rocket.values()) {
            new RocketItem(Categories.MAIN_CATEGORY, rocket.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, rocket.getRecipe()).register(addon);
        }
    }

    @Nullable
    public static Rocket getById(String id) {
        for (Rocket rocket : Rocket.values()) {
            if (rocket.getItem().getItemId().equals(id)) {
                return rocket;
            }
        }

        return null;
    }

    private static class RocketItem extends SlimefunItem {

        public RocketItem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
            super(category, item, recipeType, recipe);

            addItemHandler((BlockUseHandler) e -> e.getClickedBlock().ifPresent(block -> openGUI(e.getPlayer(), block)));

            addItemHandler(new BlockPlaceHandler(true) {
                @Override
                public void onPlayerPlace(BlockPlaceEvent e) {
                    Block b = e.getBlock();
                    BlockData data = b.getBlockData();
                    if (data instanceof Rotatable) {
                        ((Rotatable) data).setRotation(BlockFace.NORTH);
                    }
                    b.setBlockData(data, true);
                }
            });
        }

        private void openGUI(@Nonnull Player p, @Nonnull Block b) {
            if (!BlockStorage.check(b, this.getId())) return;

            String s = BlockStorage.getLocationInfo(b.getLocation(), "isLaunching");
            if (Boolean.parseBoolean(s)) {
                p.sendMessage(ChatColor.RED + "The rocket is already launching!");
                return;
            }

            CelestialWorld world = CelestialWorld.getByWorld(p.getWorld());
            if (world == null) return;

            s = BlockStorage.getLocationInfo(b.getLocation(), "fuel");
            if (s == null) return;
            int fuel = Integer.parseInt(s);
            if (fuel == 0) {
                p.sendMessage(ChatColor.RED + "The rocket has no fuel!");
                return;
            }

            s = BlockStorage.getLocationInfo(b.getLocation(), "fuelEff");
            if (s == null) return;
            double eff = Double.parseDouble(s);
            if (eff == 0) {
                throw new IllegalStateException("Fuel not zero but efficiency zero!");
            }

            double trueEff = eff / fuel;
            long maxDistance = Math.round(2_000_000 * (fuel * trueEff));

            UniversalObject<?> object = world.getOrbiting();
            while (!(object instanceof StarSystem)) {
                object = object.getOrbiting();
            }
            StarSystem system = (StarSystem) object;

            List<CelestialWorld> reachable = new ArrayList<>();
            for (UniversalObject<?> body : system.getOrbiters()) {
                if (body instanceof CelestialWorld && body.getDistanceTo(world) * Util.KM_PER_LY <= maxDistance) {
                    reachable.add((CelestialWorld) body);
                }
            }

            if (reachable.isEmpty()) {
                p.sendMessage(ChatColor.RED + "No known destinations within range!");
                return;
            }

            ChestMenu menu = new ChestMenu("Choose a destination");
            menu.setEmptySlotsClickable(false);

            int i = 0;
            for (CelestialWorld celestialWorld : reachable) {
                double distance = celestialWorld.getDistanceTo(world);
                ItemStack item = celestialWorld.getItem().clone();
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    List<String> lore = meta.getLore();
                    if (lore != null) {
                        lore.remove(lore.size() - 1);

                        if (distance > 0) {
                            lore.add(ChatColors.color("&7Distance: " + (distance < 1
                                ? LorePreset.format(distance * Util.KM_PER_LY) + " Kilometers"
                                : distance + " Light Years")
                            ));
                        } else {
                            lore.add(ChatColors.color("&7You are here!"));
                        }

                        meta.setLore(lore);
                        item = item.clone();
                        item.setItemMeta(meta);
                    }
                }

                menu.addItem(i++, item, (p1, slot, it, action) -> {
                    p1.closeInventory();
                    launch(p1, b, celestialWorld, fuel);
                    return false;
                });
            }

            menu.open(p);
        }

        private void launch(@Nonnull Player p, @Nonnull Block b, CelestialWorld worldTo, int fuel) {

        }
    }
}
