package io.github.addoncommunity.galactifun.api.aliens.behaviors;

import java.util.EnumSet;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

public abstract class AlienBehavior<T extends Mob> implements Goal<T> {

    private final Class<T> mobClass;
    private final GoalKey<T> key;
    protected final Mob mob;

    protected AlienBehavior(Class<T> mobClass, NamespacedKey key, Mob mob) {
        this.key = GoalKey.of(mobClass, key);
        this.mobClass = mobClass;
        this.mob = mob;
    }

    @Nonnull
    @Override
    public GoalKey<T> getKey() {
        return this.key;
    }

    @Nonnull
    @Override
    public abstract EnumSet<GoalType> getTypes();

}
