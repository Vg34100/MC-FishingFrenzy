package net.vg.fishingfrenzy.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.registry.DynamicFishSystem;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class FishingFrenzyNeoForgeClient {
    private FishingFrenzyNeoForgeClient() {
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        DynamicFishSystem.getFishRenderDefinitions().forEach(definition ->
                event.registerLayerDefinition(definition.getModelLayer(), definition::createLayerDefinition)
        );
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        DynamicFishSystem.getFishRenderDefinitions().forEach(definition ->
                event.registerEntityRenderer(definition.getEntityType(), definition::createRenderer)
        );
    }
}
