package net.vg.fishingfrenzy.item.bait.targetbait;

import net.minecraft.nbt.CompoundTag;
import net.vg.fishingfrenzy.item.bait.BaitItem;
import net.vg.fishingfrenzy.item.bait.BaitProperties;

public class TargetBaitItem extends BaitItem implements TargetBaitProperties {
    private final int primaryColor;
    private final int secondaryColor;

    public TargetBaitItem(int primaryColor, int secondaryColor, Properties pProperties, BaitProperties bProperties) {
        super(pProperties, bProperties);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public int getColor(int tintIndex) {
        return tintIndex == 0 ? this.primaryColor : this.secondaryColor;
    }

    public static int getPrimaryColor(CompoundTag nbt) {
        return nbt.getIntOr("PrimaryColor", 0xFFFFFF);
    }

    public static int getSecondaryColor(CompoundTag nbt) {
        return nbt.getIntOr("SecondaryColor", 0xFFFFFF);
    }
}
