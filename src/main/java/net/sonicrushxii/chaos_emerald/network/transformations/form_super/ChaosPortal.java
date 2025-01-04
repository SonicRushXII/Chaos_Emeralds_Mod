package net.sonicrushxii.chaos_emerald.network.transformations.form_super;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormProperties;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;

import java.util.function.Supplier;

public class ChaosPortal
{
    public ChaosPortal() {}

    public ChaosPortal(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();

                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            SuperFormProperties superFormProperties = (SuperFormProperties) chaosEmeraldCap.formProperties;

                            //Set Cooldown
                            superFormProperties.setCooldown(SuperFormAbility.CHAOS_PORTAL,(byte)15);

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
