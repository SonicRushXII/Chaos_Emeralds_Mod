package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerStopSoundPacketS2C {
    private final ResourceLocation soundLocation;

    public PlayerStopSoundPacketS2C(ResourceLocation soundLocation) {
        this.soundLocation = soundLocation;
    }

    public PlayerStopSoundPacketS2C(FriendlyByteBuf buf) {
        this.soundLocation = buf.readResourceLocation();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.soundLocation);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    // This code is run on the client side
                    Minecraft mc = Minecraft.getInstance();
                    ClientLevel world = mc.level;
                    LocalPlayer player = mc.player;

                    if(player != null && world != null) {
                        mc.getSoundManager().stop(this.soundLocation, SoundSource.MASTER);
                    }
                }));
        ctx.get().setPacketHandled(true);
    }
}
