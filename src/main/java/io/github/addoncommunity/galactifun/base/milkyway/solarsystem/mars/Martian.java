package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.mars;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.core.Util;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the martian, a mob spawning naturally on mars
 *
 * @author Seggan
 */
class Martian extends Alien implements Listener {

    private final Map<Material, ItemStack> trades = new HashMap<>();
    private static final NamespacedKey TARGET_KEY = new NamespacedKey(Galactifun.getInstance(), "martian_target");

    private static final Map<UUID, UUID> targets = new HashMap<>();

    protected Martian() {
        super("MARTIAN", "&4Martian", EntityType.ZOMBIE_VILLAGER, 32);

        Bukkit.getPluginManager().registerEvents(this, Galactifun.getInstance());

        setupTrades();
    }

    private void setupTrades() {
        // Fixes the sword
        this.trades.put(Material.IRON_SWORD, new ItemStack(Material.IRON_SWORD));

        this.trades.put(Material.IRON_ORE, new ItemStack(Material.IRON_INGOT));
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned, @Nonnull Location loc) {
        spawned.setCanPickupItems(false);
        spawned.setRemoveWhenFarAway(true);

        // 1/64 chance
        if (ThreadLocalRandom.current().nextDouble() <= 0.015625) {
            spawned.setCustomName(ChatColor.RED + "The Zerix");
            spawned.setCustomNameVisible(true);
        }

        spawned.getEquipment().setArmorContents(new ItemStack[]{new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET)});
        spawned.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
    }

    @Override
    public boolean canSpawn(@Nonnull Chunk chunk) {
        return chunk.getWorld().getName().equals("mars") && Util.countInChunk(chunk, this) < getMaxAmountInChunk(chunk);
    }

    @Override
    public double getChanceToSpawn(@Nonnull Chunk chunk) {
        return 50;
    }

    @Override
    public int getMaxAmountInChunk(@Nonnull Chunk chunk) {
        return 2;
    }

    @Override
    protected void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new ItemStack(Material.IRON_INGOT, 2));
        e.getDrops().add(new ItemStack(Material.RED_SAND));
    }

    @Override
    public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        LivingEntity entity = (LivingEntity) e.getRightClicked();
        PlayerInventory inv = e.getPlayer().getInventory();
        ItemStack item = inv.getItem(e.getHand());

        ItemStack trade = this.trades.get(item.getType());

        if (trade != null) {
            entity.getEquipment().setItemInOffHand(item);
            entity.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW,
                Integer.MAX_VALUE,
                100,
                false,
                false
            ));

            Bukkit.getScheduler().runTaskLater(Galactifun.getInstance(), () -> {
                entity.getWorld().dropItemNaturally(entity.getLocation(), trade.clone());

                if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    ItemUtils.consumeItem(item, true);
                }

                entity.getEquipment().setItemInOffHand(null);
                entity.removePotionEffect(PotionEffectType.SLOW);
            }, 60);
        }
    }

}
