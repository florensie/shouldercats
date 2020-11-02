package be.florens.shouldercats.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SitOnOwnerShoulderGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public abstract class MixinCatEntity extends TameableEntity {

	protected MixinCatEntity(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initGoals", at = @At("TAIL"))
	private void print(CallbackInfo info) {
		//noinspection ConstantConditions
		this.goalSelector.add(3, new SitOnOwnerShoulderGoal((TameableShoulderEntity) (Object) this));
	}
}
