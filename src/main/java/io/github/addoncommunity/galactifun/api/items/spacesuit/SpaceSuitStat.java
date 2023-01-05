package io.github.addoncommunity.galactifun.api.items.spacesuit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public final class SpaceSuitStat {

    public static final SpaceSuitStat HEAT_RESISTANCE = new SpaceSuitStat("&c热能防护");
    public static final SpaceSuitStat COLD_RESISTANCE = new SpaceSuitStat("&b寒冷防护");
    public static final SpaceSuitStat RADIATION_RESISTANCE = new SpaceSuitStat("&4辐射防护");

    private final String name;

}
