package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.blue.IceSpike;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;

public class ChaosEmeraldHandler {
    public static void aquaEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_COOLDOWN).ifPresent(chaosEmeraldCooldown -> {
            if(chaosEmeraldCooldown.cooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FFFF)),true);
                return;
            }

            Vec3 lookAngle = pPlayer.getLookAngle().scale(2);
            Vec3 displayPos = new Vec3(
                    pPlayer.getX()+lookAngle.x(),
                    pPlayer.getY()+lookAngle.y()+pPlayer.getEyeHeight(),
                    pPlayer.getZ()+lookAngle.z()
            );

            //Playsound
            pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                    SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.MASTER, 0.75f, 0.75f);
            pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                    SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.MASTER, 0.75f, 0.75f);

            //Particle Effects
            if(pLevel.isClientSide) {
                Utilities.displayParticle(pLevel, ParticleTypes.BUBBLE,displayPos.x(),displayPos.y(),displayPos.z(),
                        3.5f, 3.5f, 3.5f,
                        0.01, 30, false);
                Utilities.displayParticle(pLevel, ParticleTypes.ENCHANTED_HIT,displayPos.x(),displayPos.y(),displayPos.z(),
                        3.5f, 3.5f, 3.5f,
                        0.01, 100, true);
            }

            //Entity Effects
            lookAngle = pPlayer.getLookAngle().scale(0.2);
            for(LivingEntity target : pLevel.getEntitiesOfClass(LivingEntity.class,
                    new AABB(displayPos.x()+3.5,displayPos.y()+3.5,displayPos.z()+3.5,
                            displayPos.x()-3.5,displayPos.y()-3.5,displayPos.z()-3.5),
                    (enemy)->!enemy.is(pPlayer))
            )
            {
                target.addEffect(new MobEffectInstance(ModEffects.CHAOS_BIND.get(), 200, 0, false, false, false));
                target.addDeltaMovement(new Vec3(lookAngle.x,0.1,lookAngle.z));
            }

            //Set Cooldown(in Seconds)
            chaosEmeraldCooldown.cooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] = 1;
        });
    }

    public static void blueEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_COOLDOWN).ifPresent(chaosEmeraldCooldown -> {
            if(chaosEmeraldCooldown.cooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)),true);
                return;
            }

            Vec3 spawnPos = new Vec3(pPlayer.getX()+pPlayer.getLookAngle().x,
                    pPlayer.getY()+pPlayer.getLookAngle().y+1.0,
                    pPlayer.getZ()+pPlayer.getLookAngle().z);
            pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                    SoundEvents.EGG_THROW, SoundSource.MASTER, 1.0f, 1.0f);
            IceSpike iceSpike = new IceSpike(ModEntityTypes.ICE_SPIKE.get(), pLevel);
            iceSpike.setPos(spawnPos);
            iceSpike.setMovementDirection(pPlayer.getLookAngle());
            iceSpike.setOwner(pPlayer.getUUID());

            // Add the entity to the world
            pLevel.addFreshEntity(iceSpike);

            //Set Cooldown(in Seconds)
            chaosEmeraldCooldown.cooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] = 25;
        });
    }
}
