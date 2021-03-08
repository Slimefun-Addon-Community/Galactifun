package io.github.addoncommunity.galactifun.base.aliens;

import com.google.common.collect.HashMultimap;
import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.PersistentAlien;
import io.github.addoncommunity.galactifun.util.SerialUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the leech, an alien of Titan. They can steal you items when attacking
 *
 * @author Seggan
 */
public final class Leech extends Alien implements PersistentAlien {

    private final HashMultimap<UUID, ItemStack> eaten = HashMultimap.create();

    public Leech() {
        super("LEECH", "&eLeech", EntityType.SILVERFISH, 10);
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) {
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
                this.eaten.put(damager.getUniqueId(), inv.getItem(slot));
                inv.setItem(slot, null);
                damager.setHealth(Math.min(
                        Objects.requireNonNull(damager.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue(),
                        damager.getHealth() + 2)
                );
            }
        }
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        UUID uuid = e.getEntity().getUniqueId();
        Set<ItemStack> eatenItems = this.eaten.removeAll(uuid);
        if (eatenItems != null) {
            for (ItemStack itemStack : eatenItems) {
                e.getDrops().add(itemStack);
            }
        }
    }

    @Override
    protected int getSpawnChance() {
        return 1;
    }
    
    @Override
    public void load(@Nonnull LivingEntity entity, @Nonnull String data) {
        for (ItemStack item : SerialUtils.deserializeItemStacks(data)) {
            this.eaten.put(entity.getUniqueId(), item);
        }
    }

    @Override
    @Nullable
    public String save(@Nonnull LivingEntity entity) {
        Set<ItemStack> eaten = this.eaten.get(entity.getUniqueId());
        return SerialUtils.serializeItemStacks(eaten);
    }
    
}
