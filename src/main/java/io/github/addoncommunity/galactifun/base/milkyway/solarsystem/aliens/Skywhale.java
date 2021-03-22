package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.aliens;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

/**
 * Class for the slywhale, a passive alien of Titan
 *
 * @author Seggan
 */
public final class Skywhale extends Alien {

    public Skywhale() {
        super("SKYWHALE", "&fSkywhale", EntityType.PHANTOM, 100);
    }

    @Override
    protected void onSpawn(@Nonnull LivingEntity spawned) {
        ((Phantom) spawned).setSize(100);
    }

    @Override
    protected void onTarget(@Nonnull EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @Override
    protected void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE, 20));
    }

    @Override
    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) {
        e.getRightClicked().addPassenger(e.getPlayer());
    }

    @Override
    protected int getSpawnChance() {
        return 3;
    }

    @Override
    protected boolean getSpawnInLightLevel(int lightLevel) {
        return lightLevel > 7;
    }

    @Override
    protected Vector getSpawnOffset() {
        return new Vector(0, 100, 0);
    }
}
