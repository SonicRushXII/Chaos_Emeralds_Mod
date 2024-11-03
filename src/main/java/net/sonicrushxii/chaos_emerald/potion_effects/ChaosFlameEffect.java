package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.sonicrushxii.chaos_emerald.Utilities;
import org.joml.Vector3f;

public class ChaosFlameEffect extends MobEffect {
    public ChaosFlameEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if(pLivingEntity.level().isClientSide)
        {
            Utilities.displayParticle(pLivingEntity.level(),new DustParticleOptions(new Vector3f(1f,0f,0f),1f),
                    pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ(),0.3f,0.75f,0.3f,0,15,false);
            Utilities.displayParticle(pLivingEntity.level(), ParticleTypes.FLAME,
                    pLivingEntity.getX(),pLivingEntity.getY(),pLivingEntity.getZ(),0.3f,0.75f,0.3f,0,2,false);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
