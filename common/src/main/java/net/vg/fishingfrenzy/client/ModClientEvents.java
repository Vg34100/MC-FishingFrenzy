package net.vg.fishingfrenzy.client;

// ColorHandlerRegistry.registerItemColors was removed in Architectury 20.x.
// Bait tint registration is handled per-platform (Fabric/NeoForge) via their own APIs.
public class ModClientEvents {
    public static void registerCastTexture() {
        // ItemProperties was removed in MC 26.1.2 — cast texture handled by vanilla
    }

    public static void registerBaitColors() {
        // Moved to platform-specific modules — Architectury 20 dropped item color API
    }

    public static void registerClient() {
        registerCastTexture();
        registerBaitColors();
    }
}
