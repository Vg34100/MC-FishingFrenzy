package net.vg.fishingfrenzy.item.bait;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.function.Supplier;

public class BaitPropertiesBuilder implements BaitProperties {
    private int lureBonus = 0;
    private int luckBonus = 0;
    private float useChance = 1.0f;
    private int multiCatchAmount = 0;
    private float multiCatchChance = 0.0f;
    private ResourceKey<LootTable> lootTable = BuiltInLootTables.FISHING;
    private Supplier<Item> targetedFishSupplier = () -> null;


    public BaitPropertiesBuilder setLureBonus(int lureBonus) {
        this.lureBonus = lureBonus;
        return this;
    }

    public BaitPropertiesBuilder setLuckBonus(int luckBonus) {
        this.luckBonus = luckBonus;
        return this;
    }

    public BaitPropertiesBuilder setUseChance(float useChance) {
        this.useChance = useChance;
        return this;
    }

    public BaitPropertiesBuilder setMultiCatchAmount(int multiCatchAmount) {
        this.multiCatchAmount = multiCatchAmount;
        return this;
    }

    public BaitPropertiesBuilder setMultiCatchChance(float multiCatchChance) {
        this.multiCatchChance = multiCatchChance;
        return this;
    }

    public BaitPropertiesBuilder setLootTable(ResourceKey<LootTable> lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    public BaitPropertiesBuilder setTargetedFish(Item targetedFish) {
        this.targetedFishSupplier = () -> targetedFish;
        return this;
    }

    public BaitPropertiesBuilder setTargetedFishSupplier(Supplier<Item> targetedFishSupplier) {
        this.targetedFishSupplier = targetedFishSupplier;
        return this;
    }




    @Override
    public int getLureBonus() {
        return lureBonus;
    }

    @Override
    public int getLuckBonus() {
        return luckBonus;
    }

    @Override
    public float getUseChance() {
        return useChance;
    }

    @Override
    public int getMultiCatchAmount() {
        return multiCatchAmount;
    }

    @Override
    public float getMultiCatchChance() {
        return multiCatchChance;
    }

    @Override
    public ResourceKey<LootTable> getLootTable() {
        return lootTable;
    }

    @Override
    public Item getTargetedFish() { return targetedFishSupplier.get(); }

    @Override
    public Supplier<Item> getTargetedFishSupplier() { return targetedFishSupplier; }
}
