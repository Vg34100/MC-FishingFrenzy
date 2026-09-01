package net.vg.fishingfrenzy.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.ItemLike;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.item.DeluxeFishingRodItem;
import net.vg.fishingfrenzy.item.bait.BaitItem;
import net.vg.fishingfrenzy.item.bait.BaitManager;
import net.vg.fishingfrenzy.item.bait.BaitPropertiesBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);

    public static final List<RegistrySupplier<Item>> FISHING_RODS = new ArrayList<>();
    public static final List<RegistrySupplier<Item>> BAIT_ITEMS = new ArrayList<>();

//    public static final RegistrySupplier<Item> DELUXE_FISHING_ROD = registerItem("deluxe_fishing_rod",
//            () -> new DeluxeFishingRodItem(new Item.Properties()
//                    .durability(64).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)),
//            FISHING_RODS);
    public static final RegistrySupplier<Item> DELUXE_FISHING_ROD = registerItem("deluxe_fishing_rod",
            () -> new DeluxeFishingRodItem(itemProperties("deluxe_fishing_rod")
                    .durability(64)
                    .enchantable(1)
                    .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)),
            FISHING_RODS);

//    public static final RegistrySupplier<Item> WORM_BAIT = registerItem("worm_bait",
//            () -> new BaitItem(new Item.Properties(), new BaitPropertiesBuilder()
//                    .setLuckBonus(1)
//                    .setLureBonus(2)
//                    .setMultiCatchAmount(20)
//                    .setMultiCatchChance(1.0f)),
//            BAIT_ITEMS);


    @SafeVarargs
    public static RegistrySupplier<Item> registerItem(String name, Supplier<Item> itemSupplier, List<RegistrySupplier<Item>>... optionalLists) {
        RegistrySupplier<Item> registeredItem = ITEMS.register(name, itemSupplier);
        for (List<RegistrySupplier<Item>> optionalList : optionalLists) {
            if (optionalList != null) {
                optionalList.add(registeredItem);
            }
        }
        return registeredItem;
    }

    public static Item.Properties itemProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, name)
        ));
    }

    public static void register() {
        Constants.LOGGER.info("Registering ModItems");
        BaitManager.registerBait();
        ITEMS.register();
    }
}
