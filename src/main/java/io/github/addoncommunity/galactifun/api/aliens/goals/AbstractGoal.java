package io.github.addoncommunity.galactifun.api.aliens.goals;

import java.util.EnumSet;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

public abstract class AbstractGoal<T extends Mob> implements Goal<T> {

    private final Class<T> entityClass;
    protected final T mob;

    public AbstractGoal(@Nonnull Class<T> entityClass, @Nonnull T mob) {
        this.entityClass = entityClass;
        this.mob = mob;
    }

    @Nonnull
    public abstract NamespacedKey getGoalKey();

    @Nonnull
    @Override
    public GoalKey<T> getKey() {
        return GoalKey.of(this.entityClass, getGoalKey());
    }

    @Nonnull
    @Override
    public abstract EnumSet<GoalType> getTypes();

}
