package net.vg.fishingfrenzy.item.bait;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.function.Supplier;

public interface BaitProperties {

    default int getLureBonus() {
        return 0;
    }

    default int getLuckBonus() {
        return 0;
    }

    default float getUseChance() {
        return 1.0f;
    }

    default int getMultiCatchAmount() {
        return 0;
    }

    default float getMultiCatchChance() {
        return 0.0f;
    }

    default ResourceKey<LootTable> getLootTable() {
        return BuiltInLootTables.FISHING;
    }

    default Item getTargetedFish() { return null; }

    default Supplier<Item> getTargetedFishSupplier() {
        return this::getTargetedFish;
    }
}
