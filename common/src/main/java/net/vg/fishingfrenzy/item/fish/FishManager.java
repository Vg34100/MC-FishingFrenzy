package net.vg.fishingfrenzy.item.fish;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.vg.fishingfrenzy.util.BiomeCategories;
import net.vg.fishingfrenzy.util.HeightRanges;

import java.util.*;

public class FishManager {

    public static final List<FishRegistry> FISH_REGISTRIES = new ArrayList<>();
    public static final Map<String, FishRegistry> FISH_REGISTRY_MAP = new HashMap<>();
    public enum ItemType {
        FISH,
        COOKED_FISH,
        BAIT,
        SPAWN_EGG,
        BUCKET
    }

    public static final EnumMap<ItemType, List<RegistrySupplier<Item>>> ALL_FISH_ITEMS = new EnumMap<>(ItemType.class);
    static {
        ALL_FISH_ITEMS.put(ItemType.FISH, new ArrayList<>());
        ALL_FISH_ITEMS.put(ItemType.COOKED_FISH, new ArrayList<>());
        ALL_FISH_ITEMS.put(ItemType.BAIT, new ArrayList<>());
        ALL_FISH_ITEMS.put(ItemType.SPAWN_EGG, new ArrayList<>());
        ALL_FISH_ITEMS.put(ItemType.BUCKET, new ArrayList<>());
    }

    public static void registerAllFish() {
        FishRegistry ANCHOVY = new FishRegistry(
                "anchovy",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON_SMALL,
                        FishPreset.BIOME_WARM_CLIMATE_FISH,
                        FishPreset.FOOD_LIGHT
                )
                        .setPrimaryColor(0x28ccf0)
                        .setSecondaryColor(0x93ffb6)
                        .setEntitySize(0.30f, 0.18f)
                        .setShadowRadius(0.12f)
        );
        ANCHOVY.postInit();

        FishRegistry ALBACORE = new FishRegistry(
                "albacore",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.BIOME_WARM_CLIMATE_FISH,
                        FishPreset.FOOD_BALANCED
                )
                        .setPrimaryColor(0x4391ba)
                        .setSecondaryColor(0x296fa2)
                        .setEntitySize(0.55f, 0.34f)
                        .setShadowRadius(0.22f)
                        .setBiomes(BiomeCategories.combine(BiomeCategories.WARM_WATERS, BiomeCategories.COLD_WATERS, BiomeCategories.BEACHES))
                        .setBreedingItem(Items.SEAGRASS)
        );
        ALBACORE.postInit();

        FishRegistry BLUE_DISCUS = new FishRegistry(
                "blue_discus",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.FOOD_BALANCED
                )
                        .setPrimaryColor(0x8bf4c5)
                        .setSecondaryColor(0x12e474)
                        .setEntitySize(0.45f, 0.34f)
                        .setShadowRadius(0.18f)
                        .setBiomes(BiomeCategories.JUNGLES.getBiomes())
        );
        BLUE_DISCUS.postInit();

        FishRegistry BREAM = new FishRegistry(
                "bream",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.TIME_NIGHT,
                        FishPreset.FOOD_BALANCED,
                        FishPreset.BIOME_FRESHWATER_FISH
                )
                        .setPrimaryColor(0x72b6cd)
                        .setSecondaryColor(0x7f80eb)
                        .setEntitySize(0.48f, 0.34f)
                        .setShadowRadius(0.20f)
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        BREAM.postInit();

        FishRegistry BULLHEAD = new FishRegistry(
                "bullhead",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.BIOME_FRESHWATER_FISH,
                        FishPreset.FOOD_BALANCED
                )
                        .setPrimaryColor(0x733d1f)
                        .setSecondaryColor(0x8b4828)
                        .setEntitySize(0.42f, 0.30f)
                        .setShadowRadius(0.17f)
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        BULLHEAD.postInit();

        FishRegistry CATFISH = new FishRegistry(
                "catfish",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_LEGENDARY,
                        FishPreset.WEATHER_RAIN,
                        FishPreset.FOOD_HEARTY,
                        FishPreset.BIOME_FRESHWATER_FISH
                )
                        .setPrimaryColor(0x54473c)
                        .setSecondaryColor(0x494a4b)
                        .setEntitySize(0.62f, 0.38f)
                        .setShadowRadius(0.24f)
                        .setShouldAttack(true)
                        .setAdditionalDrops(List.of(Items.STRING))
        );
        CATFISH.postInit();

        FishRegistry WOODSKIP = new FishRegistry(
                "woodskip",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.BIOME_FRESHWATER_FISH,
                        FishPreset.FOOD_BALANCED
                )
                        .setPrimaryColor(0x8d6a4f)
                        .setSecondaryColor(0x6f563f)
                        .setEntitySize(0.56f, 0.34f)
                        .setShadowRadius(0.22f)
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        WOODSKIP.postInit();

        FishRegistry BONEFISH = new FishRegistry(
                "bonefish",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_RARE,
                        FishPreset.TIME_DAY,
                        FishPreset.WEATHER_SUNNY,
                        FishPreset.FOOD_LIGHT
                )
                        .setPrimaryColor(0x424232)
                        .setSecondaryColor(0x38382a)
                        .setEntitySize(0.48f, 0.30f)
                        .setShadowRadius(0.19f)
                        .setBiomes(BiomeCategories.WARM_WATERS.getBiomes())
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
                        .setAdditionalDrops(List.of(Items.BONE))
                        .setShouldCook(false)
        );
        BONEFISH.postInit();

        FishRegistry CARP = new FishRegistry(
                "carp",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.BIOME_FRESHWATER_FISH,
                        FishPreset.FOOD_BALANCED
                )
                        .setPrimaryColor(0xeec39a)
                        .setSecondaryColor(0xe8bb8f)
                        .setEntitySize(0.60f, 0.36f)
                        .setShadowRadius(0.24f)
                        .setBiomes(BiomeCategories.combine(BiomeCategories.RIVERS, BiomeCategories.SWAMPS, BiomeCategories.PLAINS, BiomeCategories.FORESTS))
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        CARP.postInit();

        FishRegistry COBBLESTONE_FISH = new FishRegistry(
                "cobblestone_fish",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_COMMON,
                        FishPreset.FOOD_LIGHT
                )
                        .setPrimaryColor(0x3d4245)
                        .setSecondaryColor(0x4d5458)
                        .setEntitySize(0.52f, 0.32f)
                        .setShadowRadius(0.21f)
                        .setBiomes(BiomeCategories.UNDERGROUND.getBiomes())
                        .setYRange(HeightRanges.UNDERWATER_CAVES.getRange())
                        .setShouldCook(false)
        );
        COBBLESTONE_FISH.postInit();

        FishRegistry DORADO = new FishRegistry(
                "dorado",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_RARE,
                        FishPreset.TIME_DAY,
                        FishPreset.WEATHER_SUNNY,
                        FishPreset.FOOD_BALANCED,
                        FishPreset.EFFECT_SPEED
                )
                        .setPrimaryColor(0xd76e21)
                        .setSecondaryColor(0xb0622a)
                        .setEntitySize(0.54f, 0.34f)
                        .setShadowRadius(0.22f)
                        .setBiomes(BiomeCategories.combine(BiomeCategories.JUNGLES, BiomeCategories.RIVERS))
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        DORADO.postInit();

        FishRegistry EEL = new FishRegistry(
                "eel",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_RARE,
                        FishPreset.TIME_NIGHT,
                        FishPreset.WEATHER_RAIN,
                        FishPreset.FOOD_BALANCED
                )
                        .setPrimaryColor(0x8a6f30)
                        .setSecondaryColor(0x7e6428)
                        .setEntitySize(0.62f, 0.22f)
                        .setShadowRadius(0.22f)
                        .setBiomes(BiomeCategories.combine(BiomeCategories.SWAMPS, BiomeCategories.RIVERS))
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        EEL.postInit();

        FishRegistry TUNA = new FishRegistry(
                "tuna",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_RARE,
                        FishPreset.FOOD_NUTRITIOUS
                )
                        .setPrimaryColor(0x1e4953)
                        .setSecondaryColor(0x5fcde4)
                        .setEntitySize(0.72f, 0.42f)
                        .setShadowRadius(0.28f)
                        .setBiomes(BiomeCategories.combine(BiomeCategories.WARM_WATERS, BiomeCategories.COLD_WATERS))
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        TUNA.postInit();

        FishRegistry VOID_SALMON = new FishRegistry(
                "void_salmon",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_LEGENDARY,
                        FishPreset.TIME_NIGHT,
                        FishPreset.WEATHER_RAIN_THUNDER,
                        FishPreset.FOOD_HEARTY
                )
                        .setPrimaryColor(0x561e1e)
                        .setSecondaryColor(0x902d2d)
                        .setEntitySize(0.66f, 0.38f)
                        .setShadowRadius(0.26f)
                        .setBiomes(BiomeCategories.UNDERGROUND.getBiomes())
                        .setYRange(HeightRanges.DEEP_DARK.getRange())
        );
        VOID_SALMON.postInit();

        FishRegistry WALLEYE = new FishRegistry(
                "walleye",
                FishPreset.applyPresets(
                        new FishPropertiesBuilder(),
                        FishPreset.RARITY_RARE,
                        FishPreset.TIME_DUSK,
                        FishPreset.FOOD_BALANCED,
                        FishPreset.BIOME_FRESHWATER_FISH
                )
                        .setPrimaryColor(0x322c1c)
                        .setSecondaryColor(0x8a6f30)
                        .setEntitySize(0.58f, 0.34f)
                        .setShadowRadius(0.23f)
                        .setYRange(HeightRanges.SEA_LEVEL.getRange())
        );
        WALLEYE.postInit();

    }

    public static void addFishRegistry(FishRegistry fishRegistry) {
        FISH_REGISTRIES.add(fishRegistry);
        FISH_REGISTRY_MAP.put(fishRegistry.getFishName(), fishRegistry);

        RegistrySupplier<Item> fish = fishRegistry.getFishRegistry();
        RegistrySupplier<Item> cookedFish = fishRegistry.getCookedFishRegistry();
        RegistrySupplier<Item> targetedBait = fishRegistry.getTargetBaitRegistry();
        RegistrySupplier<Item> spawnEgg = fishRegistry.getSpawnEggRegistry();
        RegistrySupplier<Item> bucket = fishRegistry.getBucketItemRegistry();

        if (fish != null) {
            ALL_FISH_ITEMS.get(ItemType.FISH).add(fish);
        }
        if (targetedBait != null) {
            ALL_FISH_ITEMS.get(ItemType.BAIT).add(targetedBait);
        }
        if (spawnEgg != null) {
            ALL_FISH_ITEMS.get(ItemType.SPAWN_EGG).add(spawnEgg);
        }
        if (cookedFish != null) {
            ALL_FISH_ITEMS.get(ItemType.COOKED_FISH).add(cookedFish);
        }
        if (bucket != null) {
            ALL_FISH_ITEMS.get(ItemType.BUCKET).add(bucket);
        }
    }


    public static List<RegistrySupplier<Item>> getItemsByType(ItemType type) {
        return ALL_FISH_ITEMS.get(type);
    }

}
