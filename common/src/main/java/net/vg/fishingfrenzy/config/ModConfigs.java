package net.vg.fishingfrenzy.config;

import com.mojang.datafixers.util.Pair;
import net.vg.fishingfrenzy.Constants;

import java.util.Arrays;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean EASY_MODE;
    public static boolean FISHINGREAL_COMPAT_ENABLED;



    public static String[] necessaryConfigs = {
        "config.easy.mode",
        "config.compat.fishingreal.enabled"
    };

    public static void registerConfigs() {
        Constants.LOGGER.info("Registering mod configurations.");
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Constants.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        Constants.LOGGER.debug("Creating default configuration values.");
        configs.addKeyValuePair(new Pair<>("config.easy.mode", false), Arrays.asList(
                "Makes the fish easier to get by removing the weather, time, and height conditions",
                "Default: false"
        ));
        configs.addKeyValuePair(new Pair<>("config.compat.fishingreal.enabled", true), Arrays.asList(
                "Enable compatibility bridge for Fishing Real when installed",
                "Default: true"
        ));

    }

    private static void assignConfigs() {
        Constants.LOGGER.info("Assigning configuration values.");
        // Assign configuration values to fields
        EASY_MODE = CONFIG.getOrDefault("config.easy.mode", false);
        FISHINGREAL_COMPAT_ENABLED = CONFIG.getOrDefault("config.compat.fishingreal.enabled", true);


        Constants.LOGGER.info("All {} configurations have been set properly.", configs.getConfigsList().size());
    }

    public static void saveConfigs() {
        Constants.LOGGER.info("Saving current configuration values.");
        CONFIG.set("config.easy.mode", EASY_MODE);
        CONFIG.set("config.compat.fishingreal.enabled", FISHINGREAL_COMPAT_ENABLED);


        CONFIG.save();
        Constants.LOGGER.info("Configuration values saved successfully.");
    }
}
