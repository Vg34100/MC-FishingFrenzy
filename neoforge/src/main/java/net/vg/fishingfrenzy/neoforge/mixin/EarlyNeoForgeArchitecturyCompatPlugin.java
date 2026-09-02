package net.vg.fishingfrenzy.neoforge.mixin;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * NeoForge 26.1 and 26.1.1 expose BlockEvent.BreakEvent. Architectury 20.0.4
 * was compiled against the later BreakBlockEvent rename, which prevents its
 * common event bridge from loading before any mod code runs. This modifies only
 * that obsolete binary name, and is a no-op when an Architectury target already
 * uses the newer API.
 */
public final class EarlyNeoForgeArchitecturyCompatPlugin implements IMixinConfigPlugin {
    private static final String TARGET = "dev.architectury.event.forge.EventHandlerImplCommon";
    private static final String OLD_INTERNAL_NAME = "net/neoforged/neoforge/event/level/block/BreakBlockEvent";
    private static final String NEW_INTERNAL_NAME = "net/neoforged/neoforge/event/level/BlockEvent$BreakEvent";

    @Override public void onLoad(String mixinPackage) { }
    @Override public String getRefMapperConfig() { return null; }
    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return true; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
    @Override public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (!TARGET.equals(targetClassName) || hasModernNeoForgeEvent()) return;
        for (MethodNode method : targetClass.methods) {
            method.desc = replace(method.desc);
            method.signature = replace(method.signature);
            if (method.localVariables != null) {
                for (LocalVariableNode variable : method.localVariables) {
                    variable.desc = replace(variable.desc);
                    variable.signature = replace(variable.signature);
                }
            }
            for (AbstractInsnNode instruction : method.instructions) rewrite(instruction);
        }
    }

    private static boolean hasModernNeoForgeEvent() {
        try {
            Class.forName(OLD_INTERNAL_NAME.replace('/', '.'), false, EarlyNeoForgeArchitecturyCompatPlugin.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    private static void rewrite(AbstractInsnNode instruction) {
        if (instruction instanceof MethodInsnNode method) {
            method.owner = replace(method.owner);
            method.desc = replace(method.desc);
        } else if (instruction instanceof FieldInsnNode field) {
            field.owner = replace(field.owner);
            field.desc = replace(field.desc);
        } else if (instruction instanceof TypeInsnNode type) {
            type.desc = replace(type.desc);
        } else if (instruction instanceof MultiANewArrayInsnNode array) {
            array.desc = replace(array.desc);
        } else if (instruction instanceof InvokeDynamicInsnNode dynamic) {
            dynamic.desc = replace(dynamic.desc);
            for (int index = 0; index < dynamic.bsmArgs.length; index++) {
                if (dynamic.bsmArgs[index] instanceof Type type) {
                    dynamic.bsmArgs[index] = Type.getType(replace(type.getDescriptor()));
                }
            }
        } else if (instruction instanceof LdcInsnNode ldc && ldc.cst instanceof Type type) {
            ldc.cst = Type.getType(replace(type.getDescriptor()));
        }
    }

    private static String replace(String value) {
        return value == null ? null : value.replace(OLD_INTERNAL_NAME, NEW_INTERNAL_NAME);
    }

    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
