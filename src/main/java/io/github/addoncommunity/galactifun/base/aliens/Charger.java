package io.github.addoncommunity.galactifun.base.aliens;

import javax.annotation.Nonnull;

import org.bukkit.entity.Goat;

import io.github.addoncommunity.galactifun.api.aliens.Alien;

public final class Charger extends Alien<Goat> {

    public Charger() {
        super(Goat.class, "CHARGER", "Charger", 30, 5);
    }

    @Override
    protected void onSpawn(@Nonnull Goat spawned) {
        spawned.setScreaming(true);
    }
}
