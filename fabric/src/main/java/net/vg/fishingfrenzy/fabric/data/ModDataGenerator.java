package net.vg.fishingfrenzy.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.impl.resource.pack.FabricPack;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.registry.ModItems;
import net.vg.fishingfrenzy.util.ModTags;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    private static boolean fishDataInitialized = false;

    private static void ensureFishDataInitialized() {
        if (!fishDataInitialized) {
            FishManager.registerAllFish();
            fishDataInitialized = true;
        }
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModModelGenerator::new);

        pack.addProvider(ModItemTagProvider::new);

        pack.addProvider(ModRecipeProvider::new);

        pack.addProvider(ModLangProvider::new);
    }

    public static class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
        public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }



        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            ensureFishDataInitialized();
            valueLookupBuilder(ItemTags.FISHING_ENCHANTABLE)
                    .add(ModItems.DELUXE_FISHING_ROD.get());
            ModItems.BAIT_ITEMS.forEach(bait ->
                    valueLookupBuilder(ModTags.Items.BAIT).add(bait.get()));

            FishManager.getItemsByType(FishManager.ItemType.FISH).forEach(fish ->
                    valueLookupBuilder(ItemTags.FISHES).add(fish.get()));
            FishManager.getItemsByType(FishManager.ItemType.COOKED_FISH).forEach(cookedfish ->
                    valueLookupBuilder(ItemTags.FISHES).add(cookedfish.get()));
        }
    }

    public static class ModLangProvider extends FabricLanguageProvider {
        protected ModLangProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            ensureFishDataInitialized();
            // Item groups
            translationBuilder.add("category.fishing_frenzy_all", "Fishing Frenzy");
            translationBuilder.add("category.fishing_frenzy_fishes", "Frenzy Fishes");
            translationBuilder.add("category.fishing_frenzy_bait", "Frenzy Baits");
            translationBuilder.add("category.fishing_frenzy_fish_spawn_eggs", "Frenzy Spawn Eggs");

            // Configuration screens
            translationBuilder.add("config.client.title", "Client Settings");
            translationBuilder.add("config.server.title", "Server Settings");
            translationBuilder.add("config.general.title", "Fishing Frenzy Settings");

            // Tags
            translationBuilder.add("tag.item.fishingfrenzy.bait", "Bait");
            translationBuilder.add("tag.item.fishingfrenzy.target_bait", "Targeted Bait");

            ModItems.FISHING_RODS.forEach(fishing_rod -> {
                String rodName = BuiltInRegistries.ITEM.getKey(fishing_rod.get()).getPath();
                translationBuilder.add(fishing_rod.get().getDescriptionId(), capitalize(rodName.replace("_", " ")));
            });

            ModItems.BAIT_ITEMS.forEach(bait -> {
                String baitName = BuiltInRegistries.ITEM.getKey(bait.get()).getPath();
                translationBuilder.add(bait.get().getDescriptionId(), capitalize(baitName.replace("_", " ")));
            });

            FishManager.getItemsByType(FishManager.ItemType.FISH).forEach(fish -> {
                String fishName = BuiltInRegistries.ITEM.getKey(fish.get()).getPath();
                translationBuilder.add(fish.get().getDescriptionId(), capitalize(fishName.replace("_", " ")));

            });
            FishManager.getItemsByType(FishManager.ItemType.COOKED_FISH).forEach(cookedFish -> {
                String cookedFishName = BuiltInRegistries.ITEM.getKey(cookedFish.get()).getPath();
                translationBuilder.add(cookedFish.get().getDescriptionId(), capitalize(cookedFishName.replace("_", " ")));
            });
            FishManager.getItemsByType(FishManager.ItemType.BAIT).forEach(bait -> {
                String baitName = BuiltInRegistries.ITEM.getKey(bait.get()).getPath();
                translationBuilder.add(bait.get().getDescriptionId(), capitalize(baitName.replace("_", " ")));

            });
            FishManager.getItemsByType(FishManager.ItemType.SPAWN_EGG).forEach(egg -> {
                String eggName = BuiltInRegistries.ITEM.getKey(egg.get()).getPath();
                translationBuilder.add(egg.get().getDescriptionId(), capitalize(eggName.replace("_", " ")));
            });
        }
    }


    private static class ModModelGenerator extends FabricModelProvider {

        public ModModelGenerator(FabricPackOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            ensureFishDataInitialized();
            // Baits
            ModItems.BAIT_ITEMS.forEach(bait -> itemModelGenerator.generateFlatItem(bait.get(), ModelTemplates.FLAT_ITEM));

            // Fishes
            FishManager.getItemsByType(FishManager.ItemType.FISH).forEach(fish -> itemModelGenerator.generateFlatItem(fish.get(), ModelTemplates.FLAT_ITEM));
            FishManager.getItemsByType(FishManager.ItemType.COOKED_FISH).forEach(cookedFish -> itemModelGenerator.generateFlatItem(cookedFish.get(), ModelTemplates.FLAT_ITEM));
            FishManager.getItemsByType(FishManager.ItemType.SPAWN_EGG).forEach(spawnEgg -> itemModelGenerator.generateFlatItem(spawnEgg.get(), ModelTemplates.FLAT_ITEM));
            FishManager.getItemsByType(FishManager.ItemType.BUCKET).forEach(bucket -> itemModelGenerator.generateFlatItem(bucket.get(), ModelTemplates.FLAT_ITEM));

            ModelTemplate baitModel = new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "item/template_target_bait")), Optional.empty());
            FishManager.getItemsByType(FishManager.ItemType.BAIT).forEach(bait -> itemModelGenerator.generateFlatItem(bait.get(), baitModel));
        }
    }

    public static class ModRecipeProvider extends FabricRecipeProvider {

        public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new RecipeProvider(registries, output) {
                @Override
                public void buildRecipes() {
                    ensureFishDataInitialized();

                    this.shaped(RecipeCategory.TOOLS, ModItems.DELUXE_FISHING_ROD.get())
                            .pattern("  i")
                            .pattern(" is")
                            .pattern("i s")
                            .define('i', Items.IRON_INGOT)
                            .define('s', Items.STRING)
                            .unlockedBy("has_iron_ingot", this.has(Items.IRON_INGOT))
                            .unlockedBy("has_string", this.has(Items.STRING))
                            .save(this.output);

                    this.shapeless(RecipeCategory.MISC, ModItems.BAIT_ITEMS.get(0).get(), 4)
                            .requires(Items.DIRT)
                            .requires(Items.STRING)
                            .requires(ItemTags.LEAVES)
                            .unlockedBy("has_dirt", this.has(Items.DIRT))
                            .save(this.output, ResourceKey.create(
                                    Registries.RECIPE,
                                    Identifier.fromNamespaceAndPath(Constants.MOD_ID, "worm_bait")
                            ));

                    this.shapeless(RecipeCategory.MISC, ModItems.BAIT_ITEMS.get(1).get(), 2)
                            .requires(ModItems.BAIT_ITEMS.get(0).get())
                            .requires(Items.GOLD_NUGGET)
                            .requires(Items.GOLD_NUGGET)
                            .requires(ItemTags.FLOWERS)
                            .unlockedBy("has_worm_bait", this.has(ModItems.BAIT_ITEMS.get(0).get()))
                            .save(this.output, ResourceKey.create(
                                    Registries.RECIPE,
                                    Identifier.fromNamespaceAndPath(Constants.MOD_ID, "deluxe_bait")
                            ));

                    FishManager.FISH_REGISTRIES.forEach(fishRegistry -> {
                        if (fishRegistry.getCookedFishRegistry() == null) {
                            return;
                        }
                        this.oreSmelting(
                                List.of(fishRegistry.getFishRegistry().get()),
                                RecipeCategory.FOOD,
                                CookingBookCategory.FOOD,
                                fishRegistry.getCookedFishRegistry().get(),
                                0.35F,
                                200,
                                fishRegistry.getFishName()
                        );

                        this.simpleCookingRecipe(
                                "smoking",
                                SmokingRecipe::new,
                                100,
                                fishRegistry.getFishRegistry().get(),
                                fishRegistry.getCookedFishRegistry().get(),
                                0.35F
                        );

                        this.simpleCookingRecipe(
                                "campfire_cooking",
                                CampfireCookingRecipe::new,
                                600,
                                fishRegistry.getFishRegistry().get(),
                                fishRegistry.getCookedFishRegistry().get(),
                                0.35F
                        );
                    });
                }
            };
        }
////        OLD METHOD
//        @Override
//        public void buildRecipes(RecipeOutput exporter) {
//            ensureFishDataInitialized();
//            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DELUXE_FISHING_ROD.get())
//                    .pattern("  i")
//                    .pattern(" is")
//                    .pattern("i s")
//                    .define('i', Items.IRON_INGOT)
//                    .define('s', Items.STRING)
//                    .unlockedBy("has_iron_ingot", RecipeProvider.has(Items.IRON_INGOT))
//                    .unlockedBy("has_string", RecipeProvider.has(Items.STRING))
//                    .save(exporter);
//
//            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BAIT_ITEMS.get(0).get(), 4)
//                    .requires(Items.DIRT)
//                    .requires(Items.STRING)
//                    .requires(ItemTags.LEAVES)
//                    .unlockedBy("has_dirt", RecipeProvider.has(Items.DIRT))
//                    .save(exporter, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "worm_bait"));
//
//            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BAIT_ITEMS.get(1).get(), 2)
//                    .requires(ModItems.BAIT_ITEMS.get(0).get())
//                    .requires(Items.GOLD_NUGGET)
//                    .requires(Items.GOLD_NUGGET)
//                    .requires(ItemTags.FLOWERS)
//                    .unlockedBy("has_worm_bait", RecipeProvider.has(ModItems.BAIT_ITEMS.get(0).get()))
//                    .save(exporter, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "deluxe_bait"));
//
//            FishManager.FISH_REGISTRIES.forEach(fishRegistry -> {
//                RecipeProvider.oreSmelting(exporter, List.of(fishRegistry.getFishRegistry().get()), RecipeCategory.FOOD, fishRegistry.getCookedFishRegistry().get(), 0.35F, 200, fishRegistry.getFishName());
//                RecipeProvider.simpleCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, fishRegistry.getFishRegistry().get(), fishRegistry.getCookedFishRegistry().get(), 0.35F);
//                RecipeProvider.simpleCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, fishRegistry.getFishRegistry().get(), fishRegistry.getCookedFishRegistry().get(), 0.35F);
//            });
//        }

        @Override
        public String getName() {
            return "";
        }
    }

    public static String capitalize(String str) {
        if (str.isEmpty()) {
            return str;
        }
        String[] words = str.split(" ");
        StringBuilder capitalizedWords = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedWords.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return capitalizedWords.toString().trim();
    }
}
