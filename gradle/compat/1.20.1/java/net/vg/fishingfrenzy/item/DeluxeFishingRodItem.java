package net.vg.fishingfrenzy.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.vg.fishingfrenzy.entity.DeluxeFishingHook;
import net.vg.fishingfrenzy.item.bait.BaitItem;
import net.vg.fishingfrenzy.item.bait.BaitProperties;
import org.jetbrains.annotations.Nullable;

/** 1.20.1's NBT equivalent of the shared rod's bundle-component storage. */
public class DeluxeFishingRodItem extends FishingRodItem {
    private static final int CAPACITY = 64;
    private static final String CONTENTS = "Items";

    public DeluxeFishingRodItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack rod = player.getItemInHand(hand);
        if (player.fishing != null) {
            if (!level.isClientSide()) {
                int damage = player.fishing.retrieve(rod);
                rod.hurtAndBreak(damage, player, owner -> owner.broadcastBreakEvent(hand));
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE,
                    SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW,
                    SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide()) {
                ItemStack bait = contents(rod).stream().filter(stack -> stack.getItem() instanceof BaitItem)
                        .findFirst().orElse(ItemStack.EMPTY);
                // The shared hook API accepts ticks; 1.20.1 returns Lure levels
                // (five seconds each). Bait bonuses already use ticks.
                int lure = EnchantmentHelper.getFishingSpeedBonus(rod) * 100;
                int luck = EnchantmentHelper.getFishingLuckBonus(rod);
                if (bait.getItem() instanceof BaitProperties properties) {
                    lure += properties.getLureBonus();
                    luck += properties.getLuckBonus();
                }
                level.addFreshEntity(new DeluxeFishingHook(player, level, luck, lure, bait));
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }
        return InteractionResultHolder.sidedSuccess(rod, level.isClientSide());
    }

    private static List<ItemStack> contents(ItemStack rod) {
        List<ItemStack> stacks = new ArrayList<>();
        CompoundTag tag = rod.getTag();
        if (tag != null) {
            ListTag items = tag.getList(CONTENTS, Tag.TAG_COMPOUND);
            for (int i = 0; i < items.size(); i++) {
                ItemStack stack = ItemStack.of(items.getCompound(i));
                if (!stack.isEmpty()) stacks.add(stack);
            }
        }
        return stacks;
    }

    private static void store(ItemStack rod, List<ItemStack> stacks) {
        ListTag items = new ListTag();
        stacks.stream().filter(stack -> !stack.isEmpty()).forEach(stack -> items.add(stack.save(new CompoundTag())));
        if (items.isEmpty()) rod.removeTagKey(CONTENTS);
        else rod.getOrCreateTag().put(CONTENTS, items);
    }

    private static int weight(List<ItemStack> stacks) {
        return stacks.stream().mapToInt(stack -> stack.getCount() * (CAPACITY / stack.getMaxStackSize())).sum();
    }

    private static int insert(ItemStack rod, ItemStack incoming) {
        if (!(incoming.getItem() instanceof BaitItem) || incoming.isEmpty()) return 0;
        List<ItemStack> stacks = contents(rod);
        int count = Math.min(incoming.getCount(), (CAPACITY - weight(stacks)) / (CAPACITY / incoming.getMaxStackSize()));
        if (count <= 0) return 0;
        ItemStack added = incoming.copyWithCount(count);
        for (int i = 0; i < stacks.size(); i++) {
            if (ItemStack.isSameItemSameTags(stacks.get(i), added)) {
                added.grow(stacks.remove(i).getCount());
                break;
            }
        }
        stacks.add(0, added);
        incoming.shrink(count);
        store(rod, stacks);
        return count;
    }

    private static ItemStack removeOne(ItemStack rod) {
        List<ItemStack> stacks = contents(rod);
        if (stacks.isEmpty()) return ItemStack.EMPTY;
        ItemStack removed = stacks.remove(0);
        store(rod, stacks);
        return removed;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack rod, Slot slot, ClickAction action, Player player) {
        if (action != ClickAction.SECONDARY) return false;
        if (slot.getItem().isEmpty()) {
            ItemStack removed = removeOne(rod);
            if (!removed.isEmpty()) {
                player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
                insert(rod, slot.safeInsert(removed));
            }
        } else if (slot.getItem().getItem() instanceof BaitItem) {
            int available = (CAPACITY - weight(contents(rod))) / (CAPACITY / slot.getItem().getMaxStackSize());
            ItemStack taken = slot.safeTake(slot.getItem().getCount(), available, player);
            if (insert(rod, taken) > 0) player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
        }
        return true;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack rod, ItemStack cursor, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action != ClickAction.SECONDARY || !slot.allowModification(player)) return false;
        if (cursor.isEmpty()) {
            ItemStack removed = removeOne(rod);
            if (!removed.isEmpty()) {
                access.set(removed);
                player.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
            }
        } else if (insert(rod, cursor) > 0) {
            player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
        }
        return true;
    }

    public void decrementBaitInBundle(ItemStack rod) {
        ItemStack selected = contents(rod).stream().filter(stack -> stack.getItem() instanceof BaitProperties)
                .findFirst().orElse(ItemStack.EMPTY);
        decrementBaitInBundle(rod, selected);
    }

    public void decrementBaitInBundle(ItemStack rod, ItemStack usedBait) {
        if (usedBait.isEmpty()) return;
        List<ItemStack> stacks = contents(rod);
        for (ItemStack stack : stacks) {
            if (stack.getItem() instanceof BaitProperties bait && ItemStack.isSameItemSameTags(stack, usedBait)) {
                if (new Random().nextFloat() <= bait.getUseChance()) {
                    stack.shrink(1);
                    store(rod, stacks);
                }
                break;
            }
        }
    }

    @Override public boolean isBarVisible(ItemStack rod) { return weight(contents(rod)) > 0; }
    @Override public int getBarWidth(ItemStack rod) { return Math.min(1 + weight(contents(rod)) * 12 / CAPACITY, 13); }
    @Override public int getBarColor(ItemStack rod) { return 0xFF6666FF; }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack rod) {
        NonNullList<ItemStack> items = NonNullList.create();
        items.addAll(contents(rod));
        return items.isEmpty() ? Optional.empty() : Optional.of(new BundleTooltip(items, weight(items)));
    }

    @Override
    public void appendHoverText(ItemStack rod, @Nullable Level level, List<Component> tooltip, TooltipFlag flags) {
        tooltip.add(Component.translatable("item.fishingfrenzy.deluxe_fishing_rod.hint").withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.literal("Bait: " + weight(contents(rod)) + "/64").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        List<ItemStack> items = contents(entity.getItem());
        entity.getItem().removeTagKey(CONTENTS);
        ItemUtils.onContainerDestroyed(entity, items.stream());
    }
}
