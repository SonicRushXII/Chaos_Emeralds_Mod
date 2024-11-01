package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;

import java.util.function.Supplier;

public class SyncEntityMotionS2C {
    int entityId;
    Vec3 entityMotion;

    public SyncEntityMotionS2C(int entityId, Vec3 entityMotion) {
        this.entityId = entityId;
        this.entityMotion = entityMotion;
    }

    public SyncEntityMotionS2C(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.entityMotion = new Vec3(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.entityId);
        buffer.writeDouble(this.entityMotion.x());
        buffer.writeDouble(this.entityMotion.y());
        buffer.writeDouble(this.entityMotion.z());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                        try {
                            Level world = Minecraft.getInstance().level;
                            Player player = (Player) world.getEntity(entityId);

                            player.setDeltaMovement(this.entityMotion);
                        }catch (NullPointerException|ClassCastException ignored) {}
                    });
                });
        ctx.get().setPacketHandled(true);
    }
}
