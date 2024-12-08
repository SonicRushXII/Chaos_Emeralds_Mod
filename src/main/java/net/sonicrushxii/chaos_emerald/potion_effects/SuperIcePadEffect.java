package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

import java.util.Arrays;
import java.util.HashSet;

public class SuperIcePadEffect extends MobEffect {
    private static final HashSet<String> iceSafeBlocks =
            new HashSet<>(Arrays.asList(
                    "minecraft:blue_ice",
                    "minecraft:air",
                    "minecraft:cave_air",
                    "minecraft:void_air"
            )
            );

    public SuperIcePadEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if(
                pLivingEntity.onGround() &&
                !(iceSafeBlocks.contains(ForgeRegistries.BLOCKS.getKey(pLivingEntity.level().getBlockState(pLivingEntity.blockPosition().offset(0,-1,0)).getBlock())+""))
        )
        {
            pLivingEntity.removeEffect(ModEffects.SUPER_ICE_LAUNCH.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
