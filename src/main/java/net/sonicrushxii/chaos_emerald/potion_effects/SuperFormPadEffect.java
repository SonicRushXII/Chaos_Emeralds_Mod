package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

public class SuperFormPadEffect extends MobEffect {
    public SuperFormPadEffect(MobEffectCategory mobEffectCategory, int color)
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
            pLivingEntity.removeEffect(ModEffects.SUPER_FALLDMG_EFFECT.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
