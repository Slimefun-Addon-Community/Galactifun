package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import org.bukkit.entity.Illusioner;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;

/**
 * Class for the Titan, an alien of Titan
 *
 * @author Seggan
 */
public final class TitanAlien extends Alien<Illusioner> {
    
    public TitanAlien() {
        super(Illusioner.class, "TITAN", "Titan", 32);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        // TODO add drops
        e.getDrops().clear();
    }

    @Override
    protected int getSpawnChance() {
        return 40;
    }

}
