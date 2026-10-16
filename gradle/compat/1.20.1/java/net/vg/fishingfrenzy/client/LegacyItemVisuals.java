package net.vg.fishingfrenzy.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.vg.fishingfrenzy.registry.ModItems;

/** Vanilla's legacy cast predicate, registered for the Deluxe Rod as well. */
public final class LegacyItemVisuals {
    private LegacyItemVisuals() { }

    public static void register() {
        ItemProperties.register(ModItems.DELUXE_FISHING_ROD.get(), new ResourceLocation("cast"),
                (stack, level, entity, seed) -> {
                    if (entity == null) return 0.0F;
                    boolean main = entity.getMainHandItem() == stack;
                    boolean off = entity.getOffhandItem() == stack;
                    if (entity.getMainHandItem().getItem() instanceof FishingRodItem) off = false;
                    return (main || off) && entity instanceof Player player && player.fishing != null ? 1.0F : 0.0F;
                });
    }
}
