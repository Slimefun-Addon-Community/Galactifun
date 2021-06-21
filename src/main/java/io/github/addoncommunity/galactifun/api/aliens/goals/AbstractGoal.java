package io.github.addoncommunity.galactifun.api.aliens.goals;

import java.util.EnumSet;

import javax.annotation.Nonnull;

import org.bukkit.entity.Mob;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.github.addoncommunity.galactifun.Galactifun;

public abstract class AbstractGoal<T extends Mob> implements Goal<T> {

    @Nonnull
    private final Class<T> entityClass;

    @Nonnull
    protected final T mob;

    public AbstractGoal(@Nonnull Class<T> entityClass, @Nonnull T mob) {
        this.entityClass = entityClass;
        this.mob = mob;
    }

    @Nonnull
    public abstract String getGoalKey();

    @Nonnull
    @Override
    public GoalKey<T> getKey() {
        return GoalKey.of(this.entityClass, Galactifun.inst().getKey(getGoalKey()));
    }

    @Nonnull
    @Override
    public abstract EnumSet<GoalType> getTypes();
}
