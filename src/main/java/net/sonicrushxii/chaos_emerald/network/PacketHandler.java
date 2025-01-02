package net.sonicrushxii.chaos_emerald.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.network.all.*;
import net.sonicrushxii.chaos_emerald.network.aqua.BindEffectSyncPacketS2C;
import net.sonicrushxii.chaos_emerald.network.grey.SyncDigPacketS2C;
import net.sonicrushxii.chaos_emerald.network.red.SuperInfernoParticleS2C;
import net.sonicrushxii.chaos_emerald.network.transformations.false_super.ActivateFalseSuper;
import net.sonicrushxii.chaos_emerald.network.purple.SyncBlastPacketS2C;
import net.sonicrushxii.chaos_emerald.network.red.FireSyncPacketS2C;
import net.sonicrushxii.chaos_emerald.network.transformations.false_super.ChaosSpaz;
import net.sonicrushxii.chaos_emerald.network.transformations.form_super.ActivateSuperForm;

public class PacketHandler {
    static int id = 0;

    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChaosEmerald.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register()
    {
        INSTANCE.messageBuilder(BreakBlock.class, id++).encoder(BreakBlock::encode).decoder(BreakBlock::new).consumerMainThread(BreakBlock::handle).add();
        INSTANCE.messageBuilder(UpdateHandItem.class, id++).encoder(UpdateHandItem::encode).decoder(UpdateHandItem::new).consumerMainThread(UpdateHandItem::handle).add();
        INSTANCE.messageBuilder(SyncEntityMotionS2C.class, id++).encoder(SyncEntityMotionS2C::encode).decoder(SyncEntityMotionS2C::new).consumerMainThread(SyncEntityMotionS2C::handle).add();
        INSTANCE.messageBuilder(ParticleAuraPacketS2C.class, id++).encoder(ParticleAuraPacketS2C::encode).decoder(ParticleAuraPacketS2C::new).consumerMainThread(ParticleAuraPacketS2C::handle).add();
        INSTANCE.messageBuilder(ParticleDirPacketS2C.class, id++).encoder(ParticleDirPacketS2C::encode).decoder(ParticleDirPacketS2C::new).consumerMainThread(ParticleDirPacketS2C::handle).add();
        INSTANCE.messageBuilder(PlayerStopSoundPacketS2C.class, id++).encoder(PlayerStopSoundPacketS2C::encode).decoder(PlayerStopSoundPacketS2C::new).consumerMainThread(PlayerStopSoundPacketS2C::handle).add();
        INSTANCE.messageBuilder(ParticleRaycastPacketS2C.class, id++).encoder(ParticleRaycastPacketS2C::encode).decoder(ParticleRaycastPacketS2C::new).consumerMainThread(ParticleRaycastPacketS2C::handle).add();
        INSTANCE.messageBuilder(EmeraldDataSyncS2C.class, id++).encoder(EmeraldDataSyncS2C::encode).decoder(EmeraldDataSyncS2C::new).consumerMainThread(EmeraldDataSyncS2C::handle).add();

        //AQUA EMERALD
        INSTANCE.messageBuilder(BindEffectSyncPacketS2C.class, id++).encoder(BindEffectSyncPacketS2C::encode).decoder(BindEffectSyncPacketS2C::new).consumerMainThread(BindEffectSyncPacketS2C::handle).add();

        //GREY EMERALD
        INSTANCE.messageBuilder(SyncDigPacketS2C.class, id++).encoder(SyncDigPacketS2C::encode).decoder(SyncDigPacketS2C::new).consumerMainThread(SyncDigPacketS2C::handle).add();

        //PURPLE EMERALD
        INSTANCE.messageBuilder(SyncBlastPacketS2C.class, id++).encoder(SyncBlastPacketS2C::encode).decoder(SyncBlastPacketS2C::new).consumerMainThread(SyncBlastPacketS2C::handle).add();

        //RED EMERALD
        INSTANCE.messageBuilder(FireSyncPacketS2C.class, id++).encoder(FireSyncPacketS2C::encode).decoder(FireSyncPacketS2C::new).consumerMainThread(FireSyncPacketS2C::handle).add();
        INSTANCE.messageBuilder(SuperInfernoParticleS2C.class, id++).encoder(SuperInfernoParticleS2C::encode).decoder(SuperInfernoParticleS2C::new).consumerMainThread(SuperInfernoParticleS2C::handle).add();

        //ACTIVATE FALSE SUPER
        INSTANCE.messageBuilder(ActivateFalseSuper.class, id++).encoder(ActivateFalseSuper::encode).decoder(ActivateFalseSuper::new).consumerMainThread(ActivateFalseSuper::handle).add();
        INSTANCE.messageBuilder(ChaosSpaz.class, id++).encoder(ChaosSpaz::encode).decoder(ChaosSpaz::new).consumerMainThread(ChaosSpaz::handle).add();

        //ACTIVATE SUPER FORM
        INSTANCE.messageBuilder(ActivateSuperForm.class, id++).encoder(ActivateSuperForm::encode).decoder(ActivateSuperForm::new).consumerMainThread(ActivateSuperForm::handle).add();

    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToChunkPlayers(LevelChunk levelChunk, Object msg) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> levelChunk), msg);
    }

    public static void sendToALLPlayers(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
