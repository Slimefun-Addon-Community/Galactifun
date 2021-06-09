package io.github.addoncommunity.galactifun.core;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.items.StargateController;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public final class StargateListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onGateBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.END_GATEWAY &&
                Boolean.parseBoolean(BlockStorage.getLocationInfo(b.getLocation(), "locked"))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Deactivate the Stargate before destroying it");
        }
    }

    @EventHandler
    public void onUsePortal(PlayerTeleportEndGatewayEvent e) {
        if (!Boolean.parseBoolean(BlockStorage.getLocationInfo(e.getGateway().getLocation(), "portal"))) return;
        Location dest = e.getGateway().getExitLocation();
        if (dest == null) return;

        Block b = dest.getBlock();
        if (BlockStorage.check(b, BaseItems.STARGATE_CONTROLLER.getItemId()) &&
                StargateController.getPortalBlocks(b).isEmpty()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "The destination Stargate is not activated");
        }
    }
}
