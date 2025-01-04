package net.sonicrushxii.chaos_emerald.network.transformations.form_super;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormProperties;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ChaosControlEX
{
    public ChaosControlEX() {}

    public ChaosControlEX(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public static boolean performChaosControlEX(ServerPlayer player)
    {
        Level pLevel = player.level();

        Vec3 currentPos = new Vec3(player.getX(),player.getY(),player.getZ());
        Vec3 lookAngle = player.getLookAngle();
        LivingEntity tpTarget = null;

        //Scan Forward for enemies
        for (int i = 0; i < 16; ++i) {
            //Increment Current Position Forward
            currentPos = currentPos.add(lookAngle);
            AABB boundingBox = new AABB(currentPos.x() + 3, currentPos.y() + 3, currentPos.z() + 3,
                    currentPos.x() - 3, currentPos.y() - 3, currentPos.z() - 3);

            List<LivingEntity> nearbyEntities = pLevel.getEntitiesOfClass(
                    LivingEntity.class, boundingBox,
                    (enemy) -> !enemy.is(player) && enemy.isAlive());

            //If enemy is found then Target it
            if (!nearbyEntities.isEmpty()) {
                //Select Closest target
                tpTarget = Collections.min(nearbyEntities, (e1, e2) -> {
                    Vec3 e1Pos = new Vec3(e1.getX(), e1.getY(), e1.getZ());
                    Vec3 e2Pos = new Vec3(e2.getX(), e2.getY(), e2.getZ());

                    return (int) (e1Pos.distanceToSqr(player.getX(),player.getY(),player.getZ()) - e2Pos.distanceToSqr(player.getX(),player.getY(),player.getZ()));
                });
                break;
            }
        }

        if(tpTarget == null)    return false;

        Vec3 targetPos = new Vec3(tpTarget.getX(),tpTarget.getY(),tpTarget.getZ());

        //Playsound
        pLevel.playSound(null,currentPos.x(),currentPos.y(),currentPos.z(),
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 0.75f, 0.75f);

        //Teleport
        Vec3 direction = currentPos.subtract(targetPos).normalize();
        Vec3 newPlayerPos = targetPos.add(direction);
        float[] yawPitch = Utilities.getYawPitchFromVec(targetPos.subtract(newPlayerPos));
        player.teleportTo(player.serverLevel(), newPlayerPos.x, newPlayerPos.y+0.2, newPlayerPos.z,
                Collections.emptySet(), yawPitch[0], yawPitch[1]);

        //Update the motion
        player.setDeltaMovement(0, 0.6, 0);
        PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(),player.getDeltaMovement()));
        tpTarget.setDeltaMovement(direction.reverse().scale(1.5));

        //Deal damage
        tpTarget.hurt(pLevel.damageSources().playerAttack(player), 5);
        tpTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,110,1,false,true,false),player);
        tpTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,110,1,false,true,false),player);

        //Particle
        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(new DustParticleOptions(new Vector3f(0,1,1),2),
                newPlayerPos.x(),newPlayerPos.y()+1,newPlayerPos.z(),
                0.01, 1.5f, 1.5f, 1.5f,
                100, false));

        //Playsound
        pLevel.playSound(null,newPlayerPos.x(),newPlayerPos.y(),newPlayerPos.z(),
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 0.75f, 0.75f);

        return true;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();

                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            SuperFormProperties superFormProperties = (SuperFormProperties) chaosEmeraldCap.formProperties;

                            //Chaos Control
                            if(performChaosControlEX(player))
                                //Set Cooldown IF move succeeds
                                superFormProperties.setCooldown(SuperFormAbility.CHAOS_CONTROL_EX,(byte)15);

                            //Emerald Data
                            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                                    player.getId(),chaosEmeraldCap
                            ));
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
