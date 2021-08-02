package io.github.addoncommunity.galactifun.base.aliens;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.entity.Phantom;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.aliens.Alien;

/**
 * Class for the slywhale, a passive alien of Titan
 *
 * @author Seggan
 */
public final class Skywhale extends Alien<Phantom> {

    public Skywhale(@Nonnull String id, @Nonnull String name, double maxHealth, int spawnChance) {
        super(Phantom.class, id, name, maxHealth, spawnChance);
    }

    @Override
    public void onSpawn(@Nonnull Phantom spawned) {
        spawned.setSize(100);
    }

    @Override
    public void onTarget(@Nonnull EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, 20));
    }

    @Override
    public void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        e.getRightClicked().addPassenger(e.getPlayer());
    }

    @Override
    protected boolean canSpawnInLightLevel(int lightLevel) {
        return lightLevel > 7;
    }

    @Override
    protected double getSpawnHeightOffset() {
        return 100;
    }

}
