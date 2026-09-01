package net.vg.fishingfrenzy.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;
import java.util.stream.Stream;

public enum BiomeCategories {
    WARM_WATERS(List.of(
            Biomes.OCEAN,
            Biomes.DEEP_OCEAN,
            Biomes.LUKEWARM_OCEAN,
            Biomes.DEEP_LUKEWARM_OCEAN,
            Biomes.WARM_OCEAN,
            Biomes.BEACH,
            Biomes.STONY_SHORE,
            Biomes.DESERT
    )),
    COLD_WATERS(List.of(
            Biomes.COLD_OCEAN,
            Biomes.DEEP_COLD_OCEAN,
            Biomes.FROZEN_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN,
            Biomes.SNOWY_BEACH,
            Biomes.FROZEN_RIVER
    )),
    RIVERS(List.of(
            Biomes.FROZEN_RIVER,
            Biomes.RIVER
    )),
    BEACHES(List.of(
            Biomes.BEACH,
            Biomes.SNOWY_BEACH,
            Biomes.STONY_SHORE
    )),
    FORESTS(List.of(
            Biomes.FOREST,
            Biomes.FLOWER_FOREST,
            Biomes.BIRCH_FOREST,
            Biomes.DARK_FOREST,
            Biomes.OLD_GROWTH_BIRCH_FOREST,
            Biomes.OLD_GROWTH_PINE_TAIGA,
            Biomes.OLD_GROWTH_SPRUCE_TAIGA,
            Biomes.CHERRY_GROVE
    )),
    JUNGLES(List.of(
            Biomes.JUNGLE,
            Biomes.BAMBOO_JUNGLE,
            Biomes.SPARSE_JUNGLE
    )),
    SWAMPS(List.of(
            Biomes.SWAMP,
            Biomes.MANGROVE_SWAMP
    )),
    MOUNTAINS(List.of(
            Biomes.WINDSWEPT_HILLS,
            Biomes.WINDSWEPT_GRAVELLY_HILLS,
            Biomes.WINDSWEPT_FOREST,
            Biomes.WINDSWEPT_SAVANNA,
            Biomes.SNOWY_SLOPES,
            Biomes.FROZEN_PEAKS,
            Biomes.JAGGED_PEAKS,
            Biomes.STONY_PEAKS,
            Biomes.CHERRY_GROVE,
            Biomes.GROVE
    )),
    ICY_BIOMES(List.of(
            Biomes.SNOWY_SLOPES,
            Biomes.FROZEN_PEAKS,
            Biomes.JAGGED_PEAKS,
            Biomes.STONY_PEAKS,
            Biomes.ICE_SPIKES
    )),
    PLAINS(List.of(
            Biomes.PLAINS,
            Biomes.SUNFLOWER_PLAINS,
            Biomes.SAVANNA,
            Biomes.SAVANNA_PLATEAU,
            Biomes.MEADOW
    )),
    SAVANNAS(List.of(
            Biomes.SAVANNA,
            Biomes.SAVANNA_PLATEAU,
            Biomes.BADLANDS,
            Biomes.WINDSWEPT_SAVANNA
    )),
    DESERTS(List.of(
            Biomes.DESERT,
            Biomes.BADLANDS,
            Biomes.ERODED_BADLANDS,
            Biomes.WOODED_BADLANDS
    )),
    SNOWY_BIOMES(List.of(
            Biomes.SNOWY_TAIGA,
            Biomes.SNOWY_SLOPES,
            Biomes.ICE_SPIKES
    )),
    UNDERGROUND(List.of(
            Biomes.DRIPSTONE_CAVES,
            Biomes.LUSH_CAVES,
            Biomes.DEEP_DARK
    )),
    MISCELLANEOUS(List.of(
            Biomes.MUSHROOM_FIELDS
    ));

    private final List<ResourceKey<Biome>> biomes;

    BiomeCategories(List<ResourceKey<Biome>> biomes) {
        this.biomes = biomes;
    }
    public List<ResourceKey<Biome>> getBiomes() {
        return biomes;
    }

    public static List<ResourceKey<Biome>> combine(BiomeCategories ... categories) {
        return Stream.of(categories)
                .flatMap(category -> category.getBiomes().stream())
                .distinct()
                .toList();
    }

}
