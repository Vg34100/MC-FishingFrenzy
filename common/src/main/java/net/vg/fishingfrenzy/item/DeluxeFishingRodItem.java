package net.vg.fishingfrenzy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.vg.fishingfrenzy.entity.DeluxeFishingHook;
import net.vg.fishingfrenzy.item.bait.BaitItem;
import net.vg.fishingfrenzy.item.bait.BaitProperties;
import org.apache.commons.lang3.math.Fraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DeluxeFishingRodItem extends FishingRodItem {
    private static final int BAR_COLOR = 0xFF6666FF;
    private static final int TOOLTIP_MAX_WEIGHT = 64;

    public DeluxeFishingRodItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack rodStack = pPlayer.getItemInHand(pHand);
        ItemStack baitStack = findBaitInBundle(rodStack);

        if (pPlayer.fishing != null) {
            if (!pLevel.isClientSide()) {
                int i = pPlayer.fishing.retrieve(rodStack);
                rodStack.hurtAndBreak(i, pPlayer, pHand);
            }

            pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (pLevel instanceof ServerLevel serverlevel) {
                int lureLevel = (int)(EnchantmentHelper.getFishingTimeReduction(serverlevel, rodStack, pPlayer) * 20.0F);
                int luckLevel = EnchantmentHelper.getFishingLuckBonus(serverlevel, rodStack, pPlayer);

                if (!baitStack.isEmpty()) {
                    BaitProperties baitProperties = (BaitProperties) baitStack.getItem();
                    luckLevel += baitProperties.getLuckBonus();
                    lureLevel += baitProperties.getLureBonus();
                }

                serverlevel.addFreshEntity(new DeluxeFishingHook(pPlayer, pLevel, luckLevel, lureLevel, baitStack));
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResult.SUCCESS;

    }


    //    Bundle Component Properties
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (action != ClickAction.SECONDARY) {
            return false;
        } else {
            BundleContents bundleContents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
            if (bundleContents == null) {
                return false;
            } else {
                ItemStack itemStack = slot.getItem();
                BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
                if (itemStack.isEmpty()) {
                    this.playRemoveOneSound(player);
                    ItemStack itemStack2 = mutable.removeOne();
                    if (itemStack2 != null) {
                        ItemStack itemStack3 = slot.safeInsert(itemStack2);
                        mutable.tryInsert(itemStack3);
                    }
                } else if (itemStack.getItem().canFitInsideContainerItems() && isBait(itemStack)) {
                    int i = mutable.tryTransfer(slot, player);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                stack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
                return true;
            }
        }
    }

    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            BundleContents bundleContents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
            if (bundleContents == null) {
                return false;
            } else {
                BundleContents.Mutable mutable = new BundleContents.Mutable(bundleContents);
                if (other.isEmpty()) {
                    ItemStack itemStack = mutable.removeOne();
                    if (itemStack != null) {
                        this.playRemoveOneSound(player);
                        access.set(itemStack);
                    }
                } else if (isBait(other)) {
                    int i = mutable.tryInsert(other);
                    if (i > 0) {
                        this.playInsertSound(player);
                    }
                }

                stack.set(DataComponents.BUNDLE_CONTENTS, mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    // Bundle stuff
    private ItemStack findBaitInBundle(ItemStack rodStack) {
        BundleContents bundleContentsComponent = rodStack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        if (bundleContentsComponent.isEmpty()) {
            return ItemStack.EMPTY;
        }
        for (ItemStackTemplate template : bundleContentsComponent.items()) {
            if (template.item().value() instanceof BaitItem) {
                return template.create();
//                return template.toStack();
//                I am unsure if this is the correct one.
            }
        }
        return ItemStack.EMPTY;
    }

    public void decrementBaitInBundle(ItemStack rodStack) {
        decrementBaitInBundle(rodStack, findBaitInBundle(rodStack));
    }

    public void decrementBaitInBundle(ItemStack rodStack, ItemStack usedBait) {
        if (usedBait.isEmpty()) return;
        BundleContents bundleContentsComponent = rodStack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        List<ItemStack> stacks = bundleContentsComponent.itemCopyStream().collect(Collectors.toList());
        Random random = new Random();

        boolean modified = false;
        for (ItemStack itemStack : stacks) {
            if (itemStack.getItem() instanceof BaitProperties baitProperties
                    && ItemStack.isSameItemSameComponents(itemStack, usedBait)) {
                if (random.nextFloat() <= baitProperties.getUseChance()) {
                    itemStack.shrink(1);
                    modified = true;
                }
                break;
            }
        }

        if (modified) {
            stacks.removeIf(ItemStack::isEmpty);
            if (stacks.isEmpty()) {
                rodStack.set(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
            } else {
                List<ItemStackTemplate> templates = stacks.stream()
                        .map(ItemStackTemplate::fromNonEmptyStack)
                        .collect(Collectors.toList());
                rodStack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(templates));
            }
        }
    }


    @Override
    public boolean isBarVisible(ItemStack stack) {
        BundleContents bundleContents = (BundleContents)stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        return bundleContents.weight().result().map(w -> w.compareTo(Fraction.ZERO) > 0).orElse(false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        BundleContents bundleContents = (BundleContents)stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        return bundleContents.weight().result().map(w -> Math.min(1 + Mth.mulAndTruncate(w, 12), 13)).orElse(0);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
        if (contents == null || contents.isEmpty()) return Optional.empty();
        return Optional.of(new BundleTooltip(contents));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> addLine, TooltipFlag tooltipFlag) {
        BundleContents bundleContents = stack.get(DataComponents.BUNDLE_CONTENTS);
        addLine.accept(Component.translatable("item.fishingfrenzy.deluxe_fishing_rod.hint").withStyle(ChatFormatting.DARK_GRAY));
        if (bundleContents != null) {
            int used = bundleContents.weight().result().map(w -> Mth.mulAndTruncate(w, 64)).orElse(0);
            addLine.accept(Component.literal("Bait: " + used + "/64").withStyle(ChatFormatting.GRAY));
        }
    }

    public void onDestroyed(ItemEntity itemEntity) {
        BundleContents bundleContents = (BundleContents)itemEntity.getItem().get(DataComponents.BUNDLE_CONTENTS);
        if (bundleContents != null) {
            itemEntity.getItem().set(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
//            ItemUtils.onContainerDestroyed(itemEntity, bundleContents.itemsCopy());
            ItemUtils.onContainerDestroyed(itemEntity, bundleContents.itemCopyStream());

        }
    }

    // Check if the given stack is a bait item
    private boolean isBait(ItemStack stack) {
        return stack.getItem() instanceof BaitItem; //* Change to BaitItem when created
    }

    //    Bundle Sounds
    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}
