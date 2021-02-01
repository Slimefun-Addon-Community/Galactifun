package io.github.addoncommunity.galactifun.api.mobs;

import io.github.addoncommunity.galactifun.core.MobManager;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@Getter
public abstract class Mob {
    @Nonnull
    private final String id;
    @Nullable
    private final String name;
    @Nonnull
    private final EntityType type;
    private final double health;
    @Nullable
    private ItemStack mainHandItem = null;
    @Nullable
    private ItemStack[] armor = null;

    @ParametersAreNonnullByDefault
    protected Mob(String id, @Nullable String name, EntityType type, double health) {
        Objects.requireNonNull(id, "Id cannot be null!");
        Objects.requireNonNull(type, "Type cannot be null!");
        this.id = id;
        this.name = name == null ? null : ChatColors.color(name);
        this.type = type;
        this.health = health;
    }

    @Nonnull
    public final EntityType getEntityType() {
        return this.type;
    }

    public final Mob setMainHandItem(@Nonnull ItemStack item) {
        Objects.requireNonNull(item, "Item cannot be null!");
        this.mainHandItem = item;
        return this;
    }

    public final Mob setArmor(@Nonnull ItemStack[] armor) {
        Objects.requireNonNull(armor, "Armor cannot be null!");
        if (armor.length == 4) {
            this.armor = armor;
        } else {
            ItemStack[] tmp = new ItemStack[4];
            System.arraycopy(armor, 0, tmp, 0, Math.min(armor.length, 4));
            this.armor = tmp;
        }

        return this;
    }

    public void onSpawn(@Nonnull LivingEntity self, @Nonnull BlockPosition position) {
    }

    public void onUniqueTick() {
    }

    public void onMobTick(@Nonnull LivingEntity self) {
    }

    public void onHit(@Nonnull LivingEntity self, @Nonnull LivingEntity damager) {
    }

    public final void register() {
        MobManager.INSTANCE.register(this);
    }

    public int hashCode() {
        return Objects.hash(this.id, this.name, this.type);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof Mob && ((Mob) obj).getId().equals(this.id);
        }
    }

    public abstract boolean canSpawn(@Nonnull World world);

    public abstract double getChanceToSpawn(@Nonnull Chunk chunk);

    public abstract int getMaxAmountInChunk(@Nonnull Chunk chunk);
}

