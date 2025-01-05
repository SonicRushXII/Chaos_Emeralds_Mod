package net.sonicrushxii.chaos_emerald.network.transformations.form_hyper;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormProperties;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class HyperDoubleJump
{
    public HyperDoubleJump() {}

    public HyperDoubleJump(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();

                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            HyperFormProperties hyperFormProperties = (HyperFormProperties) chaosEmeraldCap.formProperties;

                            //Don't use if Flying
                            if(player.getAbilities().flying)    return;

                            //Modify Tags
                            hyperFormProperties.hasHyperDoubleJump = false;

                            //Thrust
                            player.jumpFromGround();
                            player.addDeltaMovement(new Vec3(0,0.135,0));
                            //Particle
                            PacketHandler.sendToPlayer(player,new ParticleAuraPacketS2C(
                                    new DustParticleOptions(new Vector3f(0.900f,1.000f,1.000f), 1.5f),
                                    player.getX(),player.getY(),player.getZ(),
                                    0.0 ,0.2f,0.01f, 0.2f,20,true)
                            );

                            //PlaySound
                            /*Level world = player.level();
                            world.playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.DOUBLE_JUMP.get(), SoundSource.MASTER, 1.0f, 1.0f);*/
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
