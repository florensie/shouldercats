package be.florens.shouldercats.mixins.client;

import be.florens.shouldercats.client.ShoulderCatFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	public MixinPlayerEntityRenderer(EntityRenderDispatcher dispatcher, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
		super(dispatcher, model, shadowRadius);
	}

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V")
	public void init(EntityRenderDispatcher dispatcher, boolean b, CallbackInfo info) {
		this.addFeature(new ShoulderCatFeatureRenderer<>(this));
	}
}