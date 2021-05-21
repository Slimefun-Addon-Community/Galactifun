package io.github.addoncommunity.galactifun.base.aliens;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;

/**
 * Class for the martian
 *
 * @author Seggan
 * @author Mooy
 */
public final class Martian extends Alien {

    private final Map<ItemStack, ItemStack> trades = new HashMap<>();
    private final ItemStack[] armor = {
            new ItemStack(Material.IRON_BOOTS),
            new ItemStack(Material.IRON_LEGGINGS),
            new ItemStack(Material.IRON_CHESTPLATE),
            new ItemStack(Material.IRON_HELMET)
    };
    private final ItemStack sword = new ItemStack(Material.IRON_SWORD);
    
    public Martian() {
        super("MARTIAN", "&4Martian", EntityType.ZOMBIE_VILLAGER, 32);
        setupTrades();
    }

    private void setupTrades() {
        // Fixes the sword
        addTrade(Material.IRON_SWORD, new ItemStack(Material.IRON_SWORD));
        addTrade(Material.IRON_ORE, new ItemStack(Material.IRON_INGOT));
        this.trades.put(SlimefunItems.REINFORCED_PLATE, BaseMats.TUNGSTEN);
        // TODO add more trades
    }

    private void addTrade(Material material, ItemStack trade) {
        this.trades.put(new ItemStack(material), trade);
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
    protected int getSpawnChance() {
        return 50;
    }

    @Override
    public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        LivingEntity entity = (LivingEntity) e.getRightClicked();
        ItemStack item = e.getPlayer().getInventory().getItem(e.getHand());

        ItemStack trade = this.trades.get(item);

        if (trade != null && item.getAmount() >= trade.getAmount()) {
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

            Galactifun.inst().runSync(() -> {
                if (entity.isValid()) {
                    entity.getWorld().dropItemNaturally(entity.getLocation(), trade.clone());

                    entity.getEquipment().setItemInOffHand(null);
                    entity.removePotionEffect(PotionEffectType.SLOW);
                }
            }, 60);
        }
    }

}
