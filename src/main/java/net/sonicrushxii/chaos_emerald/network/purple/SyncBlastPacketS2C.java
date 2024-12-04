package net.sonicrushxii.chaos_emerald.network.purple;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;

import java.util.function.Supplier;

public class SyncBlastPacketS2C {
    int entityId;
    byte blastTime;

    public SyncBlastPacketS2C(int entityId, byte blastTime) {
        this.entityId = entityId;
        this.blastTime = blastTime;
    }

    public SyncBlastPacketS2C(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.blastTime = buffer.readByte();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.blastTime);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                        try {
                            Level world = Minecraft.getInstance().level;
                            Player player = (Player) world.getEntity(entityId);

                            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                               chaosEmeraldCap.purpleChaosUse = this.blastTime;
                            });

                        }catch (NullPointerException|ClassCastException ignored) {}
                    });
                });
        ctx.get().setPacketHandled(true);
    }
}
