package net.vg.fishingfrenzy.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public record StatusEffectEntry(Holder<MobEffect> effect, int duration, int amplifier, float chance) {
}

