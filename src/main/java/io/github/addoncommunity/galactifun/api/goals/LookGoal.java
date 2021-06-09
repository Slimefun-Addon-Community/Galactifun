package io.github.addoncommunity.galactifun.api.goals;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;

import com.destroystokyo.paper.entity.ai.GoalType;

public final class LookGoal<T extends Mob> extends AbstractGoal<T> {

    private int time = 0;

    public LookGoal(@Nonnull Class<T> entityClass, @Nonnull T mob) {
        super(entityClass, mob);
    }

    @Nonnull
    @Override
    public String getGoalKey() {
        return "look";
    }

    @Override
    public boolean shouldActivate() {
        return true;
    }

    @Override
    public void start() {
        time = 80;
    }

    @Override
    public void stop() {
        time = 0;
    }

    @Override
    public void tick() {
        if (time >= 80) {
            time = 0;
            List<Entity> nearby = mob.getNearbyEntities(20, 5, 10);
            if (nearby.isEmpty()) return;

            mob.lookAt(nearby.get(ThreadLocalRandom.current().nextInt(nearby.size())), 1, 90);
        } else {
            time--;
        }
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE, GoalType.LOOK, GoalType.JUMP);
    }
}
