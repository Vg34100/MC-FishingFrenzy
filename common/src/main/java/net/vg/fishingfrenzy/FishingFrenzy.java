package net.vg.fishingfrenzy;

import dev.architectury.platform.Platform;
import net.vg.fishingfrenzy.command.FishingFrenzyDebugCommands;
import net.vg.fishingfrenzy.config.ModConfigs;
import net.vg.fishingfrenzy.registry.DynamicFishEntityGenerator;
import net.vg.fishingfrenzy.registry.ModItems;
import net.vg.fishingfrenzy.registry.ModTabRegsitry;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.world.ModEntitySpawns;

public final class FishingFrenzy {
    private static final String FISHING_REAL_MOD_ID = "fishingreal";

    public static void init() {
        // Write common init code here.
        Constants.LOGGER.info("Initializing, {} {}", Constants.MOD_ID, Constants.MOD_VERSION);

        ModConfigs.registerConfigs();
        FishManager.registerAllFish();
        DynamicFishEntityGenerator.init();
        ModItems.register();
        DynamicFishEntityGenerator.registerEntityAttributes();
        ModTabRegsitry.register();
        FishingFrenzyDebugCommands.register();
    }

    public static void initWorldSpawns() {
        ModEntitySpawns.addEntitySpawns();
    }

    public static boolean isFishingRealLoaded() {
        return Platform.isModLoaded(FISHING_REAL_MOD_ID);
    }

    public static boolean isFishingRealCompatEnabled() {
        return ModConfigs.FISHINGREAL_COMPAT_ENABLED && isFishingRealLoaded();
    }
}
