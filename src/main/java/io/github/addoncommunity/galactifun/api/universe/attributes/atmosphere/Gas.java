package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;

public enum Gas {
    OXYGEN,
    NITROGEN,
    CARBON_DIOXIDE,
    WATER,
    HELIUM,
    ARGON,
    METHANE,
    HYDROCARBONS,
    HYDROGEN,
    SULFUR,
    AMMONIA,
    OTHER;


    @Nonnull
    @Override
    public String toString() {
        return ChatUtils.humanize(this.name());
    }

}
