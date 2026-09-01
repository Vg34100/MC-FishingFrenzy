package net.vg.fishingfrenzy.item.bait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BaitItem extends Item implements BaitProperties {
    private final int lureBonus;
    private final int luckBonus;
    private final float useChance;
    private final int multiCatchAmount;
    private final float multiCatchChance;
    private final ResourceKey<LootTable> lootTable;
    private final Supplier<Item> targetedFishSupplier;


    public BaitItem(Properties pProperties, BaitProperties bProperties) {
        super(pProperties);

        this.lureBonus = bProperties.getLureBonus();
        this.luckBonus = bProperties.getLuckBonus();

        this.useChance = bProperties.getUseChance();
        this.multiCatchAmount = bProperties.getMultiCatchAmount();
        this.multiCatchChance = bProperties.getMultiCatchChance();
        this.lootTable = bProperties.getLootTable();
        this.targetedFishSupplier = bProperties.getTargetedFishSupplier();
    }

    @Override
    public int getLureBonus() {
        return lureBonus;
    }

    @Override
    public int getLuckBonus() {
        return luckBonus;
    }

    @Override
    public float getUseChance() {
        return useChance;
    }

    @Override
    public int getMultiCatchAmount() {
        return multiCatchAmount;
    }

    @Override
    public float getMultiCatchChance() {
        return multiCatchChance;
    }

    @Override
    public ResourceKey<LootTable> getLootTable() {
        return lootTable;
    }

    @Override
    public Item getTargetedFish() {
        return targetedFishSupplier.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> addLine, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, addLine, tooltipFlag);
        addLine.accept(Component.translatable(this.getDescriptionId() + ".hint").withStyle(ChatFormatting.DARK_GRAY));
    }

}
