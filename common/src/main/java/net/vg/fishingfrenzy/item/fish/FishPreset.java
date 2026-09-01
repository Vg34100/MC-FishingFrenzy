package net.vg.fishingfrenzy.item.fish;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffects;
import net.vg.fishingfrenzy.util.BiomeCategories;

import java.util.function.Consumer;

public class FishPreset {

    public static Consumer<FishPropertiesBuilder> TEST = builder -> {
        builder.setWeight(3000).setQuality(0).setSpawningWeight(10);
    };

    public static Consumer<FishPropertiesBuilder> RARITY_COMMON_SMALL = builder -> {
        builder.setWeight(60).setQuality(0).setSpawningWeight(5).setGroupSizes(Pair.of(3, 5));
    };

    public static Consumer<FishPropertiesBuilder> RARITY_COMMON = builder -> {
        builder.setWeight(50).setQuality(0).setSpawningWeight(3).setGroupSizes(Pair.of(2, 3));
    };

    public static Consumer<FishPropertiesBuilder> RARITY_RARE = builder -> {
        builder.setWeight(30).setQuality(1).setSpawningWeight(2).setGroupSizes(Pair.of(1, 2));
    };

    public static Consumer<FishPropertiesBuilder> RARITY_LEGENDARY = builder -> {
        builder.setWeight(1).setQuality(3).setSpawningWeight(1).setGroupSizes(Pair.of(0, 1));
    };

    public static Consumer<FishPropertiesBuilder> BIOME_WARM_CLIMATE_FISH = builder -> {
        builder.setBiomes(
                BiomeCategories.combine(
                        BiomeCategories.BEACHES,
                        BiomeCategories.WARM_WATERS,
                        BiomeCategories.DESERTS
                )
        );
    };

    public static Consumer<FishPropertiesBuilder> BIOME_FRESHWATER_FISH = builder -> {
        builder.setBiomes(
                BiomeCategories.combine(
                        BiomeCategories.RIVERS,
                        BiomeCategories.WARM_WATERS,
                        BiomeCategories.FORESTS,
                        BiomeCategories.PLAINS
                )
        );
    };

    public static Consumer<FishPropertiesBuilder> BIOME_TROPICAL_FISH = builder -> {
        builder.setBiomes(
                BiomeCategories.combine(
                        BiomeCategories.RIVERS,
                        BiomeCategories.FORESTS,
                        BiomeCategories.WARM_WATERS,
                        BiomeCategories.BEACHES,
                        BiomeCategories.JUNGLES
                )
        );
    };

    public static Consumer<FishPropertiesBuilder> BIOME_MARSH_FISH = builder -> {
        builder.setBiomes(
                BiomeCategories.combine(
                        BiomeCategories.RIVERS,
                        BiomeCategories.FORESTS,
                        BiomeCategories.SWAMPS
                )
        );
    };

    public static Consumer<FishPropertiesBuilder> TIME_DAY = builder -> {
        builder.setMinTime(0).setMaxTime(12000);
    };

    public static Consumer<FishPropertiesBuilder> TIME_NIGHT = builder -> {
        builder.setMinTime(12000).setMaxTime(24000);
    };

    public static Consumer<FishPropertiesBuilder> TIME_DAWN = builder -> {
        builder.setMinTime(23000).setMaxTime(4000);
    };

    public static Consumer<FishPropertiesBuilder> TIME_DUSK = builder -> {
        builder.setMinTime(19000).setMaxTime(23000);
    };

    public static Consumer<FishPropertiesBuilder> WEATHER_RAIN = builder -> {
        builder.setWeatherDependent(true).setRaining(true).setThundering(false);
    };

    public static Consumer<FishPropertiesBuilder> WEATHER_SUNNY = builder -> {
        builder.setWeatherDependent(true).setRaining(false).setThundering(false);
    };

    public static Consumer<FishPropertiesBuilder> WEATHER_RAIN_THUNDER = builder -> {
        builder.setWeatherDependent(true).setRaining(true).setThundering(true);
    };

    public static Consumer<FishPropertiesBuilder> FOOD_LIGHT = builder -> {
        builder.setSnack(true)
                .setFoodAttributes(1, 0.2f)
                .setCookedFoodAttributes(2, 0.4f);
    };

    public static Consumer<FishPropertiesBuilder> FOOD_BALANCED = builder -> {
        builder.setFoodAttributes(2, 0.5f)
                .setCookedFoodAttributes(4, 1.5f);
    };

    public static Consumer<FishPropertiesBuilder> FOOD_HEARTY = builder -> {
        builder.setFoodAttributes(3, 1f)
                .addStatusEffect(MobEffects.REGENERATION, 150, 0, 0.2f)
                .setCookedFoodAttributes(5, 2f)
                .addCookedStatusEffect(MobEffects.REGENERATION, 250, 1, 0.5f);
    };

    public static Consumer<FishPropertiesBuilder> FOOD_POISONOUS = builder -> {
        builder.setFoodAttributes(2, 0.2f)
                .addStatusEffect(MobEffects.POISON, 150, 0, 0.8f)
                .setCookedFoodAttributes(3, 1f)
                .addCookedStatusEffect(MobEffects.POISON, 150, 0, 0.2f);
    };

    public static Consumer<FishPropertiesBuilder> FOOD_NUTRITIOUS = builder -> {
        builder.setFoodAttributes(4, 1.2f)
                .addStatusEffect(MobEffects.STRENGTH, 200, 0, 0.2f)
                .setCookedFoodAttributes(7, 1.9f)
                .addCookedStatusEffect(MobEffects.STRENGTH, 300, 1, 0.3f);
    };

    public static Consumer<FishPropertiesBuilder> FOOD_ENERGIZING = builder -> {
        builder.setFoodAttributes(3, 0.8f)
                .addStatusEffect(MobEffects.HASTE, 200, 0, 0.3f)
                .setCookedFoodAttributes(4, 1.5f)
                .addCookedStatusEffect(MobEffects.HASTE, 400, 1, 0.8f);
    };

    public static Consumer<FishPropertiesBuilder> EFFECT_SPEED = builder -> {
        builder.addStatusEffect(MobEffects.SPEED, 200, 1, 0.5f);
    };

    public static Consumer<FishPropertiesBuilder> EFFECT_FIRE_RESISTANCE = builder -> {
        builder.addStatusEffect(MobEffects.FIRE_RESISTANCE, 300, 1, 0.5f);
    };

    public static Consumer<FishPropertiesBuilder> EFFECT_NIGHT_VISION = builder -> {
        builder.addStatusEffect(MobEffects.NIGHT_VISION, 200, 1, 0.5f);
    };

    public static Consumer<FishPropertiesBuilder> EFFECT_WATER_BREATHING = builder -> {
        builder.addStatusEffect(MobEffects.WATER_BREATHING, 300, 1, 0.5f);
    };

    public static Consumer<FishPropertiesBuilder> EFFECT_JUMP_BOOST = builder -> {
        builder.addStatusEffect(MobEffects.JUMP_BOOST, 200, 1, 0.5f);
    };

    @SafeVarargs
    public static FishPropertiesBuilder applyPresets(FishPropertiesBuilder builder, Consumer<FishPropertiesBuilder>... presets) {
        for (Consumer<FishPropertiesBuilder> preset : presets) {
            preset.accept(builder);
        }
        return builder;
    }
}
