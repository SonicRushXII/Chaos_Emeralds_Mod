package net.sonicrushxii.chaos_emerald.network.transformations.form_hyper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormProperties;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormProperties;
import net.sonicrushxii.chaos_emerald.entities.form_super.PortalRingEntity;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;

import java.util.function.Supplier;

public class SuperChaosPortal
{
    public SuperChaosPortal() {}

    public SuperChaosPortal(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public static void throwPortalRing(ServerPlayer player, Vec3 spawnPos)
    {
        Level pLevel = player.level();

        //Sound
        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundSource.MASTER, 1.0f, 1.0f);

        //Spawning
        PortalRingEntity portalRingEntity = new PortalRingEntity(ModEntityTypes.PORTAL_RING.get(), pLevel);

        portalRingEntity.setPos(spawnPos);
        portalRingEntity.initializeDuration(40);
        portalRingEntity.setMovementDirection(Utilities.calculateViewVector(player.getXRot()/2,player.getYRot()).scale(0.15));
        portalRingEntity.setPortalType((player.isShiftKeyDown())?(byte)0:(byte)1);

        // Add the entity to the world
        pLevel.addFreshEntity(portalRingEntity);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();

                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            HyperFormProperties superFormProperties = (HyperFormProperties) chaosEmeraldCap.formProperties;

                            //Set Cooldown
                            superFormProperties.setCooldown(HyperFormAbility.SUPER_CHAOS_PORTAL,(byte)15);

                            //Spawn Chaos Portal Ring
                            throwPortalRing(player,new Vec3(player.getX(),player.getY()+1,player.getZ()));

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
