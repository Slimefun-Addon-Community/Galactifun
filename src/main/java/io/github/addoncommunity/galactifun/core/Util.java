package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import lombok.experimental.UtilityClass;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

@UtilityClass
public class Util {

    public int countInChunk(Chunk chunk, Alien alien) {
        Alien target = GalacticRegistry.getAlien(alien.getId());

        int i = 0;
        for (Entity entity : chunk.getEntities()) {
            if (entity instanceof LivingEntity) {
                Alien al = GalacticRegistry.getAlien(entity);
                if (Objects.equals(al, target)) {
                    i++;
                }
            }
        }

        return i;
    }
}
