package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;

public class TitanAlien extends Alien {

    public TitanAlien(@Nonnull CelestialWorld... worlds) {
        super("TITAN", "Titan", EntityType.ILLUSIONER, 1, 32, worlds);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        // TODO add drops
        e.getDrops().clear();
    }

    @Override
    protected int getMaxAmountInChunk(@Nonnull Chunk chunk) {
        return 1;
    }
}
