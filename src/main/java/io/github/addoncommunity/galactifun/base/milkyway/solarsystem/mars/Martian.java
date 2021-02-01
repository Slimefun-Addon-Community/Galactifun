package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.mars;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.mobs.Mob;
import io.github.addoncommunity.galactifun.core.MobManager;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Martian extends Mob implements Listener {

    private static final Map<ItemStack, ItemStack> TRADES = new HashMap<>();

    protected Martian() {
        super("MARTIAN", "Martian", EntityType.ENDERMAN, 32);

        Bukkit.getPluginManager().registerEvents(this, Galactifun.getInstance());

        this.setArmor(new ItemStack[] {new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)})
            .setMainHandItem(new ItemStack(Material.IRON_SWORD));
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity self, @Nonnull BlockPosition position) {
        self.setCanPickupItems(false);
    }

    @Override
    public boolean canSpawn(@Nonnull World world) {
        return world.getName().equals("mars");
    }

    @Override
    public double getChanceToSpawn(@Nonnull Chunk chunk) {
        return 0.5;
    }

    @Override
    public int getMaxAmountInChunk(@Nonnull Chunk chunk) {
        return 2;
    }

    @Override
    public void onMobTick(@Nonnull LivingEntity self) {
        super.onMobTick(self);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (Objects.equals(MobManager.INSTANCE.getByEntity(entity), this)) {
            PlayerInventory inv = e.getPlayer().getInventory();
            ItemStack item = inv.getItem(e.getHand());
            ItemStack trade = TRADES.get(inv.getItem(e.getHand()));
            if (trade != null) {
                entity.getWorld().dropItemNaturally(entity.getLocation(), trade);
                item.setAmount(item.getAmount() - 1);
                inv.setItem(e.getHand(), item);
            }
        }
    }

}
