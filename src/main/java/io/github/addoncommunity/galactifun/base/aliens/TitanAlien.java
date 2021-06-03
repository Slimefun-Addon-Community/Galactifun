package io.github.addoncommunity.galactifun.base.aliens;

import javax.annotation.Nonnull;

import org.bukkit.entity.Illusioner;
import org.bukkit.event.entity.EntityDeathEvent;

import io.github.addoncommunity.galactifun.api.aliens.Alien;

/**
 * Class for the Titan, an alien of Titan
 *
 * @author Seggan
 */
public final class TitanAlien extends Alien<Illusioner> {
    
    public TitanAlien() {
        super(Illusioner.class, "TITAN", "Titan");
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        // TODO add drops
        e.getDrops().clear();
    }

    @Override
    protected int getMaxHealth() {
        return 32;
    }

    @Override
    protected int getSpawnChance() {
        return 40;
    }

}
