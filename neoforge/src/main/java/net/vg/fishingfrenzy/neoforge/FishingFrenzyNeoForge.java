package net.vg.fishingfrenzy.neoforge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.FishingFrenzy;
import net.vg.fishingfrenzy.client.ModClientEvents;
import net.vg.fishingfrenzy.entity.mob.CustomBreedableSchoolingFishEntity;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.world.ModEntitySpawns;

@Mod(Constants.MOD_ID)
public final class FishingFrenzyNeoForge {
    public FishingFrenzyNeoForge(IEventBus modEventBus) {
        FishingFrenzy.init();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerSpawnPlacements);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(FishingFrenzy::initWorldSpawns);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModClientEvents.registerClient();
        });
    }

    @SuppressWarnings("unchecked")
    private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        FishManager.FISH_REGISTRIES.forEach(fishRegistry -> {
            EntityType<CustomBreedableSchoolingFishEntity> type =
                (EntityType<CustomBreedableSchoolingFishEntity>) fishRegistry.getFishEntityType().get();
            event.register(
                type,
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, reason, pos, random) ->
                    ModEntitySpawns.canSpawn(entityType, level, reason, pos, random, fishRegistry),
                RegisterSpawnPlacementsEvent.Operation.OR
            );
        });
    }
}
