package net.vg.fishingfrenzy.forge.client;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.client.ModClientEvents;
import net.vg.fishingfrenzy.client.LegacyItemVisuals;
import net.vg.fishingfrenzy.item.bait.targetbait.TargetBaitItem;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.registry.DynamicFishSystem;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FishingFrenzyForgeClient {
    private FishingFrenzyForgeClient() { }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModClientEvents.registerClient();
            LegacyItemVisuals.register();
        });
    }

    @SubscribeEvent
    public static void modelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        DynamicFishSystem.getFishRenderDefinitions().forEach(definition ->
                event.registerLayerDefinition(definition.getModelLayer(), definition::createLayerDefinition));
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        DynamicFishSystem.getFishRenderDefinitions().forEach(definition ->
                event.registerEntityRenderer(definition.getEntityType(), definition::createRenderer));
    }

    @SubscribeEvent
    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        Item[] baits = FishManager.getItemsByType(FishManager.ItemType.BAIT).stream()
                .map(supplier -> supplier.get()).toArray(Item[]::new);
        event.register((stack, tint) -> stack.getItem() instanceof TargetBaitItem bait
                ? 0xFF000000 | bait.getColor(tint) : 0xFFFFFFFF, baits);
        Item[] eggs = FishManager.getItemsByType(FishManager.ItemType.SPAWN_EGG).stream()
                .map(supplier -> supplier.get()).toArray(Item[]::new);
        event.register((stack, tint) -> 0xFFFFFFFF, eggs);
    }
}
