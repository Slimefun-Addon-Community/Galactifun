package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class Skywhale extends Alien {

    public Skywhale(@Nonnull AlienWorld... worlds) {
        super("SKYWHALE", "&fSkywhale", EntityType.PHANTOM, 100, worlds);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        ((Phantom) spawned).setSize(70);
        PaperLib.teleportAsync(spawned, spawned.getLocation().add(0, 50, 0));
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
    public double getChance() {
        return 3;
    }

    @Override
    public boolean canSpawnInLightLevel(int lightLevel) {
        return lightLevel > 7;
    }
}
