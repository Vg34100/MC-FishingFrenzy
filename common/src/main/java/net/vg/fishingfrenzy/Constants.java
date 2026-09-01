package net.vg.fishingfrenzy;

import dev.architectury.platform.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "fishingfrenzy";
    public static final String MOD_NAME = "Fishing Frenzy";
    public static final String MOD_VERSION = fetchModVersion();

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static String fetchModVersion() {
        String DEFAULT_VERSION = "1.0.0";

        if (Platform.isModLoaded(MOD_ID)) {
            return Platform.getMod(MOD_ID).getVersion();
        } else {
            return DEFAULT_VERSION;
        }
    }
}
