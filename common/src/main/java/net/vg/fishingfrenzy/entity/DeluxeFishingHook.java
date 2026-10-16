package net.vg.fishingfrenzy.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.FishingFrenzy;
import net.vg.fishingfrenzy.item.DeluxeFishingRodItem;
import net.vg.fishingfrenzy.item.bait.BaitItem;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.mixin.FishingHookAccessor;
import net.vg.fishingfrenzy.registry.ModItems;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

public class DeluxeFishingHook extends FishingHook {
    private final ItemStack baitStack;
    private final int luckBonus;

    public DeluxeFishingHook(EntityType<? extends FishingHook> type, Level level, int luckBonus, int waitTimeReductionTicks, ItemStack baitStack) {
        super(type, level);
        this.baitStack = baitStack.copyWithCount(1);
        this.luckBonus = luckBonus;
    }

    public DeluxeFishingHook(Player player, Level level, int luckBonus, int waitTimeReductionTicks, ItemStack baitStack) {
        super(player, level, luckBonus, waitTimeReductionTicks);
        this.baitStack = baitStack.copyWithCount(1);
        this.luckBonus = luckBonus;
    }

    private boolean removeIfInvalid(Player player) {
        ItemStack itemStack = player.getMainHandItem();
        ItemStack itemStack2 = player.getOffhandItem();
        boolean bl = itemStack.is(ModItems.DELUXE_FISHING_ROD.get());
        boolean bl2 = itemStack2.is(ModItems.DELUXE_FISHING_ROD.get());
        if (!player.isRemoved() && player.isAlive() && (bl || bl2) && !(this.distanceToSqr(player) > 1024.0)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    @Override
    public int retrieve(ItemStack usedItem) {
        Constants.LOGGER.debug("Retrieve");
        Player player = this.getPlayerOwner();
        if (this.level().isClientSide() || player == null || this.removeIfInvalid(player)) {
            return 0;
        }
        int i = 0;
        int hookCountdown = ((FishingHookAccessor) this).getNibble();
        if (this.getHookedIn() != null) {
            this.pullEntity(this.getHookedIn());
            i = this.getHookedIn() instanceof ItemEntity ? 3 : 5;
        } else if(hookCountdown > 0) {
            Constants.LOGGER.debug("luckBonus: {}, playerLuck: {}", this.luckBonus, player.getLuck());
//            LootContext.Builder lootContextBuilder = (new LootContext.Builder((ServerLevel)this.level()))
//                    .withParameter(LootContextParams.ORIGIN, this.position())
//                    .withParameter(LootContextParams.TOOL, usedItem)
//                    .withParameter(LootContextParams.THIS_ENTITY, this)
//                    .withLuck((float)this.luckBonus + player.getLuck());
            LootParams lootParams = (new LootParams.Builder((ServerLevel)this.level()))
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.TOOL, usedItem)
                    .withParameter(LootContextParams.THIS_ENTITY, this)
                    .withLuck((float)this.luckBonus + player.getLuck())
                    .create(LootContextParamSets.FISHING);


            LootTable lootTable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
            if (!baitStack.isEmpty() && baitStack.getItem() instanceof BaitItem baitProperties) {
                lootTable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(baitProperties.getLootTable());
                Constants.LOGGER.info("{}  {}", baitProperties.getMultiCatchAmount(), baitProperties.getMultiCatchChance());
            }


            List<ItemStack> list = lootTable.getRandomItems(lootParams);
            CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, usedItem, this, list);


            for (ItemStack itemStack : list) {
                double d = player.getX() - this.getX();
                double e = player.getY() - this.getY();
                double f = player.getZ() - this.getZ();

                int catches = 1;
                float multiCatchChance = 0.0f; // Default chance
                Item targetedFish = null;

                if (!baitStack.isEmpty() && baitStack.getItem() instanceof BaitItem baitProperties) {
                    catches += baitProperties.getMultiCatchAmount();
                    multiCatchChance = baitProperties.getMultiCatchChance();
                    targetedFish = baitProperties.getTargetedFish();
                }
                if (targetedFish != null) {
                    System.out.println(targetedFish);
                }

                for (int l = 0; l < catches; l++) {
                    if (l == 0 || this.random.nextFloat() < multiCatchChance) {
                        final int index = l;
                        ItemStack spawnStack;

                        if (targetedFish != null && this.random.nextFloat() < 0.8f) {
                            spawnStack = new ItemStack(targetedFish);
                        } else {
                            spawnStack = itemStack;
                        }

                        // Schedule the spawn with increasing delay
                        this.level().getServer().schedule(new TickTask(l * 5, () -> {
                            spawnCaughtItem(spawnStack, player, d, e, f, index);
                        }));

                    }
                }

                // Experience
                if (player.level() instanceof ServerLevel sl) sl.addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5, player.getZ() + 0.5, this.random.nextInt(6) + 1));
            }
            // One bait per successful retrieval, independent of loot/multi-catch count.
            if (!list.isEmpty() && !baitStack.isEmpty()) {
                ((DeluxeFishingRodItem) usedItem.getItem()).decrementBaitInBundle(usedItem, baitStack);
            }
            i = 1;
        }
        if (this.onGround()) {
            i = 2;
        }
        this.discard();
        return i;
    }

    private void spawnCaughtItem(ItemStack itemStack, Player player, double d, double e, double f, int index) {
        if (FishManager.FISH_REGISTRY_MAP.containsKey("cobblestone_fish")
                && itemStack.is(FishManager.FISH_REGISTRY_MAP.get("cobblestone_fish").getFishRegistry().get())) {
            itemStack = new ItemStack(Items.COBBLESTONE);
        }
        double v = Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08;

        // Add larger variation to spawn position
        double offsetX = (this.random.nextDouble() - 0.5);
        double offsetY = (this.random.nextDouble() - 0.5);
        double offsetZ = (this.random.nextDouble() - 0.5);

        ItemEntity itemEntity = new ItemEntity(this.level(),
                this.getX() + offsetX,
                this.getY() + offsetY,
                this.getZ() + offsetZ,
                itemStack.copy());  // Use copy() to ensure a new instance

        itemEntity.setDeltaMovement(
                d * 0.1 + offsetX * 0.05,
                e * 0.1 + v + offsetY * 0.05,
                f * 0.1 + offsetZ * 0.05
        );

        net.minecraft.world.entity.Entity spawnEntity = convertFishingRealEntity(itemEntity, player);
        if (this.level() instanceof ServerLevel sl && sl.addFreshEntity(spawnEntity)
                && itemStack.is(ItemTags.FISHES)) {
            player.awardStat(Stats.FISH_CAUGHT, itemStack.getCount());
        }

        Constants.LOGGER.info("Spawned item " + (index + 1) + ": " + itemStack.getItem().getName(itemStack).getString() + " at " + spawnEntity.position());
    }

    private Entity convertFishingRealEntity(ItemEntity itemEntity, Player player) {
        if (!FishingFrenzy.isFishingRealCompatEnabled()) {
            return itemEntity;
        }
        try {
            Class<?> fishingReal = Class.forName("koala.fishingreal.FishingReal");
            Method method;
            try {
                method = fishingReal.getMethod("convertItemEntity", Entity.class, Player.class);
            } catch (NoSuchMethodException ignored) {
                method = null;
                for (Method candidate : fishingReal.getDeclaredMethods()) {
                    if (!candidate.getName().equals("convertItemEntity")) {
                        continue;
                    }
                    Class<?>[] params = candidate.getParameterTypes();
                    if (params.length != 2) {
                        continue;
                    }
                    if (!Entity.class.isAssignableFrom(params[0]) || !Player.class.isAssignableFrom(params[1])) {
                        continue;
                    }
                    if (!Modifier.isStatic(candidate.getModifiers())) {
                        continue;
                    }
                    candidate.setAccessible(true);
                    method = candidate;
                    break;
                }
            }

            if (method != null) {
                Object converted = method.invoke(null, itemEntity, player);
                if (converted instanceof Entity entity) {
                    return entity;
                }
                Constants.LOGGER.debug("Fishing Real convertItemEntity returned non-Entity, using normal item entity.");
            } else {
                Constants.LOGGER.debug("Fishing Real convertItemEntity method not found, using normal item entity.");
            }
        } catch (Throwable throwable) {
            Constants.LOGGER.debug("Fishing Real compat failed, using normal caught item entity.", throwable);
        }
        return itemEntity;
    }
}
