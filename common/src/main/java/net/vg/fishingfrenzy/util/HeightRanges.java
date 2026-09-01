package net.vg.fishingfrenzy.util;

import net.minecraft.advancements.criterion.MinMaxBounds;

public enum HeightRanges {
    SKY_LEVEL(MinMaxBounds.Doubles.atLeast(150.0)),
    HIGH_PLATEAUS(MinMaxBounds.Doubles.between(90.0, 160.0)),
    MOUNTAIN_LEVEL(MinMaxBounds.Doubles.between(65.0, 155.0)),
    SEA_LEVEL(MinMaxBounds.Doubles.between(0.0, 70.0)),
    LOWLANDS(MinMaxBounds.Doubles.between(30.0, 65.0)),
    UNDERGROUND_LEVEL(MinMaxBounds.Doubles.between(10.0, 50.0)),
    UNDERWATER_CAVES(MinMaxBounds.Doubles.between(-10.0, 30.0)),
    CAVERN(MinMaxBounds.Doubles.atMost(10.0)),
    DEEP_DARK(MinMaxBounds.Doubles.atMost(-40.0));

    private final MinMaxBounds.Doubles range;

    HeightRanges(MinMaxBounds.Doubles range) {
        this.range = range;
    }

    public MinMaxBounds.Doubles getRange() {
        return range;
    }
}
