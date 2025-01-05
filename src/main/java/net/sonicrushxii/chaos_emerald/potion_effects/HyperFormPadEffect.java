package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

public class HyperFormPadEffect extends MobEffect {
    public HyperFormPadEffect(MobEffectCategory mobEffectCategory, int color)
    {
        super(mobEffectCategory,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if(
                pLivingEntity.onGround()
        )
        {
            pLivingEntity.removeEffect(ModEffects.HYPER_FALLDMG_EFFECT.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
