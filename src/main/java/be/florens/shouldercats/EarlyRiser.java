package be.florens.shouldercats;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.tree.MethodInsnNode;

public class EarlyRiser implements Runnable {
	@Override
	public void run() {
		MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
		String catEntity = remapper.mapClassName("intermediary", "net.minecraft.class_1451");
		String tameableEntity = remapper.mapClassName("intermediary", "net.minecraft.class_1321").replace('.', '/');
		String tameableShoulderEntity = remapper.mapClassName("intermediary", "net.minecraft.class_1471").replace('.', '/');

		ClassTinkerers.addTransformation(catEntity, classNode -> {
			classNode.superName = tameableShoulderEntity;  // Change superclass def

			// Update all super() calls (there should really only be one)
			classNode.methods.forEach(method -> method.instructions.forEach(abstractInsnNode -> {
				if (abstractInsnNode instanceof MethodInsnNode) {
					MethodInsnNode insn = (MethodInsnNode) abstractInsnNode;
					if (insn.name.equals("<init>") && insn.owner.equals(tameableEntity)) {
						insn.owner = tameableShoulderEntity;
					}
				}
			}));
		});
	}
}
