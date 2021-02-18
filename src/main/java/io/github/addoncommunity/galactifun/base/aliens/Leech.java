package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;

public class Leech extends Alien {

    public Leech(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int chance, int health, @Nonnull AlienWorld... worlds) {
        super("LEECH", "&eLeech", EntityType.SILVERFISH, health, worlds);
    }

    @Override
    public double getChance() {
        return 0;
    }

}
