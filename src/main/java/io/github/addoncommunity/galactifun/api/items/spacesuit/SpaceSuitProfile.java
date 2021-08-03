package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.util.PersistentInventory;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.apache.commons.lang.Validate;

@Getter
@SuppressWarnings("ClassCanBeRecord")
public final class SpaceSuitProfile {

    private static final NamespacedKey KEY = Galactifun.inst().getKey("space_suit");

    private static final Map<UUID, SpaceSuitProfile> profiles = new HashMap<>();

    private final Inventory inventory;
    private final UUID player;

    private SpaceSuitProfile(Inventory inventory, UUID player) {
        Validate.notNull(inventory);
        Validate.notNull(player);
        this.inventory = inventory;
        this.player = player;
        profiles.put(this.player, this);
        Galactifun.inst().registerListener(new Listener() {
            @EventHandler(ignoreCancelled = true)
            private void onArmorMove(InventoryClickEvent e) {
                UUID uuid = e.getWhoClicked().getUniqueId();
                if (!uuid.equals(player)) return;

                ItemStack curr = e.getCursor();

                // load() optional will only be present if curr isnt null
                // noinspection ConstantConditions
                loadIfNotFound(uuid, curr).ifPresent(profile -> profile.save(curr, true));

                loadIfNotFound(uuid, e.getCurrentItem());
            }
        });
    }

    @Nonnull
    public static Optional<SpaceSuitProfile> getOrCreate(@NonNull Player p) {
        return loadIfNotFound(p.getUniqueId(), p.getInventory().getChestplate());
    }

    @Nonnull
    private static Optional<SpaceSuitProfile> loadIfNotFound(UUID uuid, ItemStack item) {
        if (profiles.containsKey(uuid)) {
            return Optional.of(profiles.get(uuid));
        } else {
            return load(item, uuid);
        }
    }

    private static Optional<SpaceSuitProfile> load(ItemStack stack, UUID uuid) {
        if (SlimefunItem.getByItem(stack) instanceof SpaceSuit suit && suit.isChestPlate()) {
            return Optional.of(new SpaceSuitProfile(
                    stack.getItemMeta().getPersistentDataContainer().getOrDefault(
                            KEY,
                            PersistentInventory.INSTANCE,
                            Bukkit.createInventory(Bukkit.getPlayer(uuid), suit.getInvSize())
                    ),
                    uuid
            ));
        }

        return Optional.empty();
    }

    private void save(ItemStack stack, boolean delete) {
        stack.getItemMeta().getPersistentDataContainer().set(KEY, PersistentInventory.INSTANCE, inventory);
        if (delete) profiles.remove(player);
    }

    public static void saveAll() {
        for (SpaceSuitProfile profile : profiles.values()) {
            ItemStack stack = Bukkit.getPlayer(profile.player).getInventory().getChestplate();
            if (SlimefunItem.getByItem(stack) instanceof SpaceSuit suit && suit.isChestPlate()) {
                profile.save(stack, false);
            }
        }
    }
}
