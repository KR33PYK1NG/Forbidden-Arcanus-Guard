package rmc.mixins.forbidden_arcanus_guard.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RayTraceResult;
import rmc.libs.event_factory.EventFactory;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = EnergyBallEntity.class)
public abstract class EnergyBallEntityMixin {

    @Shadow
    private LivingEntity shootingEntity;

    @Inject(method = "Lcom/stal111/forbidden_arcanus/entity/projectile/EnergyBallEntity;onImpact(Lnet/minecraft/util/math/RayTraceResult;)V",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/entity/Entity;hurt(Lnet/minecraft/util/DamageSource;F)Z"))
    private void guardEnergyBlast(RayTraceResult result, CallbackInfo mixin, Entity entity) {
        if (!EventFactory.testEntityInteract(EventFactory.convert(this.shootingEntity), entity.level, entity)) {
            ((EnergyBallEntity)(Object) this).remove();
            mixin.cancel();
        }
    }

}