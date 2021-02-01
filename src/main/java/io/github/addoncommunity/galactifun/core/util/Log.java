package io.github.addoncommunity.galactifun.core.util;

import org.bukkit.Bukkit;

import javax.annotation.Nonnull;

public final class Log {
    private Log() {
    }

    public static void info(@Nonnull String message) {
        Bukkit.getLogger().info(message);
    }

    public static void info(@Nonnull String message, @Nonnull Object... values) {
        info(replaceVariablesInMessage(message, values));
    }

    public static void warn(@Nonnull String message) {
        Bukkit.getLogger().warning(message);
    }

    public static void warn(@Nonnull String message, @Nonnull Object... values) {
        warn(replaceVariablesInMessage(message, values));
    }

    public static void error(@Nonnull String message) {
        Bukkit.getLogger().severe(message);
    }

    public static void error(@Nonnull String message, @Nonnull Object... values) {
        error(replaceVariablesInMessage(message, values));
    }

    private static String replaceVariablesInMessage(@Nonnull String message, @Nonnull Object... values) {
        int idx = 0;

        int next;
        for(int off = 0; (next = message.indexOf("{}", off)) != -1 && idx != values.length; off = next + 1) {
            message = message.substring(0, next) + values[idx++].toString() + message.substring(next + 2);
        }

        return message;
    }
}

