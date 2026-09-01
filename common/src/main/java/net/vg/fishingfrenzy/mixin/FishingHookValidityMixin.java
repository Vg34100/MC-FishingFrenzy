package net.vg.fishingfrenzy.mixin;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.vg.fishingfrenzy.registry.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookValidityMixin extends Entity {

    public FishingHookValidityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(
            method = "shouldStopFishing",
            at = @At("HEAD"),
            cancellable = true
    )
    private void validateFishingRod(Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();

        // Check if the main hand or offhand has a valid fishing rod
        boolean mainHandHasValidRod = fishingfrenzy$isValidFishingRod(mainHandStack);
        boolean offHandHasValidRod = fishingfrenzy$isValidFishingRod(offHandStack);

        // Validate if the player is alive, not removed, has a valid rod, and is within a certain distance
        if (!player.isRemoved() && player.isAlive() && (mainHandHasValidRod || offHandHasValidRod) && this.distanceToSqr(player) <= 1024.0D) {
            cir.setReturnValue(false); // Prevent the bobber from being removed
        }
    }

    @Unique
    private static boolean fishingfrenzy$isValidFishingRod(ItemStack stack) {
        for (RegistrySupplier<Item> supplier : ModItems.FISHING_RODS) {
            if (stack.is(supplier.get())) {
                return true;
            }
        }
        return stack.is(Items.FISHING_ROD);
    }
}