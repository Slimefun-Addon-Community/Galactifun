package io.github.addoncommunity.galactifun.base.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public final class AutomaticDoor extends MenuBlock {

    private static final int[] BACKGROUND = new int[] { 0, 1, 2, 3, 5, 6, 7, 8 };
    private static final int INPUT_SLOT = 4;
    private static final String ACTIVE = "active";
    private static boolean canUseSlimefunItem = Galactifun.instance().getConfig().getBoolean("other.allow-auto-door-use-slimefun-block", true);
    private static List<Material> bannedTypes = new ArrayList<>();
    private static List<String> bannedSlimefunTypes = new ArrayList<>();

    public AutomaticDoor(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                AutomaticDoor.this.tick(BlockStorage.getInventory(b), b);
            }
        });
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        BlockStorage.addBlockInfo(b, "player", e.getPlayer().getUniqueId().toString());
    }

    private void tick(@Nonnull BlockMenu menu, @Nonnull Block b) {
        Location l = b.getLocation();
        Collection<Player> players = b.getWorld().getNearbyEntitiesByType(
                Player.class,
                l,
                Galactifun.instance().getConfig().getInt("other.auto-door-range", 2)
        );



        if (BSUtils.getStoredBoolean(l, ACTIVE)) {
            if (!players.isEmpty()) {
                BlockFace direction = ((Directional) b.getBlockData()).getFacing();
                Block startBlock = l.clone().add(direction.getDirection()).getBlock();
                if (startBlock.isEmpty()) {
                    BlockStorage.addBlockInfo(l, ACTIVE, "false");
                    return;
                }

                ItemStack item = menu.getItemInSlot(INPUT_SLOT);
                Material mat = startBlock.getType();
                String id = BlockStorage.checkID(startBlock);

                if (!canUseSlimefunItem && SlimefunItem.getByItem(item) != null){
                    return;
                }
                if (item != null && bannedTypes.contains(item.getType()) && !bannedSlimefunTypes.contains(Objects.requireNonNull(SlimefunItem.getByItem(item)).getId())) {
                    return;
                } else if (item == null || item.getType().isAir() || item.getType() == mat) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(BlockStorage.getLocationInfo(l, "player")));
                    if (!Slimefun.getProtectionManager().hasPermission(p, l, Interaction.BREAK_BLOCK)) return;

                    int size = item == null || item.getType().isAir() ?
                            mat.getMaxStackSize() :
                            item.getMaxStackSize() - item.getAmount();
                    ItemStack itemStack = id == null ? new ItemStack(mat) : SlimefunItem.getById(id).getItem();
                    for (int i = 0; i < size; i++) {
                        if (startBlock.isEmpty() || startBlock.getType() != mat ||
                                !Objects.equals(BlockStorage.checkID(startBlock), id)) break;

                        startBlock.setType(Material.AIR);
                        menu.pushItem(itemStack.clone(), INPUT_SLOT);
                        startBlock = startBlock.getRelative(direction);
                    }

                    BlockStorage.addBlockInfo(l, ACTIVE, "false");
                }
            }
        } else {
            if (players.isEmpty()) {
                ItemStack stack = menu.getItemInSlot(INPUT_SLOT);
                if (stack != null && stack.getType().isBlock() && !bannedTypes.contains(stack.getType())) {
                    if (SlimefunItem.getByItem(stack)!=null&&(!canUseSlimefunItem || bannedSlimefunTypes.contains(SlimefunItem.getByItem(stack).getId()))){
                        return;
                    }
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(BlockStorage.getLocationInfo(l, "player")));
                    if (!Slimefun.getProtectionManager().hasPermission(p, l, Interaction.PLACE_BLOCK)) return;

                    Location start = l.clone();
                    Vector v = ((Directional) b.getBlockData()).getFacing().getDirection();
                    // gotta to do this bc im modifying the stack in the loop
                    int amount = stack.getAmount();
                    SlimefunItem item = SlimefunItem.getByItem(stack);
                    boolean doorIsClose = false;
                    for (int i = 0; i < amount; i++) {
                        // add() modifies the current Location as well as returns it
                        Block next = start.add(v).getBlock();
                        if (!next.isEmpty()) break;
                        if (!canUseSlimefunItem && BlockStorage.checkID(next) != null)
                        if (bannedTypes.contains(next.getType())) break;
                        if (bannedSlimefunTypes.contains(BlockStorage.checkID(next))) break;
                        doorIsClose = true;

                        next.setType(stack.getType());
                        if (item != null) {
                            BlockStorage.addBlockInfo(next, "id", item.getId());
                        }
                        menu.consumeItem(INPUT_SLOT);
                    }
                    if (doorIsClose) {
                        BlockStorage.addBlockInfo(l, ACTIVE, "true");
                    }
                }
            }
        }
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
    }

    @Override
    protected int[] getInputSlots() {
        return new int[] { INPUT_SLOT };
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[] { INPUT_SLOT };
    }

    @Override
    public void preRegister() {
        LoadFormConfig();
    }

    public void LoadFormConfig(){
        for (String type : Galactifun.instance().getConfig().getStringList("other.auto-door-banned-types")){
            //slimefun items support
            if (type.startsWith("Slimefun:")){
                bannedSlimefunTypes.add(type.replace("Slimefun:", ""));
            } else {
                bannedTypes.add(Material.valueOf(type));
            }
        }
    }

}
