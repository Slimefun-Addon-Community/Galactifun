package io.github.addoncommunity.galactifun.base.aliens;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;

public final class MoonCow extends Alien<Cow> {

    private static final NamespacedKey TIMER = Galactifun.createKey("timer");

    public MoonCow(@Nonnull String id, @Nonnull String name, double maxHealth, double spawnChance) {
        super(Cow.class, id, name, maxHealth, spawnChance);
    }

    @Override
    public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        long timer = PersistentDataAPI.getLong(entity, TIMER, 0);
        long currentTime = System.currentTimeMillis();
        if (currentTime - timer > 5 * 60 * 1000) {
            entity.getWorld().dropItemNaturally(entity.getLocation(), BaseMats.MOON_CHEESE.clone());
            PersistentDataAPI.setLong(entity, TIMER, currentTime);
        }
    }

}
