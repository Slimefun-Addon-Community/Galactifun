package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.core.util.Sphere;
import io.github.mooy1.infinitylib.command.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class GenSphereCommand extends AbstractCommand {

    private static final Sphere SPHERE = new Sphere(Material.DIRT, Material.STONE);
    
    public GenSphereCommand() {
        super("sphere", "Generates a sphere in the direction you are facing with specified radius", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 2) {
            return;
        }
        
        int radius;
        
        try {
            radius = Integer.parseInt(strings[1]);
        } catch (NumberFormatException e) {
            return;
        }
        
        if (radius < 3 || radius > 125) {
            return;
        }
        
        Player p = (Player) commandSender; 

        Block target = p.getLocation().getBlock().getRelative(p.getFacing(), radius + 4);
        
        double time = System.nanoTime();
        
        SPHERE.generate(target, ThreadLocalRandom.current(), radius, 0);
        
        time = System.nanoTime() - time;
        
        p.sendMessage(ChatColor.GREEN + "Time: " + (time / 1000000D) + " ms");
        
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        List<String> tabs = new ArrayList<>();
        if (strings.length == 2) {
            tabs.add("16");
            tabs.add("64");
        }
        return tabs;
    }

}
