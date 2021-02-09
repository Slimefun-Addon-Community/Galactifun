package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;

public final class TitanAlien extends Alien {

    public TitanAlien(@Nonnull AlienWorld... worlds) {
        super("TITAN", "Titan", EntityType.ILLUSIONER, 16, 32, worlds);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        // TODO add drops
        e.getDrops().clear();
    }

}
