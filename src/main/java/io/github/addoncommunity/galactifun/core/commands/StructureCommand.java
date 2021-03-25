package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.schematics.BlockVector3;
import io.github.addoncommunity.galactifun.core.schematics.GalactifunStructureFormat;
import io.github.addoncommunity.galactifun.util.PersistentLocation;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class StructureCommand extends AbstractCommand {

    private static final File FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");

    private static final NamespacedKey POS1 = new NamespacedKey(Galactifun.inst(), "pos1");
    private static final NamespacedKey POS2 = new NamespacedKey(Galactifun.inst(), "pos2");

    public StructureCommand() {
        super("structure", "The command for structures", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length >= 2 && sender instanceof Player) {
            Player p = (Player) sender;
            PersistentDataContainer container = p.getPersistentDataContainer();
            Location l = p.getLocation();

            switch (args[1]) {
                case "pos1":
                    container.set(POS1, PersistentLocation.TYPE, p.getLocation());
                    p.sendMessage("Set pos1 to " + Util.locToString(l));
                    break;
                case "pos2":
                    container.set(POS2, PersistentLocation.TYPE, p.getLocation());
                    p.sendMessage("Set pos2 to " + Util.locToString(l));
                    break;
                case "save":
                    Location pos1 = container.get(POS1, PersistentLocation.TYPE);
                    if (pos1 == null) {
                        p.sendMessage("pos1 not set!");
                        break;
                    }
                    Location pos2 = container.get(POS2, PersistentLocation.TYPE);
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

    @Override
    protected void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] args, @Nonnull List<String> options) {
        if (args.length == 2) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "load"));
        }
    }
}
