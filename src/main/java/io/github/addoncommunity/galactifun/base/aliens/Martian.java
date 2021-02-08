package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the martian
 *
 * @author Seggan
 * @author Mooy
 */
public final class Martian extends Alien {

    private final Map<Material, ItemStack> trades = new EnumMap<>(Material.class);
    private final ItemStack[] armor = {
            new ItemStack(Material.IRON_BOOTS),
            new ItemStack(Material.IRON_LEGGINGS),
            new ItemStack(Material.IRON_CHESTPLATE),
            new ItemStack(Material.IRON_HELMET)
    };
    private final ItemStack sword = new ItemStack(Material.IRON_SWORD);
    
    public Martian(@Nonnull CelestialWorld... worlds) {
        super("MARTIAN", "&4Martian", EntityType.ZOMBIE_VILLAGER, 32, 50, worlds);

        setupTrades();
    }

    private void setupTrades() {
        // Fixes the sword
        this.trades.put(Material.IRON_SWORD, new ItemStack(Material.IRON_SWORD));
        this.trades.put(Material.IRON_ORE, new ItemStack(Material.IRON_INGOT));
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        spawned.setCanPickupItems(false);

        // 1/64 chance
        if (ThreadLocalRandom.current().nextDouble() <= 0.015625) {
            spawned.setCustomName(ChatColor.RED + "The Zerix");
            spawned.setCustomNameVisible(true);
        }
        
        Objects.requireNonNull(spawned.getEquipment());

        spawned.getEquipment().setArmorContents(this.armor);
        spawned.getEquipment().setItemInMainHand(this.sword);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new ItemStack(Material.IRON_INGOT, 2));
        e.getDrops().add(new ItemStack(Material.RED_SAND));
    }

    @Override
    public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        LivingEntity entity = (LivingEntity) e.getRightClicked();
        ItemStack item = e.getPlayer().getInventory().getItem(e.getHand());

        ItemStack trade = this.trades.get(item.getType());

        if (trade != null) {
            Objects.requireNonNull(entity.getEquipment()).setItemInOffHand(item);
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
                if (entity.isValid()) {
                    entity.getWorld().dropItemNaturally(entity.getLocation(), trade.clone());

                    entity.getEquipment().setItemInOffHand(null);
                    entity.removePotionEffect(PotionEffectType.SLOW);
                }
            }, 60);
        }
    }

}
