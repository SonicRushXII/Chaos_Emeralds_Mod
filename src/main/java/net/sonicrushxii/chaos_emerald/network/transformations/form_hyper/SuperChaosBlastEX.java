package net.sonicrushxii.chaos_emerald.network.transformations.form_hyper;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormProperties;
import net.sonicrushxii.chaos_emerald.entities.form_hyper.SuperSpearEXEntity;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.function.Supplier;

public class SuperChaosBlastEX
{
    public static float CHAOS_BLAST_EX_DMG = 15.0F;

    public SuperChaosBlastEX() {}

    public SuperChaosBlastEX(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public static void singularChaosBlast(ServerPlayer player, Vec3 explodePos)
    {
        Level world = player.level();

        //Perform Blast
        {
            //Commands
            CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(4).withSuppressedOutput();
            MinecraftServer server = player.serverLevel().getServer();
            server.
                    getCommands().
                    performPrefixedCommand(commandSourceStack,String.format("summon firework_rocket ~%.4f ~%.4f ~%.4f {Life:0,LifeTime:0,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Flicker:1b,Colors:[I;13434876,16777215],FadeColors:[I;16760825,13893590]}]}}}}",
                            explodePos.x(),explodePos.y(),explodePos.z()));
        }

        //Damage
        AABB boundingBox = new AABB(player.getX()+explodePos.x+4,player.getY()+explodePos.y+4,player.getZ()+explodePos.z+4,
                player.getX()+explodePos.x-4,player.getY()+explodePos.y-4,player.getZ()+explodePos.z-4);
        for(LivingEntity enemy : world.getEntitiesOfClass(LivingEntity.class,boundingBox,(enemy)->!enemy.is(player)))
            enemy.hurt(world.damageSources().playerAttack(player), SuperChaosBlastEX.CHAOS_BLAST_EX_DMG);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();

                    if(player != null)
                    {
                        Level pLevel = player.level();

                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            HyperFormProperties hyperFormProperties = (HyperFormProperties) chaosEmeraldCap.formProperties;

                            //Data
                            hyperFormProperties.chaosBlastEXTimer = 1;

                            //Set Cooldown
                            hyperFormProperties.setCooldown(HyperFormAbility.SUPER_CHAOS_BLAST_EX,(byte)16);

                            //Emerald Data
                            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                                    player.getId(),chaosEmeraldCap
                            ));
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
