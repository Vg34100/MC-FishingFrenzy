package net.vg.fishingfrenzy.item.bait.targetbait;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public interface TargetBaitProperties {

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
}
