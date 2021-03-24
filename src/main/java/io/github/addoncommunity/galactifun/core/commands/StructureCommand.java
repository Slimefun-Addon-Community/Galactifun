package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.GalacticProfile;
import io.github.addoncommunity.galactifun.core.schematics.BlockVector3;
import io.github.addoncommunity.galactifun.core.schematics.GalactifunStructureFormat;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.command.AbstractCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StructureCommand extends AbstractCommand {

    private static final File FOLDER = new File(Galactifun.getInstance().getDataFolder(), "structures");

    public StructureCommand() {
        super("structure", "The command for structures", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length >= 2 && sender instanceof Player) {
            Player p = (Player) sender;
            GalacticProfile profile = GalacticProfile.get(p);
            Location l = p.getLocation();

            switch (args[1]) {
                case "pos1":
                    profile.setPos1(l);
                    p.sendMessage("Set pos1 to " + Util.locToString(l));
                    break;
                case "pos2":
                    profile.setPos2(l);
                    p.sendMessage("Set pos2 to " + Util.locToString(l));
                    break;
                case "save":
                    Location pos1 = profile.getPos1();
                    if (pos1 == null) {
                        p.sendMessage("pos1 not set!");
                        break;
                    }
                    Location pos2 = profile.getPos2();
                    if (pos2 == null) {
                        p.sendMessage("pos2 not set!");
                        break;
                    }

                    GalactifunStructureFormat format = new GalactifunStructureFormat(
                        p.getWorld(),
                        BlockVector3.fromLocation(pos1),
                        BlockVector3.fromLocation(pos2)
                    );

                    format.save(new File(FOLDER, args[2] + ".gsf"));
                    p.sendMessage("Saved " + args[2]);
                    break;
                case "load":
                    GalactifunStructureFormat loaded;
                    try {
                        loaded = GalactifunStructureFormat.load(new File(FOLDER, args[2] + ".gsf"));
                    } catch (FileNotFoundException e) {
                        p.sendMessage(ChatColor.RED + "Unknown structure!");
                        break;
                    }

                    loaded.paste(l);
                    break;
            }
        }
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        List<String> options = new ArrayList<>();
        if (args.length == 2) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "load"));
        }

        return options;
    }
}
