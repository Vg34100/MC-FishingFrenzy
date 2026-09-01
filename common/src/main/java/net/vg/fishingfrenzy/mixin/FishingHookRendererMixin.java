package net.vg.fishingfrenzy.mixin;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.vg.fishingfrenzy.registry.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingHookRenderer.class)
public class FishingHookRendererMixin {

    @Redirect(
            method = "getHoldingArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"
            )
    )
    private static Item fishingfrenzy$redirectGetItem(ItemStack stack) {
        if (fishingfrenzy$isValidFishingRod(stack)) {
            return Items.FISHING_ROD;
        }

        return stack.getItem();
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
//@Mixin(FishingHookRenderer.class)
//public class FishingHookRendererMixin {
//    @Redirect(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
//    private boolean fishingfrenzy$redirectFishingRodCheck(ItemStack itemStack, Item item) {
//        return fishingfrenzy$isValidFishingRod(itemStack);
//    }
//
//    @Unique
//    private static boolean fishingfrenzy$isValidFishingRod(ItemStack stack) {
//        for (RegistrySupplier<Item> supplier : ModItems.FISHING_RODS) {
//            if (stack.is(supplier.get())) {
//                return true;
//            }
//        }
//        return stack.is(Items.FISHING_ROD);
//    }
//}