package io.github.addoncommunity.galactifun.api.aliens.behaviours;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

public abstract class AlienBehaviour<T extends Mob> implements Goal<T> {

    private final List<T> mobs = new ArrayList<>();
    private final Class<T> mobClass;
    private final GoalKey<T> key;

    protected AlienBehaviour(Class<T> mobClass, NamespacedKey key) {
        this.key = GoalKey.of(mobClass, key);
    }

    public Collection<T> mobs() {
        return Collections.unmodifiableCollection(this.mobs);
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
