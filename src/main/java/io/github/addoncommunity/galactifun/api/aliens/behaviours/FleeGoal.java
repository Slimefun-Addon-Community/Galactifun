package io.github.addoncommunity.galactifun.api.aliens.behaviours;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.github.addoncommunity.galactifun.Galactifun;

/**
 * Flees if attacked in the last {@code fleeTicks} ticks
 */
public final class FleeGoal<T extends Mob> extends AlienBehaviour<T> implements Listener {

    private final int fleeTicks;
    private int ticks = 0;

    public FleeGoal(@Nonnull Class<T> entityClass, @Nonnull T mob, int fleeTicks) {
        super(entityClass, mob);
        this.fleeTicks = fleeTicks;

        // Todo dont register a new listener for every alien, move to alien manager
        Galactifun.instance().registerListener(this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHit(EntityDamageEvent e) {
        if (e.getEntity().getUniqueId().equals(this.mob.getUniqueId())) {
            this.ticks = this.fleeTicks;
        }
    }

    @Nonnull
    @Override
    public NamespacedKey getGoalKey() {
        return Galactifun.instance().getKey("flee");
    }

    @Override
    public boolean shouldActivate() {
        return this.ticks > 0;
    }

    @Override
    public boolean shouldStayActive() {
        return this.ticks > 0;
    }

    @Override
    public void stop() {
        this.mob.getPathfinder().stopPathfinding();
    }

    @Override
    public void tick() {
        this.ticks--;
        Pathfinder pathfinder = this.mob.getPathfinder();
        if (!pathfinder.hasPath()) {
            Location mobLoc = this.mob.getLocation();
            Location l = null;
            for (int tries = 0; tries < 100; tries++) {
                l = new Location(
                        this.mob.getWorld(),
                        mobLoc.getBlockX() + ThreadLocalRandom.current().nextInt(10) - 5,
                        mobLoc.getBlockY(),
                        mobLoc.getBlockZ() + ThreadLocalRandom.current().nextInt(10) - 5
                );
                if (pathfinder.findPath(l) == null) {
                    l.add(0, 1, 0);
                    if (pathfinder.findPath(l) != null) break;

                    l.subtract(0, 2, 0);
                    if (pathfinder.findPath(l) != null) break;
                }
            }

            if (pathfinder.findPath(l) != null) {
                pathfinder.moveTo(l, 3);
            }
        }
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE, GoalType.JUMP);
    }

}
