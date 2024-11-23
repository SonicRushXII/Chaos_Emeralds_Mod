package net.sonicrushxii.chaos_emerald.network.transformations.false_super;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModSounds;

import java.util.function.Supplier;

public class ChaosSpaz
{
    public ChaosSpaz() {}

    public ChaosSpaz(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            //Cancel if Not in False Super
                            if(chaosEmeraldCap.falseSuperTimer <= 0 || chaosEmeraldCap.falseChaosSpaz > 0) return;

                            //Play the Sound
                            player.level().playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.FALSE_CHAOS_SPAZ.get(), SoundSource.MASTER, 1.0f, 1.0f);

                            //Change Data
                            chaosEmeraldCap.falseChaosSpaz = 1;

                            //Set Gravity to Zero
                            player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
