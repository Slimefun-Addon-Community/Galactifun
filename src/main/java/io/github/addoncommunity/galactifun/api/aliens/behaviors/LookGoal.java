package io.github.addoncommunity.galactifun.api.aliens.behaviors;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;

import com.destroystokyo.paper.entity.ai.GoalType;
import io.github.addoncommunity.galactifun.Galactifun;

public final class LookGoal<T extends Mob> extends AlienBehavior<T> {

    private int time = 0;

    public LookGoal(@Nonnull Class<T> entityClass, @Nonnull T mob) {
        super(entityClass, Galactifun.createKey("look"), mob);
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public void start() {
        this.time = 80;
    }

    @Override
    public void stop() {
        this.time = 0;
    }

    @Override
    public void tick() {
        if (this.time >= 80) {
            this.time = 0;
            List<Entity> nearby = this.mob.getNearbyEntities(20, 5, 10);
            if (nearby.isEmpty()) return;

            this.mob.lookAt(nearby.get(ThreadLocalRandom.current().nextInt(nearby.size())), 1, 90);
        } else {
            this.time--;
        }
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE, GoalType.LOOK, GoalType.JUMP);
    }

}
