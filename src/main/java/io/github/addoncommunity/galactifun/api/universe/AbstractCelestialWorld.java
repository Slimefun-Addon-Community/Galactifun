package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import javax.annotation.Nonnull;

public abstract class AbstractCelestialWorld extends CelestialBody {

    @Getter @Nonnull
    protected final World world;

    public AbstractCelestialWorld(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity,
                                  @Nonnull Material material, @Nonnull DayCycle dayCycle, @Nonnull Terrain terrain, @Nonnull Atmosphere atmosphere) {
        super(name, distance, surfaceArea, gravity, material, dayCycle, terrain, atmosphere);
        this.world = createWorld();

        if (BlockStorage.getStorage(this.world) == null) {
            new BlockStorage(this.world);
        }
        
        GalacticRegistry.register(this.world, this);
    }
    
    @Nonnull
    protected abstract World createWorld();
    
    public abstract void tickWorld();

    /**
     * Apply effects each tick and upon joining the world
     */
    public abstract void applyEffects(@Nonnull Player p);

    public void onMobSpawn(@Nonnull CreatureSpawnEvent e) {
        // spawn native mobs here?
        e.setCancelled(true);
    }
    
}
