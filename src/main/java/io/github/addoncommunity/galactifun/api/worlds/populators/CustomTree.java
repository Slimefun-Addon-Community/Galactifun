package io.github.addoncommunity.galactifun.api.worlds.populators;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;

import org.bukkit.Location;
import org.bukkit.Material;

import io.github.addoncommunity.galactifun.util.GenUtils;

/**
 * Class for a custom tree. Left subclassable
 */
@AllArgsConstructor
public class CustomTree { // TODO make this into a populator? or move class

    protected final Material log;
    protected final Material leaves;
    protected final int trunkHeight;

    /**
     * Generates this tree. Default implementation makes an oak tree top
     *
     * @param location the location of the bottom log. <b>This is modified</b>
     */
    public void generate(@Nonnull Location location) {
        for (int y = 0; y < this.trunkHeight; y++) {
            location.add(0, 1, 0);
            location.getBlock().setType(this.log, false);
        }

        GenUtils.generateOakTop(location, this.leaves);
    }

}
