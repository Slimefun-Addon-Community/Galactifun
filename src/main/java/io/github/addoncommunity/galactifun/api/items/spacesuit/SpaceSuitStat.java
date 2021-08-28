package io.github.addoncommunity.galactifun.api.items.spacesuit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public final class SpaceSuitStat {

    public static final SpaceSuitStat HEAT_RESISTANCE = new SpaceSuitStat("&cHeat Resistance");
    public static final SpaceSuitStat COLD_RESISTANCE = new SpaceSuitStat("&bCold Resistance");
    public static final SpaceSuitStat RADIATION_RESISTANCE = new SpaceSuitStat("&4Radiation Resistance");

    private final String name;

}
