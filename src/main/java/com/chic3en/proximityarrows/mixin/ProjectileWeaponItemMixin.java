package com.chic3en.proximityarrows.mixin;

import com.chic3en.proximityarrows.util.ProximityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

// We inject into Minecraft's RangedWeaponItem (which handles Bows and Crossbows)
@Mixin(RangedWeaponItem.class)
public class ProjectileWeaponItemMixin {

    // Inject at the very beginning of the method that searches for an arrow to fire
    @Inject(method = "getHeldProjectile", at = @At("HEAD"), cancellable = true)
    private static void onGetHeldProjectile(LivingEntity entity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {

        // 1. Only want to change this for Players. Skeletons can use vanilla logic.
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        // 2. Always check the offhand first
        // If a player specifically puts an arrow in their offhand, they want to force-use it.
        ItemStack offhandStack = player.getOffHandStack();
        if (predicate.test(offhandStack)) {
            cir.setReturnValue(offhandStack);
            return;
        }

        // 3. Find the Bow's location. We get the currently selected hotbar slot (0-8)
        int bowSlot = player.getInventory().selectedSlot;

        ItemStack closestArrow = ItemStack.EMPTY;
        double minDistance = Double.MAX_VALUE;

        // 4. Loop through the 36 main inventory slots (0-8 hotbar, 9-35 main inventory)
        for (int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getStack(i);

            // Is this item actually a valid arrow for this weapon?
            if (predicate.test(stack)) {

                // Calculate the physical 2D distance using the Helper
                double distance = ProximityHelper.calculateDistance(bowSlot, i);

                // If this arrow is closer than the last one checked, remember it
                if (distance < minDistance) {
                    minDistance = distance;
                    closestArrow = stack;
                }
            }
        }

        // 5. If found an arrow, force Minecraft to use it
        // This completely bypasses the vanilla top-left priority.
        if (!closestArrow.isEmpty()) {
            cir.setReturnValue(closestArrow);
        }
    }
}