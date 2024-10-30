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
                        try {
                            if (effectDur > 0)
                            {
                                LivingEntity livingEntity = (LivingEntity) Minecraft.getInstance().level.getEntity(entityId);
                                System.out.println(livingEntity);
                                if(livingEntity.hasEffect(ModEffects.CHAOS_BIND.get()))
                                    livingEntity.getEffect(ModEffects.CHAOS_BIND.get()).
                                        update(new MobEffectInstance(ModEffects.CHAOS_BIND.get(), effectDur,
                                                0, false, false,false));
                                else
                                    livingEntity.addEffect(new MobEffectInstance(ModEffects.CHAOS_BIND.get(), effectDur,
                                            0, false, false,false));
                            }
                        }catch (NullPointerException|ClassCastException ignored) {}
                    });
                });
        ctx.get().setPacketHandled(true);
    }
}
