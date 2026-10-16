package net.vg.fishingfrenzy.world;

import dev.architectury.hooks.level.biome.BiomeProperties;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.config.ModConfigs;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.item.fish.FishRegistry;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class ModEntitySpawns {
    /**
     * Natural fish must have a meaningful connected body of water, not merely one valid
     * water block. 32 deliberately rejects a 3x3x3 pool (27 blocks) and irrigation lines.
     */
    public static final int MINIMUM_CONNECTED_WATER_BLOCKS = 32;

    public static void addEntitySpawnsBasic() {
        Constants.LOGGER.info("Attempting to add basic entity spawns");

        FishManager.FISH_REGISTRIES.forEach(fishRegistry -> {
            Constants.LOGGER.info("Spawn for {}", fishRegistry.getFishName());
            // Add spawn to all biomes
            BiomeModifications.addProperties(
                    context -> true, // This will add the spawn to all biomes
                    (context, properties) -> properties.getSpawnProperties().addSpawn(
                            MobCategory.WATER_AMBIENT,
                            new MobSpawnSettings.SpawnerData(
                                    fishRegistry.getFishEntityType().get(),
                                    fishRegistry.getGroupSizes().getFirst(),
                                    fishRegistry.getGroupSizes().getSecond()
                            ),
                            fishRegistry.getSpawningWeight()
                            )
            );

            // Register spawn placement
            SpawnPlacementsRegistry.register(
                    fishRegistry.getFishEntityType(),
                    SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    (entityType, serverLevelAccessor, mobSpawnType, blockPos, random) ->
                            // Only check if the spawn location is in water
                            serverLevelAccessor.getFluidState(blockPos).is(FluidTags.WATER)
            );
        });
        Constants.LOGGER.info("Added basic entity spawns");
    }

    public static void addEntitySpawns() {
        FishManager.FISH_REGISTRIES.forEach(fishRegistry -> {

            BiomeModifications.addProperties(
                    context -> shouldAddSpawn(context, fishRegistry),
                    (context, properties) -> properties.getSpawnProperties().addSpawn(
                            MobCategory.WATER_AMBIENT,
                            new MobSpawnSettings.SpawnerData(
                                    fishRegistry.getFishEntityType().get(),
                                    fishRegistry.getGroupSizes().getFirst(),
                                    fishRegistry.getGroupSizes().getSecond()
                            ),
                            fishRegistry.getSpawningWeight()
                            )
            );

            SpawnPlacementsRegistry.register(
                    fishRegistry.getFishEntityType(),
                    SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> canSpawn(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource, fishRegistry))

            );
        });
    }


    private static boolean shouldAddSpawn(BiomeModifications.BiomeContext context, FishRegistry fishRegistry) {
        if (ModConfigs.EASY_MODE) {
            return true;
        }
        if (fishRegistry.getBiomes().isEmpty()) {
            return true;
        }
        // Architectury gives ResourceLocation here; fish config stores ResourceKey<Biome>.
        return context.getKey()
                .map(location -> fishRegistry.getBiomes().stream().anyMatch(biomeKey -> biomeKey.identifier().equals(location)))
                .orElse(true);
    }

    public static boolean canSpawn(EntityType<? extends Entity> type, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random, FishRegistry fishRegistry) {
        return getSpawnRuleFailure(type, level, spawnType, pos, random, fishRegistry) == null;
    }

    /** Returns the first failed natural-spawn gate, or {@code null} when all gates pass. */
    public static String getSpawnRuleFailure(EntityType<? extends Entity> type, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random, FishRegistry fishRegistry) {
        if (!WaterAnimal.checkSurfaceWaterAnimalSpawnRules((EntityType<? extends WaterAnimal>) type, level, spawnType, pos, random)) {
            return "surface_water";
        }
        if (!hasMinimumConnectedWater(level, pos)) {
            return "connected_water";
        }
        if (ModConfigs.EASY_MODE) {
            return null;
        }
        if (!fishRegistry.getYRange().matches(pos.getY())) {
            return "height";
        }
        long timeOfDay = level.getLevel().getOverworldClockTime();
        if (!isWithinTimeRange(timeOfDay, fishRegistry.getMinTime(), fishRegistry.getMaxTime())) {
            return "time";
        }
        if (fishRegistry.isWeatherDependent()) {
            boolean isRaining = level.getLevel().isRaining();
            boolean isThundering = level.getLevel().isThundering();

            if (fishRegistry.isRaining() && !isRaining) {
                return "rain";
            }
            if (fishRegistry.isThundering() && !isThundering) {
                return "thunder";
            }
            if (!fishRegistry.isRaining() && !fishRegistry.isThundering() && (isRaining || isThundering)) {
                return "clear_weather";
            }
        }
        return null;
    }

    public static boolean hasMinimumConnectedWater(ServerLevelAccessor level, BlockPos origin) {
        return countConnectedWaterBlocks(level, origin) >= MINIMUM_CONNECTED_WATER_BLOCKS;
    }

    /**
     * Breadth-first search capped at the threshold, so an ocean costs no more than 32 checks.
     */
    public static int countConnectedWaterBlocks(ServerLevelAccessor level, BlockPos origin) {
        if (!level.getFluidState(origin).is(FluidTags.WATER)) {
            return 0;
        }

        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> toVisit = new ArrayDeque<>();
        visited.add(origin.immutable());
        toVisit.add(origin.immutable());

        while (!toVisit.isEmpty() && visited.size() < MINIMUM_CONNECTED_WATER_BLOCKS) {
            BlockPos current = toVisit.removeFirst();
            for (Direction direction : Direction.values()) {
                BlockPos adjacent = current.relative(direction);
                if (level.getFluidState(adjacent).is(FluidTags.WATER) && visited.add(adjacent)) {
                    toVisit.addLast(adjacent);
                }
            }
        }
        return visited.size();
    }

    private static boolean isWithinTimeRange(long currentTime, int minTime, int maxTime) {
        currentTime = Math.floorMod(currentTime, 24000);
        if (minTime <= maxTime) {
            return currentTime >= minTime && currentTime <= maxTime;
        } else {
            return currentTime >= minTime || currentTime <= maxTime;
        }
    }
}
