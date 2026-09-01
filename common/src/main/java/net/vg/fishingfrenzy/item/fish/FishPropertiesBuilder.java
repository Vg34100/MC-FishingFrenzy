package net.vg.fishingfrenzy.item.fish;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.vg.fishingfrenzy.util.StatusEffectEntry;

import java.util.ArrayList;
import java.util.List;

public class FishPropertiesBuilder implements FishProperties {

    // Spawn Conditions
    private int weight = 25;
    private int quality = 0;
    private MinMaxBounds.Doubles yRange = MinMaxBounds.Doubles.ANY;
    private int minTime = 0;
    private int maxTime = 24000;
    private boolean weatherDependent = false;
    private boolean raining = false;
    private boolean thundering = false;
    private List<ResourceKey<Biome>> biomes = List.of();

    // Spawn Egg
    private int primaryColor = 0xffd476;
    private int secondaryColor = 0xb29452;
    private EntityType<? extends Mob> fishEntityType = null;

    // Entity
    private int spawningWeight = 5;
    private Pair<Integer, Integer> groupSizes = Pair.of(2, 3);

    // Food Attributes
    private Pair<Integer, Float> foodAttributes = Pair.of(2, 0.1f);
    private List<StatusEffectEntry> statusEffects = new ArrayList<>();

    // Cooked Food Attributes
    private Pair<Integer, Float> cookedFoodAttributes = Pair.of(4, 0.3f);
    private List<StatusEffectEntry> cookedStatusEffects = new ArrayList<>();

    private boolean snack = false;
    private boolean shouldCook = true;
    private boolean shouldBucket = true;

    // Breeding Properties
    private Item breedingItem = Items.SEAGRASS;

    // Entity Properties
    private double babySizeMultiplier = 0.5;
    private int maxHealth = 3;
    private float moveSpeed = 1.0f;
    private List<Item> additionalDrops = List.of();
    private boolean shouldAttack = false;
    private float attackDamage = 1.0f;
    private float entityWidth = 0.45f;
    private float entityHeight = 0.30f;
    private float shadowRadius = 0.20f;


    public FishPropertiesBuilder setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public FishPropertiesBuilder setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public FishPropertiesBuilder setYRange(MinMaxBounds.Doubles yRange) {
        this.yRange = yRange;
        return this;
    }

    public FishPropertiesBuilder setMinTime(int minTime) {
        this.minTime = minTime;
        return this;
    }

    public FishPropertiesBuilder setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public FishPropertiesBuilder setShouldCook(boolean shouldCook) {
        this.shouldCook = shouldCook;
        return this;
    }

    public FishPropertiesBuilder setShouldBucket(boolean shouldBucket) {
        this.shouldBucket = shouldBucket;
        return this;
    }

    public FishPropertiesBuilder setWeatherDependent(boolean weatherDependent) {
        this.weatherDependent = weatherDependent;
        return this;
    }

    public FishPropertiesBuilder setRaining(boolean raining) {
        this.raining = raining;
        return this;
    }

    public FishPropertiesBuilder setThundering(boolean thundering) {
        this.thundering = thundering;
        return this;
    }

    public FishPropertiesBuilder setBiomes(List<ResourceKey<Biome>> biomes) {
        this.biomes = biomes;
        return this;
    }

    // Spawn Egg
    public FishPropertiesBuilder setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public FishPropertiesBuilder setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    public FishPropertiesBuilder setFishEntityType(EntityType<? extends Mob> fishEntityType) {
        this.fishEntityType = fishEntityType;
        return this;
    }

    // Entity
    public FishPropertiesBuilder setSpawningWeight(int spawningWeight) {
        this.spawningWeight = spawningWeight;
        return this;
    }

    public FishPropertiesBuilder setGroupSizes(Pair<Integer, Integer> groupSizes) {
        this.groupSizes = groupSizes;
        return this;
    }

    // Food Attributes
    public FishPropertiesBuilder setFoodAttributes(int nutrition, float saturation) {
        this.foodAttributes = Pair.of(nutrition, saturation);
        return this;
    }

    public FishPropertiesBuilder setCookedFoodAttributes(int nutrition, float saturation) {
        this.cookedFoodAttributes = Pair.of(nutrition, saturation);
        return this;
    }

    public FishPropertiesBuilder setSnack(boolean snack) {
        this.snack = snack;
        return this;
    }

    public FishPropertiesBuilder addStatusEffect(Holder<MobEffect> effect, int duration, int amplifier, float chance) {
        this.statusEffects.add(new StatusEffectEntry(effect, duration, amplifier, chance)); //* Add back the chance thing
        return this;
    }

    public FishPropertiesBuilder addCookedStatusEffect(Holder<MobEffect> effect, int duration, int amplifier, float chance) {
        this.cookedStatusEffects.add(new StatusEffectEntry(effect, duration, amplifier, chance)); //* Add back the chance thing
        return this;
    }

    // Breeding Properties
    public FishPropertiesBuilder setBreedingItem(Item breedingItem) {
        this.breedingItem = breedingItem;
        return this;
    }

    // Entity Properties
    public FishPropertiesBuilder setBabySizeMultiplier(double babySizeMultiplier) {
        this.babySizeMultiplier = babySizeMultiplier;
        return this;
    }

    public FishPropertiesBuilder setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public FishPropertiesBuilder setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
        return this;
    }

    public FishPropertiesBuilder setAdditionalDrops(List<Item> additionalDrops) {
        this.additionalDrops = additionalDrops;
        return this;
    }

    public FishPropertiesBuilder setShouldAttack(boolean shouldAttack) {
        this.shouldAttack = shouldAttack;
        return this;
    }

    public FishPropertiesBuilder setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }
    public FishPropertiesBuilder setEntitySize(float width, float height) {
        this.entityWidth = width;
        this.entityHeight = height;
        return this;
    }
    public FishPropertiesBuilder setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
        return this;
    }
    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getQuality() {
        return quality;
    }

    @Override
    public MinMaxBounds.Doubles getYRange() {
        return yRange;
    }

    @Override
    public int getMinTime() {
        return minTime;
    }

    @Override
    public int getMaxTime() {
        return maxTime;
    }

    @Override
    public boolean isWeatherDependent() {
        return weatherDependent;
    }

    @Override
    public boolean isRaining() {
        return raining;
    }

    @Override
    public boolean isThundering() {
        return thundering;
    }

    @Override
    public List<ResourceKey<Biome>> getBiomes() {
        return biomes;
    }

    @Override
    public int getPrimaryColor() {
        return primaryColor;
    }

    @Override
    public int getSecondaryColor() {
        return secondaryColor;
    }

    @Override
    public EntityType<? extends Mob> getFishEntityType() {
        return fishEntityType;
    }

    @Override
    public boolean hasFishEntityType() {
        return fishEntityType != null;
    }

    @Override
    public int getSpawningWeight() {
        return spawningWeight;
    }

    @Override
    public Pair<Integer, Integer> getGroupSizes() {
        return groupSizes;
    }

    // Food Attributes
    @Override
    public Pair<Integer, Float> getFoodAttributes() {
        return foodAttributes;
    }

    @Override
    public List<StatusEffectEntry> getStatusEffects() {
        return statusEffects;
    }

    @Override
    public Pair<Integer, Float> getCookedFoodAttributes() {
        return cookedFoodAttributes;
    }

    @Override
    public List<StatusEffectEntry> getCookedStatusEffects() {
        return cookedStatusEffects;
    }

    @Override
    public boolean isSnack() {
        return snack;
    }

    @Override
    public boolean shouldCook() {
        return shouldCook;
    }

    @Override
    public boolean shouldBucket() {
        return shouldBucket;
    }

    @Override
    public Item getBreedingItem() {
        return breedingItem;
    }

    @Override
    public double getBabySizeMultiplier() {
        return babySizeMultiplier;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public float getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public List<Item> getAdditionalDrops() {
        return additionalDrops;
    }

    @Override
    public boolean shouldAttack() {
        return shouldAttack;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }
    @Override
    public float getEntityWidth() {
        return entityWidth;
    }
    @Override
    public float getEntityHeight() {
        return entityHeight;
    }
    @Override
    public float getShadowRadius() {
        return shadowRadius;
    }

}
