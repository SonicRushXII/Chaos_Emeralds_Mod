package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.grey.SyncDigPacketS2C;
import net.sonicrushxii.chaos_emerald.network.master.ActivateFalseSuper;
import net.sonicrushxii.chaos_emerald.network.purple.SyncBlastPacketS2C;
import org.joml.Vector3f;

public class PlayerTickHandler {
    private static final int TICKS_PER_SEC = 20;
    private static int serverTick=0;
    public static int clientTickCounter = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END || event.player == null) return;
        if (event.player.level().isClientSide()) onLocalPlayerTick((LocalPlayer)event.player);
        else onServerPlayerTick((ServerPlayer)event.player);

        Player player = event.player;

        //Handle cooldowns for all players
        if(serverTick == 0)
            event.player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                byte[] cooldownKey = chaosEmeraldCap.cooldownKey;
                for(byte i=0;i<cooldownKey.length;++i)
                    cooldownKey[i] = (byte) Math.max(0,cooldownKey[i]-1);
            });
    }

    public void onLocalPlayerTick(LocalPlayer player)
    {
        clientTickCounter = (clientTickCounter+1)%TICKS_PER_SEC;

        //Particle
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Grey Emerald Digging Particle
            if(chaosEmeraldCap.greyEmeraldUse > 0)
                Utilities.displayParticle(player.level(), new DustParticleOptions(new Vector3f(0.9f, 0.9f, 0.9f), 2),
                            player.getX(), player.getY() + 1, player.getZ(),
                            1.5f, 1.5f, 1.5f,
                            0.01, 50, false);

            //Blast Particle
            if(chaosEmeraldCap.purpleEmeraldUse > 0)
                Utilities.displayParticle(player.level(), new DustParticleOptions(new Vector3f(0.8f, 0.0f, 1f), 1),
                        player.getX(), player.getY() + 1, player.getZ(),
                        1f, 1.5f, 1f,
                        0.01, 30, false);
        });

        //Master Emerald
        if(KeyBindings.INSTANCE.transformButton.consumeClick())
        {
            PacketHandler.sendToServer(new ActivateFalseSuper());
            while(KeyBindings.INSTANCE.transformButton.consumeClick());
        }
    }

    public void onServerPlayerTick(ServerPlayer player)
    {
        //Handles Tick
        serverTick = (serverTick+1)%TICKS_PER_SEC;

        Level world = player.level();

        //Grey Emerald
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Checks to See if On Ground Before Starting
                if(chaosEmeraldCap.greyEmeraldUse == -1 && player.onGround()) {
                    chaosEmeraldCap.greyEmeraldUse = 0;
                    PacketHandler.sendToALLPlayers(new SyncDigPacketS2C(player.getId(),chaosEmeraldCap.greyEmeraldUse,player.getDeltaMovement()));
                }

                //Grey Emerald Use
                if (chaosEmeraldCap.greyEmeraldUse > 0) {
                    chaosEmeraldCap.greyEmeraldUse += 1;

                    Vec3 lookAngle = player.getLookAngle();
                    BlockPos playerPos = new BlockPos(
                            (int)(player.getX()+lookAngle.x()*1),
                            (int)(player.getY()+lookAngle.y()*1),
                            (int)(player.getZ()+lookAngle.z()*1)
                    );
                    final int radius = 1;

                    //Damage Entities
                    for(LivingEntity enemy : world.getEntitiesOfClass(LivingEntity.class,new AABB(
                            player.getX()+lookAngle.x()-1.5,player.getY()+lookAngle.y()-1.5,player.getZ()+lookAngle.z()-1.5,
                            player.getX()+lookAngle.x()+1.5,player.getY()+lookAngle.y()+1.5,player.getZ()+lookAngle.z()+1.5
                    ),(enemy)->!enemy.is(player))) {
                        enemy.hurt(world.damageSources().playerAttack(player), 4);
                    }

                    //Break Blocks
                    BlockPos start = playerPos.offset(-radius, -(radius+1), -radius);
                    BlockPos end = playerPos.offset(radius, radius+2, radius);

                    // Use BlockPos.betweenClosed to iterate over all positions in the cube
                    for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
                        BlockState blockState = player.level().getBlockState(pos);
                        if(!Utilities.unbreakableBlocks.contains(ForgeRegistries.BLOCKS.getKey(blockState.getBlock())+""))
                            player.level().destroyBlock(pos, player.isShiftKeyDown());
                    }
                    player.setDeltaMovement(lookAngle.scale(1));
                    PacketHandler.sendToALLPlayers(new SyncDigPacketS2C(player.getId(),chaosEmeraldCap.greyEmeraldUse,player.getDeltaMovement()));
                }

                //End Ability Also can be ended by pressing R-Click with Emerald Again
                if(chaosEmeraldCap.greyEmeraldUse == 60)
                {
                    //Set Cooldown(in Seconds)
                    chaosEmeraldCap.greyEmeraldUse = -1;
                    chaosEmeraldCap.cooldownKey[EmeraldType.GREY_EMERALD.ordinal()] = 30;

                    PacketHandler.sendToALLPlayers(new SyncDigPacketS2C(player.getId(),chaosEmeraldCap.greyEmeraldUse,player.getDeltaMovement()));
                }
            });
        }

        //Purple Emerald
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Negate Fall Damage
                if(chaosEmeraldCap.purpleEmeraldUse == -1 && player.onGround())
                {
                    chaosEmeraldCap.purpleEmeraldUse = 0;
                    PacketHandler.sendToALLPlayers(new SyncBlastPacketS2C(player.getId(),chaosEmeraldCap.purpleEmeraldUse));
                }

                //Increase The Purple Emerald Use
                if(chaosEmeraldCap.purpleEmeraldUse > 0)
                {
                    if(chaosEmeraldCap.purpleEmeraldUse == 1)
                        PacketHandler.sendToALLPlayers(new SyncBlastPacketS2C(player.getId(), chaosEmeraldCap.purpleEmeraldUse));

                    chaosEmeraldCap.purpleEmeraldUse += 1;
                }

                //Perform Blast
                if(chaosEmeraldCap.purpleEmeraldUse == 20)
                {
                    //Perform Blast
                    {
                        //Commands
                        CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(4).withSuppressedOutput();
                        MinecraftServer server = player.serverLevel().getServer();
                        server.
                                getCommands().
                                performPrefixedCommand(commandSourceStack,"summon firework_rocket ~ ~ ~ {Life:0,LifeTime:0,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Colors:[I;12779775,16777215],FadeColors:[I;16711680,0]}]}}}}");
                    }

                    //Damage
                    AABB boundingBox = new AABB(player.getX()+6,player.getY()+6,player.getZ()+6,
                            player.getX()-6,player.getY()-6,player.getZ()-6);
                    for(LivingEntity enemy : world.getEntitiesOfClass(LivingEntity.class,boundingBox,(enemy)->!enemy.is(player)))
                        enemy.hurt(world.damageSources().playerAttack(player),7);

                    //Sync Set Motion to Zero
                    player.setDeltaMovement(0,0,0);
                    PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(),player.getDeltaMovement()));
                }

                //End Blast
                if(chaosEmeraldCap.purpleEmeraldUse > 30)
                {
                    //Reset Gravity
                    player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
                    player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0);

                    //Blast Cooldowns
                    chaosEmeraldCap.purpleEmeraldUse = -1;
                    PacketHandler.sendToALLPlayers(new SyncBlastPacketS2C(player.getId(),chaosEmeraldCap.purpleEmeraldUse));
                    chaosEmeraldCap.cooldownKey[EmeraldType.PURPLE_EMERALD.ordinal()] = 30;
                }
            });
        }
    }
}
