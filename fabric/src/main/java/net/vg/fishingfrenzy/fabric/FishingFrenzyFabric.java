package net.vg.fishingfrenzy.fabric;

import net.fabricmc.api.ModInitializer;
import net.vg.fishingfrenzy.FishingFrenzy;

public final class FishingFrenzyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FishingFrenzy.init();
        FishingFrenzy.initWorldSpawns();
    }
}
