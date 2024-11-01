package net.sonicrushxii.chaos_emerald.network.grey;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SyncDigPacketS2C {
    int entityId;
    byte digTime;
    Vec3 entityMotion;

    public SyncDigPacketS2C(int entityId, byte digTime, Vec3 entityMotion) {
        this.entityId = entityId;
        this.digTime = digTime;
        this.entityMotion = entityMotion;
    }

    public SyncDigPacketS2C(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.digTime = buffer.readByte();
        this.entityMotion = new Vec3(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.digTime);
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
                            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                               chaosEmeraldCap.greyEmeraldUse = this.digTime;
                            });

                        }catch (NullPointerException|ClassCastException ignored) {}
                    });
                });
        ctx.get().setPacketHandled(true);
    }
}
