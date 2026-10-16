package net.vg.fishingfrenzy.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.MobSpawnType;
import net.vg.fishingfrenzy.config.ModConfigs;
import net.vg.fishingfrenzy.world.ModEntitySpawns;
import net.vg.fishingfrenzy.item.bait.BaitItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.vg.fishingfrenzy.entity.DeluxeFishingHook;
import net.vg.fishingfrenzy.entity.mob.CustomBreedableSchoolingFishEntity;
import net.vg.fishingfrenzy.item.DeluxeFishingRodItem;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.item.fish.FishRegistry;
import net.vg.fishingfrenzy.registry.ModItems;
import net.vg.fishingfrenzy.mixin.FishingHookAccessor;

/** Exercises actual item interactions, registered entities and world ticks. */
public final class FishGameplayTests {
    private static final String ID = "fishingfrenzy_gameplay_tests";
    private FishGameplayTests() { }

    public static Collection<TestFunction> tests(Function<GameTestHelper, ServerPlayer> playerFactory) {
        List<TestFunction> tests = new ArrayList<>();
        if (Boolean.getBoolean("fishingfrenzy.gameplay.baitTimersOnly")) {
            add(tests, "bait_timers_and_catches", h -> baitTimersAndCatches(h, playerFactory));
            return tests;
        }
        for (String name : fishNames()) {
            add(tests, name + "_egg_on_land", h -> eggOnLand(h, registry(name)));
            add(tests, name + "_bucket_round_trip", h -> bucketRoundTrip(h, registry(name), playerFactory));
            add(tests, name + "_save_and_breed", h -> saveAndBreed(h, registry(name)));
            add(tests, name + "_bait_crafting", h -> baitCrafting(h, registry(name)));
        }
        add(tests, "rod_bait_storage_and_cast", FishGameplayTests::rodBaitStorageAndCast);
        add(tests, "breeding_rules", FishGameplayTests::breedingRules);
        add(tests, "spawn_rules", FishGameplayTests::spawnRules);
        add(tests, "rod_multiple_loot", h -> rodMultipleLoot(h, playerFactory));
        return tests;
    }

    private static List<String> fishNames() {
        var source = FishGameplayTests.class.getResourceAsStream("/fishingfrenzy-gameplay-fish.txt");
        if (source == null) throw new IllegalStateException("Missing gameplay test inventory");
        try (var reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8))) {
            return reader.lines().filter(s -> !s.isBlank()).toList();
        } catch (IOException error) {
            throw new UncheckedIOException(error);
        }
    }

    private static FishRegistry registry(String name) {
        return FishManager.FISH_REGISTRIES.stream().filter(f -> f.getFishName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalStateException("Fish not registered: " + name));
    }

    private static void add(List<TestFunction> tests, String name, Consumer<GameTestHelper> body) {
        String selected = System.getProperty("fishingfrenzy.gameplay.cases", "");
        if (!selected.isEmpty() && !List.of(selected.split(",")).contains(name)) return;
        tests.add(new TestFunction(ID, ID + ":" + name, ID + ":empty", 120, 0, true, body));
    }

    private static void floor(GameTestHelper h, boolean water) {
        for (int x = 0; x < 8; x++) for (int z = 0; z < 8; z++) {
            h.setBlock(new BlockPos(x, 0, z), Blocks.STONE);
            for (int y = 1; y <= 2; y++) {
                // Contain flopping fish; falling off a floating test platform
                // is unrelated to surviving the original land-spawn crash.
                h.setBlock(new BlockPos(x, y, z), x == 0 || x == 7 || z == 0 || z == 7
                        ? Blocks.STONE : water ? Blocks.WATER : Blocks.AIR);
            }
        }
    }

    private static AABB bounds(GameTestHelper h) {
        return new AABB(h.absolutePos(BlockPos.ZERO), h.absolutePos(new BlockPos(8, 4, 8)));
    }

    private static CustomBreedableSchoolingFishEntity useEgg(GameTestHelper h, FishRegistry fish) {
        Player player = h.makeMockSurvivalPlayer();
        ItemStack egg = new ItemStack(fish.getSpawnEggRegistry().get());
        player.setItemInHand(InteractionHand.MAIN_HAND, egg);
        BlockPos pos = h.absolutePos(new BlockPos(2, 0, 2));
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        h.assertTrue(egg.getItem().useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hit)).consumesAction(), "Egg placement failed");
        h.assertTrue(egg.isEmpty(), "Survival egg was not consumed");
        List<CustomBreedableSchoolingFishEntity> spawned = h.getLevel().getEntitiesOfClass(
                CustomBreedableSchoolingFishEntity.class, bounds(h), entity -> entity.getType() == fish.getFishEntityType().get());
        h.assertTrue(spawned.size() == 1, "Egg did not spawn exactly its registered fish");
        spawned.get(0).setPersistenceRequired();
        return spawned.get(0);
    }

    private static void eggOnLand(GameTestHelper h, FishRegistry registry) {
        floor(h, false);
        CustomBreedableSchoolingFishEntity fish = useEgg(h, registry);
        h.runAfterDelay(60, () -> {
            h.assertTrue(fish.isAlive() && fish.tickCount >= 40,
                    "Fish did not survive land ticks: alive=" + fish.isAlive()
                            + ", removal=" + fish.getRemovalReason() + ", ticks=" + fish.tickCount);
            h.succeed();
        });
    }

    private static void bucketRoundTrip(GameTestHelper h, FishRegistry registry,
                                       Function<GameTestHelper, ServerPlayer> playerFactory) {
        floor(h, true);
        CustomBreedableSchoolingFishEntity fish = useEgg(h, registry);
        fish.setCustomName(Component.literal("Bucket test"));
        fish.setHealth(2.0F);
        h.runAfterDelay(10, () -> {
            h.assertTrue(fish.isInWater() && fish.isAlive(), "Fish did not tick in water");
            // Bucket criteria require an actual ServerPlayer, including its
            // advancement manager, rather than GameTestHelper's plain Player.
            var player = playerFactory.apply(h);
            // The helper logs players in at world spawn. Put this player by
            // its test pool so distant fixtures do not naturally despawn.
            player.moveTo(Vec3.atCenterOf(h.absolutePos(new BlockPos(3, 3, 3))));
            player.getAbilities().instabuild = false;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
            h.assertTrue(fish.mobInteract(player, InteractionHand.MAIN_HAND).consumesAction(), "Bucket capture failed");
            ItemStack bucket = player.getMainHandItem();
            h.assertTrue(bucket.is(registry.getBucketItemRegistry().get()) && fish.isRemoved(), "Wrong bucket or fish not captured");
            ((MobBucketItem) bucket.getItem()).checkExtraContent(player, h.getLevel(), bucket, h.absolutePos(new BlockPos(4, 1, 4)));
            h.runAfterDelay(30, () -> {
                List<CustomBreedableSchoolingFishEntity> released = h.getLevel().getEntitiesOfClass(
                        CustomBreedableSchoolingFishEntity.class, bounds(h), entity -> entity.getType() == registry.getFishEntityType().get());
                h.assertTrue(released.size() == 1, "Bucket released the wrong fish count/type");
                CustomBreedableSchoolingFishEntity restored = released.get(0);
                h.assertTrue(restored.isAlive() && restored.fromBucket() && restored.getHealth() == 2.0F
                        && restored.getName().getString().equals("Bucket test"), "Bucket lost fish data or failed during ticking");
                var players = h.getLevel().getServer().getPlayerList();
                if (players.getPlayers().contains(player)) players.remove(player);
                h.succeed();
            });
        });
    }

    private static void saveAndBreed(GameTestHelper h, FishRegistry registry) {
        floor(h, true);
        CustomBreedableSchoolingFishEntity fish = useEgg(h, registry);
        fish.setBaby(true);
        CompoundTag saved = fish.saveWithoutId(new CompoundTag());
        CustomBreedableSchoolingFishEntity restored = registry.getFishEntityType().get().create(h.getLevel());
        h.assertTrue(restored != null, "Fish constructor returned null");
        restored.load(saved);
        h.assertTrue(restored.isBaby() && restored.getBreedingAge() == fish.getBreedingAge(), "Entity save/load lost baby state");
        // This saved-state clone is a second parent, with its own identity.
        restored.setUUID(UUID.randomUUID());
        fish.setBaby(false);
        restored.setBaby(false);
        restored.moveTo(h.absolutePos(new BlockPos(4, 1, 4)), 0, 0);
        h.assertTrue(h.getLevel().addFreshEntity(restored), "Restored parent could not enter the world");
        fish.breed(h.getLevel(), restored);
        h.getLevel().getEntitiesOfClass(CustomBreedableSchoolingFishEntity.class, bounds(h))
                .forEach(entity -> entity.setPersistenceRequired());
        h.runAfterDelay(30, () -> {
            List<CustomBreedableSchoolingFishEntity> children = h.getLevel().getEntitiesOfClass(
                    CustomBreedableSchoolingFishEntity.class, bounds(h), entity -> entity.getType() == registry.getFishEntityType().get() && entity.isBaby());
            h.assertTrue(children.size() == 1 && children.get(0).isAlive(), "Breeding did not produce a live baby of the same species");
            var child = children.get(0);
            h.assertTrue(child.getBreedingAge() > -24000 && fish.getBreedingAge() < 6000
                    && restored.getBreedingAge() < 6000, "Growth or breeding cooldown did not advance");
            fish.setBreedingAge(2);
            child.setBreedingAge(-2);
            h.runAfterDelay(4, () -> {
                h.assertTrue(fish.getBreedingAge() == 0 && child.getBreedingAge() == 0 && !child.isBaby(),
                        "Growth/cooldown did not finish naturally");
                h.succeed();
            });
        });
    }

    private static void breedingRules(GameTestHelper h) {
        var type = registry(fishNames().get(0)).getFishEntityType().get();
        var food = registry(fishNames().get(0));
        var fish = new CustomBreedableSchoolingFishEntity(type, h.getLevel(), food);
        var mate = type.create(h.getLevel());
        fish.lovePlayer(null);
        mate.lovePlayer(null);
        h.assertTrue(fish.canBreedWith(mate), "Same species with different implementation classes cannot breed");
        mate.setBreedingAge(1);
        h.assertTrue(!fish.canBreedWith(mate), "Fish can breed during cooldown");
        var other = registry(fishNames().get(1)).getFishEntityType().get().create(h.getLevel());
        other.lovePlayer(null);
        h.assertTrue(!fish.canBreedWith(other), "Different species can breed");
        withProperties(food, java.util.Map.of("breedingItem", Items.KELP), () ->
                h.assertTrue(fish.isBreedingItem(new ItemStack(Items.KELP))
                && !fish.isBreedingItem(new ItemStack(Items.SEAGRASS)), "Configured breeding food ignored"));
        fish.setBreedingAge(-100);
        fish.growUp(2, true);
        CompoundTag saved = fish.saveWithoutId(new CompoundTag());
        h.assertTrue(saved.getInt("Age") == -60 && saved.getInt("ForcedAge") == 40,
                "Feeding growth or forced-age persistence incorrect");
        var mirror = type.create(h.getLevel());
        mirror.getEntityData().assignValues(fish.getEntityData().packDirty());
        h.assertTrue(mirror.isBaby(), "Baby flag did not synchronize");
        fish.setBreedingAge(0);
        mirror.getEntityData().assignValues(fish.getEntityData().packDirty());
        h.assertTrue(!mirror.isBaby(), "Adult flag did not synchronize");
        h.succeed();
    }

    private static void withProperties(FishRegistry registry, java.util.Map<String, Object> values, Runnable check) {
        var originals = new java.util.HashMap<java.lang.reflect.Field, Object>();
        try {
            for (var entry : values.entrySet()) {
                var field = FishRegistry.class.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                originals.put(field, field.get(registry));
                field.set(registry, entry.getValue());
            }
            check.run();
        } catch (ReflectiveOperationException error) { throw new AssertionError(error); }
        finally {
            originals.forEach((field, value) -> {
                try { field.set(registry, value); }
                catch (IllegalAccessException error) { throw new AssertionError(error); }
            });
        }
    }

    private static void spawnRules(GameTestHelper h) {
        var level = h.getLevel();
        var type = registry(fishNames().get(0)).getFishEntityType().get();
        BlockPos at = new BlockPos(h.absolutePos(new BlockPos(2, 0, 2)).getX(),
                level.getSeaLevel() - 1, h.absolutePos(new BlockPos(2, 0, 2)).getZ());
        var originals = new java.util.HashMap<BlockPos, net.minecraft.world.level.block.state.BlockState>();
        long day = level.getDayTime();
        boolean easy = ModConfigs.EASY_MODE;
        boolean rain = level.isRaining(), thunder = level.isThundering();
        try {
            for (int x = -1; x <= 2; x++) for (int z = -1; z <= 2; z++) for (int y = -1; y <= 1; y++) {
                BlockPos p = at.offset(x, y, z);
                originals.put(p, level.getBlockState(p));
                level.setBlock(p, Blocks.WATER.defaultBlockState(), 2);
            }
            ModConfigs.EASY_MODE = false;
            var night = registry(fishNames().get(0));
            withProperties(night, java.util.Map.of("minTime", 13000, "maxTime", 23000,
                    "yRange", net.minecraft.advancements.critereon.MinMaxBounds.Doubles.ANY, "isWeatherDependent", false), () -> {
            level.getServer().getCommands().performPrefixedCommand(
                    level.getServer().createCommandSourceStack().withSuppressedOutput(), "time set day");
            h.assertTrue("time".equals(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, night)), "Day command did not block night fish");
            level.getServer().getCommands().performPrefixedCommand(
                    level.getServer().createCommandSourceStack().withSuppressedOutput(), "time set night");
            h.assertTrue(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, night) == null, "Night command did not allow night fish");
            level.setDayTime(level.getDayTime() + 24000);
            h.assertTrue(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, night) == null, "Next-day clock changed the time window");
            var wet = night;
            withProperties(wet, java.util.Map.of("minTime", 0, "maxTime", 24000,
                    "isWeatherDependent", true, "raining", true, "thundering", false), () -> {
            level.setWeatherParameters(6000, 0, false, false);
            level.setRainLevel(0);
            level.setThunderLevel(0);
            h.assertTrue("rain".equals(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, wet)), "Rain restriction ignored");
            level.setWeatherParameters(0, 6000, true, false);
            level.setRainLevel(1);
            h.assertTrue(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, wet) == null, "Rain fish rejected in rain");
            });
            ModConfigs.EASY_MODE = true;
            level.setDayTime(1000);
            h.assertTrue(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, night) == null, "Easy mode retained time restrictions");
            for (BlockPos p : originals.keySet()) if (p.getX() != at.getX() || p.getZ() != at.getZ()) level.setBlock(p, Blocks.AIR.defaultBlockState(), 2);
            h.assertTrue("connected_water".equals(ModEntitySpawns.getSpawnRuleFailure(type, level, MobSpawnType.NATURAL, at, level.random, night)), "Easy mode allowed a tiny water column");
            });
        } finally {
            originals.forEach((p, state) -> level.setBlock(p, state, 2));
            level.setDayTime(day);
            level.setWeatherParameters(0, 6000, rain, thunder);
            level.setRainLevel(rain ? 1 : 0);
            level.setThunderLevel(thunder ? 1 : 0);
            ModConfigs.EASY_MODE = easy;
        }
        h.succeed();
    }

    private static void rodMultipleLoot(GameTestHelper h, Function<GameTestHelper, ServerPlayer> factory) {
        floor(h, true);
        var player = factory.apply(h);
        player.moveTo(Vec3.atCenterOf(h.absolutePos(new BlockPos(2, 3, 2))));
        ItemStack rod = new ItemStack(ModItems.DELUXE_FISHING_ROD.get());
        var item = (DeluxeFishingRodItem) rod.getItem();
        ItemStack worms = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation("fishingfrenzy", "worm_bait")), 3);
        worms.setHoverName(Component.literal("Keep my name"));
        var bait = worms.getItem();
        var castBait = worms.copyWithCount(1);
        var inventory = new SimpleContainer(1);
        var slot = new Slot(inventory, 0, 0, 0);
        inventory.setItem(0, worms);
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        player.setItemInHand(InteractionHand.MAIN_HAND, rod);
        int before = player.getStats().getValue(Stats.CUSTOM.get(Stats.FISH_CAUGHT));
        try {
            var loot = BaitItem.class.getDeclaredField("lootTable");
            loot.setAccessible(true);
            Object original = loot.get(bait);
            try {
                loot.set(bait, new ResourceLocation(ID, "two_fish"));
                item.use(h.getLevel(), player, InteractionHand.MAIN_HAND);
                var nibble = FishingHook.class.getDeclaredField("nibble");
                nibble.setAccessible(true);
                nibble.setInt(player.fishing, 20);
                item.use(h.getLevel(), player, InteractionHand.MAIN_HAND);
            } finally {
                loot.set(bait, original);
            }
        } catch (ReflectiveOperationException error) { throw new AssertionError(error); }
        inventory.setItem(0, ItemStack.EMPTY);
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        h.assertTrue(inventory.getItem(0).getCount() == 2
                && inventory.getItem(0).getHoverName().getString().equals("Keep my name"), "Multiple loot consumed extra bait or lost stored data");
        // A cast must not consume replacement bait with different stored data.
        ItemStack replacement = inventory.getItem(0).copy();
        replacement.setHoverName(Component.literal("Replacement"));
        inventory.setItem(0, replacement);
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        item.decrementBaitInBundle(rod, castBait);
        inventory.setItem(0, ItemStack.EMPTY);
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        h.assertTrue(inventory.getItem(0).getCount() == 2, "Retrieval consumed replacement bait");
        h.runAfterDelay(3, () -> {
            h.assertTrue(player.getStats().getValue(Stats.CUSTOM.get(Stats.FISH_CAUGHT)) - before == 2,
                    "Caught-fish statistic did not count both produced fish");
            var players = h.getLevel().getServer().getPlayerList();
            if (players.getPlayers().contains(player)) players.remove(player);
            h.succeed();
        });
    }

    private static void baitCrafting(GameTestHelper h, FishRegistry fish) {
        AbstractContainerMenu menu = new AbstractContainerMenu(null, 0) {
            @Override public ItemStack quickMoveStack(Player player, int slot) { return ItemStack.EMPTY; }
            @Override public boolean stillValid(Player player) { return true; }
        };
        TransientCraftingContainer grid = new TransientCraftingContainer(menu, 2, 2);
        grid.setItem(0, new ItemStack(fish.getFishRegistry().get()));
        var recipe = h.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, grid, h.getLevel());
        h.assertTrue(recipe.isPresent(), "Missing fish-to-bait crafting recipe");
        ItemStack result = recipe.get().assemble(grid, h.getLevel().registryAccess());
        h.assertTrue(result.is(fish.getTargetBaitRegistry().get()) && result.getCount() == 2, "One fish must craft two matching baits");
        h.succeed();
    }

    private static void rodBaitStorageAndCast(GameTestHelper h) {
        h.assertTrue(FishManager.FISH_REGISTRIES.size() == fishNames().size(), "Test inventory does not cover every registered fish");
        floor(h, true);
        Player player = h.makeMockSurvivalPlayer();
        player.moveTo(Vec3.atCenterOf(h.absolutePos(new BlockPos(2, 3, 2))));
        ItemStack rod = new ItemStack(ModItems.DELUXE_FISHING_ROD.get());
        DeluxeFishingRodItem item = (DeluxeFishingRodItem) rod.getItem();
        SimpleContainer inventory = new SimpleContainer(1);
        Slot slot = new Slot(inventory, 0, 0, 0);
        inventory.setItem(0, new ItemStack(Items.STONE, 32));
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        h.assertTrue(inventory.getItem(0).getCount() == 32 && !item.isBarVisible(rod), "Rod accepted a non-bait item");
        ItemStack worms = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation("fishingfrenzy", "worm_bait")), 64);
        worms.setHoverName(Component.literal("Stored bait"));
        inventory.setItem(0, worms);
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        h.assertTrue(inventory.getItem(0).isEmpty() && item.getBarWidth(rod) == 13, "Rod did not store a full bait stack");
        inventory.setItem(0, new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation("fishingfrenzy", "worm_bait")), 12));
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        h.assertTrue(inventory.getItem(0).getCount() == 12, "Rod exceeded bait capacity");
        rod = ItemStack.of(rod.save(new CompoundTag()));
        player.setItemInHand(InteractionHand.MAIN_HAND, rod);
        item.use(h.getLevel(), player, InteractionHand.MAIN_HAND);
        h.assertTrue(player.fishing instanceof DeluxeFishingHook, "Casting did not create the custom fishing hook");
        item.use(h.getLevel(), player, InteractionHand.MAIN_HAND);
        h.assertTrue(player.fishing == null, "Retrieving did not remove the hook");
        item.decrementBaitInBundle(rod);
        inventory.setItem(0, ItemStack.EMPTY);
        item.overrideStackedOnOther(rod, slot, ClickAction.SECONDARY, player);
        h.assertTrue(inventory.getItem(0).getCount() == 63 && inventory.getItem(0).getHoverName().getString().equals("Stored bait"), "Bait consumption/save/load/removal lost count or data");
        h.assertTrue(!item.isBarVisible(rod), "Rod did not empty after removing bait");
        h.succeed();
    }

    private static void baitTimersAndCatches(GameTestHelper h,
                                            Function<GameTestHelper, ServerPlayer> playerFactory) {
        floor(h, true);
        var player = playerFactory.apply(h);
        player.moveTo(Vec3.atCenterOf(h.absolutePos(new BlockPos(2, 3, 2))));
        player.getAbilities().instabuild = false;
        List<ItemStack> baits = new ArrayList<>();
        baits.add(ItemStack.EMPTY); // Vanilla Lure behavior, without bait.
        for (var bait : ModItems.BAIT_ITEMS) baits.add(new ItemStack(bait.get()));
        for (var fish : FishManager.FISH_REGISTRIES) {
            baits.add(new ItemStack(fish.getTargetBaitRegistry().get()));
        }
        h.assertTrue(baits.size() == FishManager.FISH_REGISTRIES.size() + 7,
                "Fishing check must cover every general and targeted bait");
        checkBaitCatch(h, player, baits, 0);
    }

    private static void checkBaitCatch(GameTestHelper h, ServerPlayer player,
                                      List<ItemStack> baits, int index) {
        if (index == baits.size() * 2) {
            var players = h.getLevel().getServer().getPlayerList();
            if (players.getPlayers().contains(player)) players.remove(player);
            h.succeed();
            return;
        }
        h.getLevel().getEntitiesOfClass(ItemEntity.class, bounds(h)).forEach(ItemEntity::discard);
        player.getInventory().clearContent();
        ItemStack bait = baits.get(index / 2).copy();
        int lureLevel = index % 2 == 0 ? 0 : 3;
        String context = (bait.isEmpty() ? "no bait" : bait.getHoverName().getString()) + ", Lure " + lureLevel;
        ItemStack rod = new ItemStack(ModItems.DELUXE_FISHING_ROD.get());
        if (lureLevel != 0) rod.enchant(Enchantments.FISHING_SPEED, lureLevel);
        DeluxeFishingRodItem item = (DeluxeFishingRodItem) rod.getItem();
        if (!bait.isEmpty()) {
            SimpleContainer inventory = new SimpleContainer(1);
            inventory.setItem(0, bait);
            item.overrideStackedOnOther(rod, new Slot(inventory, 0, 0, 0), ClickAction.SECONDARY, player);
            h.assertTrue(inventory.getItem(0).isEmpty(), "Bait insertion failed: " + context);
        }
        player.setItemInHand(InteractionHand.MAIN_HAND, rod);
        item.use(h.getLevel(), player, InteractionHand.MAIN_HAND);
        h.assertTrue(player.fishing instanceof DeluxeFishingHook, "Cast failed: " + context);
        var hook = player.fishing;
        hook.moveTo(Vec3.atCenterOf(h.absolutePos(new BlockPos(4, 2, 4))));
        hook.setDeltaMovement(Vec3.ZERO);
        // Advance the actual hook, including vanilla's wait/bite state machine.
        // This avoids waiting through each cast in real time or forcing a bite.
        for (int tick = 0; tick < 1600 && hook.isAlive()
                && ((FishingHookAccessor) hook).getNibble() == 0; tick++) hook.tick();
        h.assertTrue(hook.isAlive() && ((FishingHookAccessor) hook).getNibble() > 0,
                "No bite; fishing timer stalled: " + context);
        item.use(h.getLevel(), player, InteractionHand.MAIN_HAND);
        h.runAfterDelay(1, () -> {
            boolean caught = !h.getLevel().getEntitiesOfClass(ItemEntity.class, bounds(h)).isEmpty()
                    || player.getInventory().items.stream().anyMatch(stack -> !stack.isEmpty()
                        && !stack.is(ModItems.DELUXE_FISHING_ROD.get()));
            h.assertTrue(caught, "Bite produced no caught item: " + context);
            h.assertTrue(!item.isBarVisible(rod), "Catch did not consume bait: " + context);
            checkBaitCatch(h, player, baits, index + 1);
        });
    }
}
