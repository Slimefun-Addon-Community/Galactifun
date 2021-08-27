package io.github.addoncommunity.galactifun.api.events;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;

public class PlayerVisitWorldEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    @Getter
    @Nonnull
    private final PlanetaryWorld world;

    private boolean cancelled;

    public PlayerVisitWorldEvent(@Nonnull Player who, @Nonnull PlanetaryWorld world) {
        super(who);
        this.world = world;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
