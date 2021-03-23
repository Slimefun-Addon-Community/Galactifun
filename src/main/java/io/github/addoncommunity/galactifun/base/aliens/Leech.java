package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.mooy1.infinitylib.items.PersistentStackArray;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the leech, an alien of Titan. They can steal your items when attacking
 *
 * @author Seggan
 * @author Mooy1
 */
public final class Leech extends Alien {

    private static final NamespacedKey EATEN = PluginUtils.getKey("eaten");
    
    public Leech() {
        super("LEECH", "&eLeech", EntityType.SILVERFISH, 10);
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) {
        
        // get random item
        PlayerInventory inv = ((Player) e.getEntity()).getInventory();
        List<Integer> slots = new ArrayList<>();
        
        for (int i = 0 ; i < 36 ; i++) {
            if (inv.getItem(i) != null) {
                slots.add(i);
            }
        }
        
        if (slots.isEmpty()) {
            return;
        }
        
        int slot = slots.get(ThreadLocalRandom.current().nextInt(slots.size()));
        
        ItemStack item = inv.getItem(slot);
        
        // eat it
        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        
        ItemStack[] eatenItems = container.get(EATEN, PersistentStackArray.instance());
        if (eatenItems != null) {
            // add on to the array
            ItemStack[] arr = new ItemStack[eatenItems.length + 1];
            System.arraycopy(eatenItems, 0, arr, 0, eatenItems.length);
            arr[eatenItems.length] = item;
            container.set(EATEN, PersistentStackArray.instance(), arr);
        } else {
            container.set(EATEN, PersistentStackArray.instance(), new ItemStack[] {item});
        }
        
        inv.setItem(slot, null);
        
        // heal
        LivingEntity attacker = (LivingEntity) e.getDamager();
        attacker.setHealth(Math.min(
                this.maxHealth,
                attacker.getHealth() + 2)
        );
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        ItemStack[] eatenItems = e.getEntity().getPersistentDataContainer().get(EATEN, PersistentStackArray.instance());
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
    
}
