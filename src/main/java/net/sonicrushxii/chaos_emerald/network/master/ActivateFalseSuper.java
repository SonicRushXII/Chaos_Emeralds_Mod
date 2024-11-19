package net.sonicrushxii.chaos_emerald.network.master;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.Utilities;

import java.util.function.Supplier;

public class ActivateFalseSuper
{
    public ActivateFalseSuper() {}

    public ActivateFalseSuper(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    //Check if Looking at the Master Emerald
                    if(player != null && Utilities.isPlayerLookingAtBlockType(player,player.level(),"chaos_emerald:master_emerald",7.5D))
                    {
                        System.out.println("Transform Into False Super");
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
