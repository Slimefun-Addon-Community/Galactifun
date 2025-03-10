package io.github.addoncommunity.galactifun.base.aliens;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;

/**
 * Class for the martian
 *
 * @author Seggan
 * @author Mooy
 */
public final class Martian extends Alien<ZombieVillager> {

    private final Map<String, ItemStack> trades = new HashMap<>();

    public Martian(String id, String name, double maxHealth, int spawnChance) {
        super(ZombieVillager.class, id, name, maxHealth, spawnChance);

        // Fixes the sword
        this.trades.put(Material.IRON_SWORD.name(), new ItemStack(Material.IRON_SWORD));
        this.trades.put(Material.IRON_ORE.name(), new ItemStack(Material.IRON_INGOT));
        this.trades.put(SlimefunItems.REINFORCED_PLATE.getItemId(), BaseMats.TUNGSTEN_INGOT.item());
    }

    @Override
    public void onSpawn(@Nonnull ZombieVillager spawned) {
        spawned.setCanPickupItems(false);

        // 1/64 chance
        if (ThreadLocalRandom.current().nextDouble() <= 0.015625) {
            spawned.setCustomName(ChatColor.RED + "The Zerix");
            spawned.setCustomNameVisible(true);
        }

        Objects.requireNonNull(spawned.getEquipment());

        spawned.getEquipment().setArmorContents(new ItemStack[] {
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        });
        spawned.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new ItemStack(Material.IRON_INGOT, 2));
        e.getDrops().add(new ItemStack(Material.RED_SAND));
    }

    @Override
    public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItem(e.getHand());
        if (item == null) {
            return;
        }

        SlimefunItem sfi = SlimefunItem.getByItem(item);
        ItemStack trade = this.trades.get(sfi == null ? item.getType().name() : sfi.getId());

        if (trade != null && item.getAmount() >= trade.getAmount()) {
            LivingEntity entity = (LivingEntity) e.getRightClicked();
            Objects.requireNonNull(entity.getEquipment()).setItemInOffHand(item);
            entity.addPotionEffect(new PotionEffect(
                    PotionEffectType.SLOW,
                    Integer.MAX_VALUE,
                    100,
                    false,
                    false
            ));

            if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                ItemUtils.consumeItem(item, trade.getAmount(), true);
            }

            Scheduler.run(60, () -> {
                if (entity.isValid()) {
                    entity.getWorld().dropItemNaturally(entity.getLocation(), trade.clone());

                    entity.getEquipment().setItemInOffHand(null);
                    entity.removePotionEffect(PotionEffectType.SLOW);
                }
            });
        }
    }

    @Override
    protected boolean canSpawnInLightLevel(int lightLevel) {
        return true;
    }

}
