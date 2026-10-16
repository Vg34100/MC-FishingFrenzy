package net.vg.fishingfrenzy.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.FishingFrenzy;
import net.vg.fishingfrenzy.entity.mob.CustomBreedableSchoolingFishEntity;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.world.ModEntitySpawns;

@Mod(Constants.MOD_ID)
public final class FishingFrenzyForge {
    public FishingFrenzyForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Constants.MOD_ID, bus);
        FishingFrenzy.init();
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerSpawnPlacements);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(FishingFrenzy::initWorldSpawns);
    }

    private void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        FishManager.FISH_REGISTRIES.forEach(fish -> {
            EntityType<CustomBreedableSchoolingFishEntity> type = fish.getFishEntityType().get();
            event.register(type, SpawnPlacements.Type.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    (entityType, level, reason, pos, random) ->
                            ModEntitySpawns.canSpawn(entityType, level, reason, pos, random, fish),
                    SpawnPlacementRegisterEvent.Operation.OR);
        });
    }
}
