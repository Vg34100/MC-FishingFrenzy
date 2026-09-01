package net.vg.fishingfrenzy.item.bait;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.vg.fishingfrenzy.registry.ModItems;

public class BaitManager {
    private static final ResourceKey<LootTable> MAGNET_FISHING_LOOT = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("fishingfrenzy", "gameplay/magnet_fishing"));
    public static void registerBait() {
        RegistrySupplier<Item> WORM_BAIT = ModItems.registerItem("worm_bait",
                () -> new BaitItem(ModItems.itemProperties("worm_bait"), new BaitPropertiesBuilder()
                        .setLuckBonus(1)
                        .setLureBonus(2)),
                ModItems.BAIT_ITEMS);

        // Overall better
        RegistrySupplier<Item> DELUXE_BAIT = ModItems.registerItem("deluxe_bait",
                () -> new BaitItem(ModItems.itemProperties("deluxe_bait"), new BaitPropertiesBuilder()
                        .setLureBonus(10)
                        .setLuckBonus(3)
                        .setMultiCatchAmount(2)
                        .setMultiCatchChance(0.2f)),
                ModItems.BAIT_ITEMS);

        // Double the fish
        RegistrySupplier<Item> WILD_BAIT = ModItems.registerItem("wild_bait",
                () -> new BaitItem(ModItems.itemProperties("wild_bait"), new BaitPropertiesBuilder()
                        .setLureBonus(8)
                        .setMultiCatchAmount(1)
                        .setMultiCatchChance(0.9f)),
                ModItems.BAIT_ITEMS);

        // Double the fish
        RegistrySupplier<Item> MAGIC_BAIT = ModItems.registerItem("magic_bait",
                () -> new BaitItem(ModItems.itemProperties("magic_bait"), new BaitPropertiesBuilder()
                        .setLureBonus(8)
                        .setMultiCatchAmount(9)
                        .setMultiCatchChance(0.2f)),
                ModItems.BAIT_ITEMS);

        // Treasure greater chance
        RegistrySupplier<Item> MAGNET = ModItems.registerItem("magnet",
                () -> new BaitItem(ModItems.itemProperties("magnet"), new BaitPropertiesBuilder()
                        .setLootTable(MAGNET_FISHING_LOOT)
                        .setMultiCatchAmount(1)
                        .setMultiCatchChance(0.05f)),
                ModItems.BAIT_ITEMS);

        // Small Chance for 4 fish
        RegistrySupplier<Item> CHALLENGE_BAIT = ModItems.registerItem("challenge_bait",
                () -> new BaitItem(ModItems.itemProperties("challenge_bait"), new BaitPropertiesBuilder()
                        .setLuckBonus(4)
                        .setLureBonus(2)
                        .setMultiCatchAmount(3)
                        .setMultiCatchChance(0.1f)),
                ModItems.BAIT_ITEMS);
    }
}
