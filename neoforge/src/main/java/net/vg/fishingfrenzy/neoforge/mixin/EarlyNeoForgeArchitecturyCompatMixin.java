package net.vg.fishingfrenzy.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;

/**
 * Gives the accompanying config plugin a chance to rewrite Architectury's
 * obsolete early-NeoForge event descriptor before that class is linked.
 */
@Mixin(targets = "dev.architectury.event.forge.EventHandlerImplCommon", remap = false)
abstract class EarlyNeoForgeArchitecturyCompatMixin {
}
