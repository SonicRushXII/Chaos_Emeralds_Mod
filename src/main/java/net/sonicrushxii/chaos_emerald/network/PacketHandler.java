package net.sonicrushxii.chaos_emerald.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.network.all.BreakBlock;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.all.UpdateMainhandItem;
import net.sonicrushxii.chaos_emerald.network.aqua.BindEffectSyncPacketS2C;
import net.sonicrushxii.chaos_emerald.network.grey.SyncDigPacketS2C;
import net.sonicrushxii.chaos_emerald.network.purple.SyncBlastPacketS2C;

public class PacketHandler {
    static int id = 0;

    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChaosEmerald.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.messageBuilder(BreakBlock.class, id++).encoder(BreakBlock::encode).decoder(BreakBlock::new).consumerMainThread(BreakBlock::handle).add();
        INSTANCE.messageBuilder(UpdateMainhandItem.class, id++).encoder(UpdateMainhandItem::encode).decoder(UpdateMainhandItem::new).consumerMainThread(UpdateMainhandItem::handle).add();
        INSTANCE.messageBuilder(SyncEntityMotionS2C.class, id++).encoder(SyncEntityMotionS2C::encode).decoder(SyncEntityMotionS2C::new).consumerMainThread(SyncEntityMotionS2C::handle).add();

        //AQUA EMERALD
        INSTANCE.messageBuilder(BindEffectSyncPacketS2C.class, id++).encoder(BindEffectSyncPacketS2C::encode).decoder(BindEffectSyncPacketS2C::new).consumerMainThread(BindEffectSyncPacketS2C::handle).add();

        //GREY EMERALD
        INSTANCE.messageBuilder(SyncDigPacketS2C.class, id++).encoder(SyncDigPacketS2C::encode).decoder(SyncDigPacketS2C::new).consumerMainThread(SyncDigPacketS2C::handle).add();

        //PURPLE EMERALD
        INSTANCE.messageBuilder(SyncBlastPacketS2C.class, id++).encoder(SyncBlastPacketS2C::encode).decoder(SyncBlastPacketS2C::new).consumerMainThread(SyncBlastPacketS2C::handle).add();
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
