package net.vg.fishingfrenzy.item.fish;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class FishItem extends Item {
    private final int weight;
    private final int quality;
    private final MinMaxBounds.Doubles yRange;
    private final int minTime;
    private final int maxTime;
    private final boolean isWeatherDependent;
    private final boolean raining;
    private final boolean thundering;
    private final List<ResourceKey<Biome>> biomes;
    private final int primaryColor;
    private final int secondaryColor;
    private final EntityType<? extends Mob> fishEntityType;
    private final int spawningWeight;
    private final Pair<Integer, Integer> groupSizes;

    public FishItem(Properties settings, FishProperties properties) {
        super(settings);
        this.weight = properties.getWeight();
        this.quality = properties.getQuality();
        this.yRange = properties.getYRange();
        this.minTime = properties.getMinTime();
        this.maxTime = properties.getMaxTime();
        this.isWeatherDependent = properties.isWeatherDependent();
        this.raining = properties.isRaining();
        this.thundering = properties.isThundering();
        this.biomes = properties.getBiomes();
        this.primaryColor = properties.getPrimaryColor();
        this.secondaryColor = properties.getSecondaryColor();
        this.fishEntityType = properties.getFishEntityType();
        this.spawningWeight = properties.getSpawningWeight();
        this.groupSizes = properties.getGroupSizes();

    }

    public int getWeight() {
        return weight;
    }

    public int getQuality() {
        return quality;
    }

    public MinMaxBounds.Doubles getYRange() {
        return yRange;
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

    public List<ResourceKey<Biome>> getBiomes() {
        return biomes;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public EntityType<? extends Mob> getFishEntityType() {
        return fishEntityType;
    }

    public boolean hasFishEntityType() {
        return fishEntityType != null;
    }

    public int getSpawningWeight() {
        return spawningWeight;
    }

    public Pair<Integer, Integer> getGroupSizes() {
        return groupSizes;
    }

//    public  RegistryEntryList<Biome> getEntryBiomes(RegistryWrapper<Biome> biomeRegistry) {
//        List<RegistryKey<Biome>> biomeKeys = getBiomes();
//        if (biomeKeys.isEmpty()) {
//            return RegistryEntryList.of();
//        }
//        return RegistryEntryList.of(
//                biomeKeys.stream()
//                        .map(biomeRegistry::getOrThrow)
//                        .toList()
//        );
//    }


}
