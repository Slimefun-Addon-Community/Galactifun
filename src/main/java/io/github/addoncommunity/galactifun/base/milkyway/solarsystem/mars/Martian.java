package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.mars;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.core.Util;
import io.github.mooy1.infinitylib.PluginUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the martian, a mob spawning naturally on mars
 *
 * @author Seggan
 */
class Martian extends Alien {

    private final Map<Material, ItemStack> trades = new HashMap<>();

    protected Martian() {
        super("MARTIAN", "&4Martian", EntityType.ZOMBIE_VILLAGER, 32);

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

            if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                ItemUtils.consumeItem(item, true);
            }

            PluginUtils.runSync(() -> {
                if (!entity.isDead()) {
                    entity.getWorld().dropItemNaturally(entity.getLocation(), trade.clone());

                    entity.getEquipment().setItemInOffHand(null);
                    entity.removePotionEffect(PotionEffectType.SLOW);
                }
            }, 60);
        }
    }

}
