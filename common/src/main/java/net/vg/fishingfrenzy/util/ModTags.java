package net.vg.fishingfrenzy.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vg.fishingfrenzy.Constants;

public class ModTags {

    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> BAIT =
                createTag("bait");

        public static final TagKey<Item> TARGET_BAIT =
                createTag("target_bait");


        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }
}
