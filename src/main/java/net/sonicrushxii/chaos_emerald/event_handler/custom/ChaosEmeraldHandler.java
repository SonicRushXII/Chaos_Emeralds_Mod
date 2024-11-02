package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.blue.IceSpike;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.List;

public class ChaosEmeraldHandler {
    public static void aquaEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.cooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] > 0) {
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
                target.addDeltaMovement(new Vec3(lookAngle.x,0.15,lookAngle.z));
            }

            //Set Cooldown(in Seconds)
            chaosEmeraldCap.cooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] = 40;
        });
    }

    public static void blueEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.cooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] > 0) {
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
            chaosEmeraldCap.cooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] = 25;
        });
    }

    public static void greenEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.cooldownKey[EmeraldType.GREEN_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FF00)),true);
                return;
            }

            Vec3 currentPos = new Vec3(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ());
            Vec3 lookAngle = pPlayer.getLookAngle();
            LivingEntity tpTarget = null;

            //Scan Forward for enemies
            for (int i = 0; i < 10; ++i) {
                //Increment Current Position Forward
                currentPos = currentPos.add(lookAngle);
                AABB boundingBox = new AABB(currentPos.x() + 3, currentPos.y() + 3, currentPos.z() + 3,
                        currentPos.x() - 3, currentPos.y() - 3, currentPos.z() - 3);

                List<LivingEntity> nearbyEntities = pLevel.getEntitiesOfClass(
                        LivingEntity.class, boundingBox,
                        (enemy) -> !enemy.is(pPlayer) && enemy.isAlive());

                //If enemy is found then Target it
                if (!nearbyEntities.isEmpty()) {
                    //Select Closest target
                    tpTarget = Collections.min(nearbyEntities, (e1, e2) -> {
                        Vec3 e1Pos = new Vec3(e1.getX(), e1.getY(), e1.getZ());
                        Vec3 e2Pos = new Vec3(e2.getX(), e2.getY(), e2.getZ());

                        return (int) (e1Pos.distanceToSqr(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ()) - e2Pos.distanceToSqr(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ()));
                    });
                    break;
                }
            }

            if(tpTarget == null)    return;
            Vec3 targetPos = new Vec3(tpTarget.getX(),tpTarget.getY(),tpTarget.getZ());

            //Playsound
            pLevel.playSound(null,currentPos.x(),currentPos.y(),currentPos.z(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 0.75f, 0.75f);

            //Teleport
            Vec3 direction = currentPos.subtract(targetPos).normalize();
            Vec3 newPlayerPos = targetPos.add(direction);
            float[] yawPitch = Utilities.getYawPitchFromVec(targetPos.subtract(newPlayerPos));
            try {
                if (!pLevel.isClientSide) {
                    ServerPlayer player = (ServerPlayer) pPlayer;
                    pPlayer.teleportTo(player.serverLevel(), newPlayerPos.x, newPlayerPos.y+0.2, newPlayerPos.z,
                            Collections.emptySet(), yawPitch[0], yawPitch[1]);
                }
            }catch (ClassCastException e) {
                System.out.println("Teleport Failed");
            }

            //Update the motion
            if(!pLevel.isClientSide()) {
                pPlayer.setDeltaMovement(0, 0.6, 0);
                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(pPlayer.getId(),pPlayer.getDeltaMovement()));
            }
            tpTarget.setDeltaMovement(direction.reverse().scale(1.5));

            //Deal damage
            tpTarget.hurt(pLevel.damageSources().playerAttack(pPlayer), 4);
            tpTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,110,11,false,true,false),pPlayer);
            tpTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,110,11,false,true,false),pPlayer);

            //Particle
            if(pLevel.isClientSide)
                Utilities.displayParticle(pLevel, new DustParticleOptions(new Vector3f(0,1,0),2),
                            newPlayerPos.x(),newPlayerPos.y()+1,newPlayerPos.z(),
                            1.5f, 1.5f, 1.5f,
                            0.01, 100, false);

            //Playsound
            pLevel.playSound(null,newPlayerPos.x(),newPlayerPos.y(),newPlayerPos.z(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 0.75f, 0.75f);


            //Set Cooldown(in Seconds)
            chaosEmeraldCap.cooldownKey[EmeraldType.GREEN_EMERALD.ordinal()] = 7;
        });
    }

    public static void greyEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(!pLevel.isClientSide)
            pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.cooldownKey[EmeraldType.GREY_EMERALD.ordinal()] > 0) {
                    pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xEEEEEE)),true);
                    return;
                }

                //Activate Grey Emerald
                if(chaosEmeraldCap.greyEmeraldUse == 0) chaosEmeraldCap.greyEmeraldUse = 1;
            });
    }

    public static void purpleEmeraldUse(Level pLevel ,Player pPlayer)
    {
        if(!pLevel.isClientSide)
            pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.cooldownKey[EmeraldType.PURPLE_EMERALD.ordinal()] > 0) {
                    pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xCC00FF)),true);
                    return;
                }

                //Launch up
                pPlayer.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);
                pPlayer.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
                pPlayer.setDeltaMovement(0,0.17,0);
                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(pPlayer.getId(),pPlayer.getDeltaMovement()));

                //Activate Purple Emerald
                if(chaosEmeraldCap.purpleEmeraldUse == 0) chaosEmeraldCap.purpleEmeraldUse = 1;
            });
    }
}
