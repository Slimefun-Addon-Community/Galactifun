package io.github.addoncommunity.galactifun.base.aliens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;

/**
 * Class for the leech, an alien of Titan. They can steal your items when attacking
 *
 * @author Seggan
 * @author Mooy1
 */
public final class Leech extends Alien {

    private static final NamespacedKey EATEN = Galactifun.inst().getKey("eaten");
    
    public Leech() {
        super("LEECH", "&eLeech", EntityType.SILVERFISH);
    }

    @Override
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) {
        
        // get random item
        PlayerInventory inv = ((Player) e.getEntity()).getInventory();
        List<Integer> slots = new ArrayList<>();
        
        ItemStack[] contents = inv.getContents();
        for (int i = 0 ; i < contents.length ; i++) {
            if (contents[i] != null) {
                slots.add(i);
            }
        }
        
        if (slots.isEmpty()) {
            return;
        }
        
        int slot = slots.get(ThreadLocalRandom.current().nextInt(slots.size()));
        
        ItemStack item = contents[slot];
        
        // eat it
        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        
        ItemStack[] eatenItems = container.get(EATEN, PersistenceUtils.STACK_ARRAY);
        if (eatenItems != null) {
            // add on to the array
            ItemStack[] arr = new ItemStack[eatenItems.length + 1];
            System.arraycopy(eatenItems, 0, arr, 0, eatenItems.length);
            arr[eatenItems.length] = item;
            container.set(EATEN, PersistenceUtils.STACK_ARRAY, arr);
        } else {
            container.set(EATEN, PersistenceUtils.STACK_ARRAY, new ItemStack[] {item});
        }
        
        inv.setItem(slot, null);
        
        // heal
        LivingEntity attacker = (LivingEntity) e.getDamager();
        attacker.setHealth(Math.min(getMaxHealth(), attacker.getHealth() + 2));
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        ItemStack[] eatenItems = e.getEntity().getPersistentDataContainer().get(EATEN, PersistenceUtils.STACK_ARRAY);
        if (eatenItems != null) {
            for (ItemStack itemStack : eatenItems) {
                e.getDrops().add(itemStack);
            }
        }
    }

    @Override
    protected int getMaxHealth() {
        return 10;
    }

    @Override
    protected int getSpawnChance() {
        return 1;
    }
    
}
