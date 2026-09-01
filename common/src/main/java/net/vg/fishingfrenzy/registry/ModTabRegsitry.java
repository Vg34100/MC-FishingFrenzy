package net.vg.fishingfrenzy.registry;

import dev.architectury.platform.Mod;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.item.fish.FishManager;

public class ModTabRegsitry {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Constants.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> FISHING_FRENZY_GROUP = TABS.register(
            "fishing_frenzy_all",
            () -> CreativeTabRegistry.create(builder -> {
                builder.title(Component.translatable("category.fishing_frenzy_all"));
                builder.icon(() -> new ItemStack(Items.FISHING_ROD));
            })
    );

    public static final RegistrySupplier<CreativeModeTab> FISHES_GROUP = TABS.register(
            "fishing_frenzy_fishes",
            () -> CreativeTabRegistry.create(builder -> {
                builder.title(Component.translatable("category.fishing_frenzy_fishes"));
                builder.icon(() -> new ItemStack(Items.COD));
            })
    );

    public static final RegistrySupplier<CreativeModeTab> BAIT_GROUP = TABS.register(
            "fishing_frenzy_bait",
            () -> CreativeTabRegistry.create(builder -> {
                builder.title(Component.translatable("category.fishing_frenzy_bait"));
                builder.icon(() -> new ItemStack(Items.STICK));
            })
    );

//    public static final RegistrySupplier<CreativeModeTab> BAIT_GROUP = TABS.register(
//            "fishing_frenzy_bait", // Tab ID
//            () -> CreativeTabRegistry.create(
//                    Component.translatable("category.fishing_frenzy_bait"), // Tab Name
//                    () -> new ItemStack(ModItems.WORM_BAIT) // Icon
//            )
//    );

//    public static final RegistrySupplier<CreativeModeTab> FISHES_SPAWN_GROUP = TABS.register(
//            "fishing_frenzy_fish_spawn_eggs", // Tab ID
//            () -> CreativeTabRegistry.create(
//                    Component.translatable("category.fishing_frenzy_fish_spawn_eggs"), // Tab Name
//                    () -> new ItemStack(Items.SALMON_SPAWN_EGG) // Icon
//            )
//    );

    public static void register() {
        TABS.register();
        appendAll(FISHING_FRENZY_GROUP, ModItems.FISHING_RODS);
        appendAll(FISHING_FRENZY_GROUP, ModItems.BAIT_ITEMS);
        appendAll(FISHING_FRENZY_GROUP, FishManager.getItemsByType(FishManager.ItemType.FISH));
        appendAll(FISHING_FRENZY_GROUP, FishManager.getItemsByType(FishManager.ItemType.COOKED_FISH));
        appendAll(FISHING_FRENZY_GROUP, FishManager.getItemsByType(FishManager.ItemType.BUCKET));
        appendAll(FISHING_FRENZY_GROUP, FishManager.getItemsByType(FishManager.ItemType.SPAWN_EGG));
        appendAll(FISHING_FRENZY_GROUP, FishManager.getItemsByType(FishManager.ItemType.BAIT));

        appendAll(FISHES_GROUP, FishManager.getItemsByType(FishManager.ItemType.FISH));
        appendAll(FISHES_GROUP, FishManager.getItemsByType(FishManager.ItemType.COOKED_FISH));
        appendAll(FISHES_GROUP, FishManager.getItemsByType(FishManager.ItemType.BUCKET));

        appendAll(BAIT_GROUP, ModItems.BAIT_ITEMS);
        appendAll(BAIT_GROUP, FishManager.getItemsByType(FishManager.ItemType.BAIT));
    }

    private static <T extends net.minecraft.world.level.ItemLike> void appendAll(
            RegistrySupplier<CreativeModeTab> tab,
            Iterable<? extends RegistrySupplier<T>> items
    ) {
        for (RegistrySupplier<T> item : items) {
            CreativeTabRegistry.append(tab, item);
        }
    }
}
