package io.github.addoncommunity.galactifun.base.aliens;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.alien.PersistentDataHoldingAlien;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class Leech extends Alien implements PersistentDataHoldingAlien {

    private static final NamespacedKey KEY = new NamespacedKey(Galactifun.getInstance(), "eaten");

    private static final Map<UUID, List<ItemStack>> eaten = new HashMap<>();

    public Leech(@Nonnull AlienWorld... worlds) {
        super("LEECH", "&eLeech", EntityType.SILVERFISH, 10, worlds);
    }

    @Override
    public void onAttack(@Nonnull EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && ThreadLocalRandom.current().nextBoolean()) {
            Player p = (Player) e.getEntity();
            PlayerInventory inv = p.getInventory();

            List<Integer> availableSlots = new ArrayList<>();
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack itemStack = inv.getItem(i);
                if (itemStack != null && !itemStack.getType().isAir()) {
                    availableSlots.add(i);
                }
            }

            if (!availableSlots.isEmpty()) {
                LivingEntity damager = (LivingEntity) e.getDamager();
                int slot = ThreadLocalRandom.current().nextInt(availableSlots.size());
                eaten.computeIfAbsent(damager.getUniqueId(), u -> new ArrayList<>()).add(inv.getItem(slot));
                inv.setItem(slot, null);
                damager.setHealth(Math.min(
                    damager.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue(),
                    damager.getHealth() + 2)
                );
            }
        }
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        UUID uuid = e.getEntity().getUniqueId();
        List<ItemStack> eatenItems = eaten.get(uuid);
        if (eatenItems != null) {
            for (ItemStack itemStack : eaten.get(uuid)) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    e.getDrops().add(itemStack);
                }
            }
        }

        eaten.remove(uuid);
    }

    @Override
    public double getChance() {
        return 1;
    }

    @Override
    public void load(@Nonnull LivingEntity entity) {
        JsonArray array = PersistentDataAPI.getJsonArray(entity, KEY);
        if (array != null) {
            List<ItemStack> list = new ArrayList<>();
            for (JsonElement element : array) {
                FileConfiguration configuration = new YamlConfiguration();
                try {
                    configuration.loadFromString(element.getAsString());
                } catch (InvalidConfigurationException e) {
                    throw new IllegalStateException(e);
                }

                list.add(configuration.getItemStack("eaten"));
            }
            eaten.put(entity.getUniqueId(), list);
        }
    }

    @Override
    public void save(@Nonnull LivingEntity entity) {
        UUID uuid = entity.getUniqueId();
        if (eaten.containsKey(uuid)) {
            JsonArray array = new JsonArray();
            for (ItemStack stack : eaten.get(uuid)) {
                FileConfiguration configuration = new YamlConfiguration();
                configuration.set("eaten", stack);
                array.add(configuration.saveToString());
            }

            PersistentDataAPI.setJsonArray(entity, KEY, array);
        }
    }
}
