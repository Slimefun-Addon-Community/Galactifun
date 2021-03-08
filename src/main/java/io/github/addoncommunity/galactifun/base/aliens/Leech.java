package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.PersistentItemStackArray;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for the leech, an alien of Titan. They can steal you items when attacking
 *
 * @author Seggan
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
        
        int slot = ThreadLocalRandom.current().nextInt(inv.getSize());
        
        ItemStack item = inv.getItem(slot);
        
        if (item == null) {
            return;
        }
        
        // eat it
        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        
        ItemStack[] eatenItems = container.get(EATEN, PersistentItemStackArray.ITEM_STACK_ARRAY);
        if (eatenItems != null) {
            // add on to the array
            ItemStack[] arr = new ItemStack[eatenItems.length + 1];
            System.arraycopy(eatenItems, 0, arr, 0, eatenItems.length);
            arr[eatenItems.length] = item;
            container.set(EATEN, PersistentItemStackArray.ITEM_STACK_ARRAY, arr);
        } else {
            container.set(EATEN, PersistentItemStackArray.ITEM_STACK_ARRAY, new ItemStack[] {item});
        }
        
        inv.setItem(slot, null);
        
        // heal
        LivingEntity attacker = (LivingEntity) e.getDamager();
        attacker.setHealth(Math.min(
                Objects.requireNonNull(attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue(),
                attacker.getHealth() + 2)
        );
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        ItemStack[] eatenItems = e.getEntity().getPersistentDataContainer().get(EATEN, PersistentItemStackArray.ITEM_STACK_ARRAY);
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
