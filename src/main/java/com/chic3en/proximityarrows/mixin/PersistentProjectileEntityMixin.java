package com.chic3en.proximityarrows.mixin;

import com.chic3en.proximityarrows.util.ModTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

    // @Shadow allows usage of vanilla methods inside the class being mixed into
    @Shadow protected abstract ItemStack getItemStack();
    @Shadow protected abstract void onHit(LivingEntity target);

    // Inject right when the arrow calculates hitting an entity
    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void onPlayerHitMakeHarmless(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity target = entityHitResult.getEntity();

        // 1. Check if we hit a player, if false, abort
        if (!(target instanceof PlayerEntity playerTarget)) {
            return;
        }

        ItemStack arrowStack = this.getItemStack();
        if (arrowStack == null || arrowStack.isEmpty()) return;

        boolean isHarmless = false;

        // 2. Check custom JSON Tag (for arrows with the #proximityarrows:harmless_arrows tag)
        if (arrowStack.isIn(ModTags.HARMLESS_ARROWS)) {
            isHarmless = true;
        }

        // 3. Dynamic Check: Does this tipped arrow have beneficial potion effects?
        PotionContentsComponent potionContents = arrowStack.get(DataComponentTypes.POTION_CONTENTS);
        if (potionContents != null) {
            for (StatusEffectInstance effect : potionContents.getEffects()) {
                // If ANY effect on the arrow is considered "beneficial" by the game
                if (effect.getEffectType().value().isBeneficial()) {
                    isHarmless = true;
                    break;
                }
            }
        }

        // 4. If it's a buff arrow, manually apply the magic and CANCEL the physical hit
        if (isHarmless) {
            // Manually trigger the Potion effect
            this.onHit(playerTarget);

            // Destroy the arrow entity so it doesn't bounce off or stick in the ground
            ((PersistentProjectileEntity) (Object) this).discard();

            // Completely cancel the vanilla physical damage and knockback
            ci.cancel();
        }
    }
}