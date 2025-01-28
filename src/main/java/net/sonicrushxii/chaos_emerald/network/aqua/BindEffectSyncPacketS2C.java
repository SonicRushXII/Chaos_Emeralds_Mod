package net.sonicrushxii.chaos_emerald.network.aqua;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.event_handler.LoginHandler;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientPacketHandler;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

import java.util.function.Supplier;

public class BindEffectSyncPacketS2C {
    private int entityId;
    private int effectDur;

    public BindEffectSyncPacketS2C(int entityId, int effectDur) {
        this.entityId = entityId;
        this.effectDur = effectDur;
    }

    public BindEffectSyncPacketS2C(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.effectDur = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.effectDur);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                        ClientPacketHandler.bindEffectSync(this.entityId,this.effectDur);
                    });
                });
        ctx.get().setPacketHandled(true);
    }
}
