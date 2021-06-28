package rmc.mixins.forbidden_arcanus_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stal111.forbidden_arcanus.entity.projectile.ChorusPearlEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RayTraceResult;
import rmc.libs.event_factory.EventFactory;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ChorusPearlEntity.class)
public abstract class ChorusPearlEntityMixin {

    @Inject(method = "Lcom/stal111/forbidden_arcanus/entity/projectile/ChorusPearlEntity;onHit(Lnet/minecraft/util/math/RayTraceResult;)V",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/entity/LivingEntity;hurt(Lnet/minecraft/util/DamageSource;F)Z"))
    private void guardChorusTeleport(RayTraceResult result, CallbackInfo mixin, LivingEntity entity) {
        if (!EventFactory.testEntityInteract(EventFactory.convert(((ChorusPearlEntity)(Object) this).getOwner()), entity.level, entity)) {
            ((ChorusPearlEntity)(Object) this).remove();
            mixin.cancel();
        }
    }

}