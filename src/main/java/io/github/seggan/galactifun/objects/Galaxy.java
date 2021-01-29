package io.github.seggan.galactifun.objects;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Galaxy {

    public static final Galaxy MILKY_WAY = new Galaxy(StarSystem.SOLAR_SYSTEM);

    private final List<StarSystem> systems = new ArrayList<>();

    public Galaxy(@Nonnull StarSystem... systems) {
        this.systems.addAll(Arrays.asList(systems));
    }
    
    public void addStarSystem(@Nonnull StarSystem... objects) {
        this.systems.addAll(Arrays.asList(objects));
    }
    
}
