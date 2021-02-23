package io.github.addoncommunity.galactifun.core.listener;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Listeners for celestial world
 *
 * @author Mooy1
 * @author GallowsDove
 */
public final class CelestialListener implements Listener {

    private static final Set<Material> crops = EnumSet.of(
        Material.WHEAT,
        Material.BEETROOT,
        Material.BEETROOTS,
        Material.PUMPKIN,
        Material.PUMPKIN_STEM,
        Material.ATTACHED_PUMPKIN_STEM,
        Material.MELON,
        Material.MELON_STEM,
        Material.ATTACHED_MELON_STEM,
        Material.POTATO,
        Material.POTATOES,
        Material.CARROT,
        Material.CARROTS,
        Material.KELP,
        Material.KELP_PLANT,
        Material.SWEET_BERRIES,
        Material.SWEET_BERRY_BUSH,
        Material.SUGAR_CANE,
        Material.BAMBOO,
        Material.BAMBOO_SAPLING,
        Material.CACTUS,
        Material.COCOA
    );

    static {
        crops.addAll(Tag.SAPLINGS.getValues());
    }

    public CelestialListener() {
        PluginUtils.registerListener(this);
    }

    @EventHandler
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e) {
        AlienWorld object = AlienWorld.getByWorld(e.getFrom());
        if (object != null) {
            object.getGravity().removeGravity(e.getPlayer());
        }
        applyWorldEffects(e);
    }

    @EventHandler
    public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        applyWorldEffects(e);
    }

    private static void applyWorldEffects(@Nonnull PlayerEvent e) {
        AlienWorld object = AlienWorld.getByWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
        if (!e.getPlayer().hasPermission("galactifun.admin") && e.getTo() != null && e.getTo().getWorld() != null) {
            AlienWorld world = AlienWorld.getByWorld(e.getTo().getWorld());
            if (world != null) {
                e.setCancelled(true);
                // TODO we should add ways to 'fast travel' to worlds that are super expensive so that people can build bases there
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(@Nonnull CreatureSpawnEvent e) {
        // don't want to prevent mobs from spawning from spawners
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            AlienWorld world = AlienWorld.getByWorld(e.getEntity().getWorld());
            if (world != null) {
                e.setCancelled(!world.canSpawnVanillaMobs());
            }
        }
    }

    @EventHandler
    public void onWaypointCreate(@Nonnull WaypointCreateEvent e) {
        AlienWorld world = AlienWorld.getByWorld(e.getPlayer().getWorld());
        if (world != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCropGrow(@Nonnull BlockGrowEvent e) {
        Block block = e.getBlock();
        AlienWorld world = AlienWorld.getByWorld(block.getWorld());
        if (world != null && crops.contains(block.getType())) {
            BigDecimal relative = world.getAtmosphere().getCarbonDioxidePercentage()
                .divide(Atmosphere.EARTH_LIKE.getCarbonDioxidePercentage(), 50, RoundingMode.HALF_UP)
                .stripTrailingZeros();

            int times = relative.intValue();
            double chance = relative.remainder(BigDecimal.ONE).doubleValue();

            for (int i = 0; i < times + 1; i++) {
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    BlockData data = block.getBlockData();

                    if (data instanceof Ageable) {
                        Ageable ageable = (Ageable) data;
                        if (ageable.getAge() < ageable.getMaximumAge()) {
                            ageable.setAge(ageable.getAge() + 1);
                            block.setBlockData(ageable);
                        }
                    }
                }
            }
        }
    }

}
