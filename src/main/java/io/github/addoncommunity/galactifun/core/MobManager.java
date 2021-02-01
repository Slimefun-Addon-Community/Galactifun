package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.mobs.Mob;
import io.github.addoncommunity.galactifun.core.util.Log;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The class that manages mobs
 *
 * @author WalshyDev
 */
public final class MobManager {
    private static final Map<String, Mob> registry = new HashMap<>();
    public static final MobManager INSTANCE = new MobManager();

    public static final NamespacedKey MOB_ID = new NamespacedKey(Galactifun.getInstance(), "mob_id");

    private MobManager() {
    }

    public void register(@Nonnull Mob mob) {
        registry.put(mob.getId().toUpperCase(), mob);
    }

    @Nullable
    public Mob getById(@Nullable String id) {
        return id == null ? null : registry.get(id);
    }

    @Nullable
    public Mob getByEntity(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, MOB_ID);
        return id != null ? registry.get(id) : null;
    }

    public void spawn(@Nonnull Mob mob, @Nonnull Location location) {
        Objects.requireNonNull(mob, "Mob cannot be null");
        Objects.requireNonNull(location, "Position cannot be null");

        if (!mob.getEntityType().isAlive()) {
            Log.error("Attempted to spawn '{}' however it isn't a living entity!", mob.getId());
        } else {
            LivingEntity e = (LivingEntity) location.getWorld().spawnEntity(location, mob.getEntityType());
            PersistentDataAPI.setString(e, MOB_ID, mob.getId());

            e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(mob.getHealth());
            e.setHealth(mob.getHealth());
            if (e.getEquipment() != null) {
                if (mob.getArmor() != null) {
                    e.getEquipment().setArmorContents(mob.getArmor());
                }
                if (mob.getMainHandItem() != null) {
                    e.getEquipment().setItemInMainHand(mob.getMainHandItem(), false);
                }
            }

            if (mob.getName() != null) {
                e.setCustomName(ChatColors.color(mob.getName()));
                e.setCustomNameVisible(true);
            }

            mob.onSpawn(e, new BlockPosition(location));
        }
    }

    public Map<String, Mob> getRegistry() {
        return registry;
    }

    public static int countInChunk(@Nonnull Chunk chunk, @Nonnull Mob mob) {
        Mob target = MobManager.INSTANCE.getById(mob.getId());

        int i = 0;
        for (Entity entity : chunk.getEntities()) {
            if (entity instanceof LivingEntity) {
                Mob m = MobManager.INSTANCE.getByEntity(entity);
                if (Objects.equals(m, target)) {
                    i++;
                }
            }
        }

        return i;
    }
}
