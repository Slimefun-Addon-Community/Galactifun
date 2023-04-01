package io.github.addoncommunity.galactifun;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

/**
 * This is required because Kotlin does not play well with inheriting 2 methods of the same name
 */
public abstract class AbstractAddon extends JavaPlugin implements SlimefunAddon {

}
