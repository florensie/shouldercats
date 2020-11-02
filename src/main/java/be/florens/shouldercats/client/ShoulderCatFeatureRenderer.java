package be.florens.shouldercats.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class ShoulderCatFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
	private final CatEntityModel<CatEntity> model = new CatEntityModel<>(1.0f);

	public ShoulderCatFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		this.renderShoulderCat(matrices, vertexConsumers, light, player, limbAngle, limbDistance, headYaw, headPitch, true);
		this.renderShoulderCat(matrices, vertexConsumers, light, player, limbAngle, limbDistance, headYaw, headPitch, false);
	}

	private void renderShoulderCat(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T player, float limbAngle, float limbDistance, float headYaw, float headPitch, boolean leftShoulder) {
		CompoundTag compoundTag = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
		EntityType.get(compoundTag.getString("id")).filter((entityType) -> entityType == EntityType.CAT).ifPresent((entityType) -> {
			matrices.push();
			matrices.translate(leftShoulder ? 0.4000000059604645D : -0.4000000059604645D, player.isInSneakingPose() ? -1.2999999523162842D : -1.5D, 0.0D);
			//VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(CatEntityRenderer.TEXTURES[compoundTag.getInt("Variant")]));
			model.render(matrices, vertexConsumers.getBuffer(model.getLayer(CatEntity.TEXTURES.get(compoundTag.getInt("CatType")))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
			matrices.pop();
		});
	}
}
