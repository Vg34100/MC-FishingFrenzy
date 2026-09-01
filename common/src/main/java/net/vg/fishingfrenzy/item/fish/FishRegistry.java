package net.vg.fishingfrenzy.item.fish;

import com.mojang.datafixers.util.Pair;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluids;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.entity.mob.CustomBreedableSchoolingFishEntity;
import net.vg.fishingfrenzy.item.bait.BaitPropertiesBuilder;
import net.vg.fishingfrenzy.item.bait.targetbait.TargetBaitItem;
import net.vg.fishingfrenzy.registry.DynamicFishEntityGenerator;
import net.vg.fishingfrenzy.registry.ModItems;
import net.vg.fishingfrenzy.util.StatusEffectEntry;

import java.util.List;

public class FishRegistry {
    private final String fish_name;

    // Item registrations
    private final RegistrySupplier<Item> fish;
    private final RegistrySupplier<Item> cookedFish;
    private final RegistrySupplier<Item> bait;
    private final RegistrySupplier<Item> spawnEgg;
    private RegistrySupplier<Item> bucketItem;

    // Entity properties
    private final RegistrySupplier<EntityType<CustomBreedableSchoolingFishEntity>> fishEntityType;

    // Additional properties
    private final Item breedingItem;
    private final double babySizeMultiplier;
    private final int maxHealth;
    private final float moveSpeed;
    private final float attackDamage;
    private final List<Item> additionalDrops;
    private final boolean shouldAttack;
    private final float entityWidth;
    private final float entityHeight;
    private final float shadowRadius;
    private final boolean shouldBucket;

    private final List<ResourceKey<Biome>> biomes;
    private final MinMaxBounds.Doubles yRange;

    private final int weight;
    private final int quality;

    private final int minTime;
    private final int maxTime;

    private final boolean isWeatherDependent;
    private final boolean raining;
    private final boolean thundering;

    private final int spawningWeight;
    private final Pair<Integer, Integer> groupSizes;

    public FishRegistry(String name, FishProperties properties) {
        this.fish_name = name;

        this.breedingItem = properties.getBreedingItem();
        this.babySizeMultiplier = properties.getBabySizeMultiplier();
        this.maxHealth = properties.getMaxHealth();
        this.moveSpeed = properties.getMoveSpeed();
        this.attackDamage = properties.getAttackDamage();
        this.additionalDrops = properties.getAdditionalDrops();
        this.shouldAttack = properties.shouldAttack();
        this.entityWidth = properties.getEntityWidth();
        this.entityHeight = properties.getEntityHeight();
        this.shadowRadius = properties.getShadowRadius();
        this.shouldBucket = properties.shouldBucket();

        this.biomes = properties.getBiomes();
        this.yRange = properties.getYRange();

        this.weight = properties.getWeight();
        this.quality = properties.getQuality();

        this.minTime = properties.getMinTime();
        this.maxTime = properties.getMaxTime();

        this.isWeatherDependent = properties.isWeatherDependent();
        this.raining = properties.isRaining();
        this.thundering = properties.isThundering();

        this.spawningWeight = properties.getSpawningWeight();
        this.groupSizes = properties.getGroupSizes();

        FoodProperties foodProperties = createFoodComponent(properties.getFoodAttributes(), properties.isSnack(), properties.getStatusEffects());
        FoodProperties cookedFoodProperties = createFoodComponent(properties.getCookedFoodAttributes(), properties.isSnack(), properties.getCookedStatusEffects());

        this.fishEntityType = DynamicFishEntityGenerator.generateFishEntity(this);

//        // Register items
//        this.fish = ModItems.ITEMS.register(name, () -> new FishItem(new Item.Properties().food(foodProperties), properties));
//        this.cookedFish = ModItems.ITEMS.register("cooked_" + name, () -> new Item(new Item.Properties().food(cookedFoodProperties)));
//
//        this.bait = ModItems.ITEMS.register(name + "_bait", () -> new TargetBaitItem(
//                properties.getPrimaryColor(),
//                properties.getSecondaryColor(),
//                new Item.Properties(),
//                new BaitPropertiesBuilder().setTargetedFishSupplier(fish::get)
//        ));
//
//        this.spawnEgg = ModItems.ITEMS.register(name + "_spawn_egg", () ->
//                new SpawnEggItem(new Item.Properties()
//                        .component(DataComponents.ENTITY_DATA,
//                                TypedEntityData.of(fishEntityType.get(), new CompoundTag())))
//        );
        this.fish = ModItems.ITEMS.register(name, () ->
                new FishItem(ModItems.itemProperties(name).food(foodProperties), properties)
        );

        this.cookedFish = properties.shouldCook()
                ? ModItems.ITEMS.register("cooked_" + name, () ->
                        new Item(ModItems.itemProperties("cooked_" + name).food(cookedFoodProperties))
                )
                : null;

        this.bait = ModItems.ITEMS.register(name + "_bait", () -> new TargetBaitItem(
                properties.getPrimaryColor(),
                properties.getSecondaryColor(),
                ModItems.itemProperties(name + "_bait"),
                new BaitPropertiesBuilder().setTargetedFishSupplier(fish::get)
        ));

        this.spawnEgg = ModItems.ITEMS.register(name + "_spawn_egg", () ->
                new SpawnEggItem(ModItems.itemProperties(name + "_spawn_egg")
                        .component(DataComponents.ENTITY_DATA,
                                TypedEntityData.of(fishEntityType.get(), new CompoundTag())))
        );

    }


    public void postInit() {
//        this.bucketItem = ModItems.ITEMS.register(fish_name + "_bucket", () ->
//                new MobBucketItem(
//                        fishEntityType.get(),
//                        Fluids.WATER,
//                        SoundEvents.BUCKET_EMPTY_FISH,
//                        new Item.Properties()
//                                .stacksTo(1)
//                                .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
//                )
//        );
        if (shouldBucket) {
            this.bucketItem = ModItems.ITEMS.register(fish_name + "_bucket", () ->
                new MobBucketItem(
                        fishEntityType.get(),
                        Fluids.WATER,
                        SoundEvents.BUCKET_EMPTY_FISH,
                        ModItems.itemProperties(fish_name + "_bucket")
                                .stacksTo(1)
                                .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                )
            );
        }
        FishManager.addFishRegistry(this);
    }


    // Handles ModFoodComponents
    private FoodProperties createFoodComponent(Pair<Integer, Float> foodAttributes, boolean snack, List<StatusEffectEntry> statusEffects) {
        FoodProperties.Builder builder = new FoodProperties.Builder().nutrition(foodAttributes.getFirst()).saturationModifier(foodAttributes.getSecond());

        Consumable.Builder consumable = Consumable.builder();

        if (snack) {
            consumable.consumeSeconds(0.8F); // old fast food speed, likely half of default 1.6F
        }

        for (StatusEffectEntry entry : statusEffects) {
            consumable.onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(entry.effect(), entry.duration(), entry.amplifier()),
                    entry.chance()
            ));
        }

//        if (snack) {
//            builder.fast();
//        }
//        for (StatusEffectEntry entry : statusEffects) {
//            builder.effect(new MobEffectInstance(entry.effect(), entry.duration(), entry.amplifier()), entry.chance());
//        }
        return builder.build();
    }

    public String getFishName() {
        return fish_name;
    }
    public RegistrySupplier<Item> getFishRegistry() {
        return fish;
    }
    public RegistrySupplier<Item> getCookedFishRegistry() {
        return cookedFish;
    }
    public RegistrySupplier<Item> getTargetBaitRegistry() {
        return bait;
    }
    public RegistrySupplier<Item> getSpawnEggRegistry() {
        return spawnEgg;
    }
    public RegistrySupplier<Item> getBucketItemRegistry() {
        return bucketItem;
    }

    public RegistrySupplier<EntityType<CustomBreedableSchoolingFishEntity>> getFishEntityType() {
        return fishEntityType;
    }
    public EntityType<CustomBreedableSchoolingFishEntity> getFishEntityTypeDirectly() {
        return fishEntityType.get();
    }

    public boolean hasFishEntityType() {
        return fishEntityType != null;
    }

    public Item getBreedingItem() {
        return breedingItem;
    }
    public double getBabySizeMultiplier() {
        return babySizeMultiplier;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public float getMoveSpeed() {
        return moveSpeed;
    }
    public float getAttackDamage() {
        return attackDamage;
    }
    public float getEntityWidth() {
        return entityWidth;
    }
    public float getEntityHeight() {
        return entityHeight;
    }
    public float getShadowRadius() {
        return shadowRadius;
    }
    public List<Item> getAdditionalDrops() {
        return additionalDrops;
    }
    public boolean shouldAttack() {
        return shouldAttack;
    }
    public List<ResourceKey<Biome>> getBiomes() {
        return biomes;
    }
    public MinMaxBounds.Doubles getYRange() {
        return yRange;
    }
    public int getWeight() {
        return weight;
    }
    public int getQuality() {
        return quality;
    }
    public int getMinTime() {
        return minTime;
    }
    public int getMaxTime() {
        return maxTime;
    }
    public boolean isWeatherDependent() {
        return isWeatherDependent;
    }
    public boolean isRaining() {
        return raining;
    }
    public boolean isThundering() {
        return thundering;
    }
    public int getSpawningWeight() {
        return spawningWeight;
    }
    public Pair<Integer, Integer> getGroupSizes() {
        return groupSizes;
    }
}
