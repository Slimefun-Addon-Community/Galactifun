package io.github.addoncommunity.galactifun.api.aliens.goals;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Location;
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
public final class FleeGoal<T extends Mob> extends AbstractGoal<T> {

    private int ticks = 0;

    public FleeGoal(@Nonnull Class<T> entityClass, @Nonnull T mob, int fleeTicks) {
        super(entityClass, mob);

        Galactifun.inst().registerListener(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            public void onHit(EntityDamageEvent e) {
                if (e.getEntity().getUniqueId().equals(mob.getUniqueId())) {
                    FleeGoal.this.ticks = fleeTicks;
                }
            }
        });
    }

    public FleeGoal(@Nonnull Class<T> entityClass, @Nonnull T mob) {
        this(entityClass, mob, 400);
    }

    @Nonnull
    @Override
    public String getGoalKey() {
        return "flee";
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
