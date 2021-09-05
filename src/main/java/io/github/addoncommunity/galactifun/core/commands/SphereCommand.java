package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.util.Sphere;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.commands.SubCommand;

public final class SphereCommand extends SubCommand {

    public SphereCommand() {
        super("sphere", "Generates a sphere in the direction you are facing with specified radius", true);
    }

    @Override
    public void execute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p)) {
            return;
        }

        if (strings.length < 2) {
            p.sendMessage(ChatColor.RED + "Usage: /galactifun sphere <radius> <material>...");
            return;
        }

        int radius;

        try {
            radius = Integer.parseInt(strings[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.RED + "Invalid radius!");
            return;
        }

        if (radius < Sphere.MIN_RADIUS || radius > Sphere.MAX_RADIUS) {
            p.sendMessage(ChatColor.RED + "Radius must be within [3 - 125]");
            return;
        }

        Block tar = p.getLocation().getBlock().getRelative(p.getFacing(), radius + 4);

        Material[] materials = new Material[strings.length - 1];

        for (int i = 0; i < materials.length; i++) {
            try {
                materials[i] = Material.valueOf(strings[i + 1]);
            } catch (IllegalArgumentException e) {
                p.sendMessage(ChatColor.RED + "'" + strings[i + 1] + "' is not a material!");
                return;
            }
        }

        Sphere sphere = new Sphere(materials);
        long nano = System.nanoTime();
        sphere.generate(tar, radius, 0);
        p.sendMessage(ChatColor.GREEN + "Generated in " + Util.timeSince(nano));
    }

    @Override
    public void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> tabs) {
        if (strings.length == 1) {
            tabs.add("16");
            tabs.add("64");
        } else {
            for (Material material : Material.values()) {
                tabs.add(material.name());
            }
        }
    }

}
