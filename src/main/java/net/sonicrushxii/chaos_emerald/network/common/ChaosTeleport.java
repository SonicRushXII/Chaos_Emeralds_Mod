package net.sonicrushxii.chaos_emerald.network.common;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.level.GameType;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldAbility;
import net.sonicrushxii.chaos_emerald.event_handler.custom.ChaosEmeraldHandler;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;

public class ChaosTeleport
{
    public static void keyPress(ServerPlayer player)
    {
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap ->
        {
            //Activate Teleport
            if (chaosEmeraldCap.teleport == 0 && chaosEmeraldCap.chaosCooldownKey[EmeraldAbility.TELEPORT.ordinal()] == 0) {
                chaosEmeraldCap.teleport = -ChaosEmeraldHandler.TELEPORT_BUILDUP;
                player.displayClientMessage(Component.translatable("Teleport!").withStyle(Style.EMPTY.withColor(0xFFFFFF)),true);
            }

            //Deactivate Teleport
            else if (chaosEmeraldCap.teleport > 0) {
                endTeleport(player);
            }

            //Cooldown not set
            else if(chaosEmeraldCap.chaosCooldownKey[EmeraldAbility.TELEPORT.ordinal()] > 0){
                player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xFFFFFF)),true);
            }

            //Error Handling
            else
            {
                player.displayClientMessage(Component.translatable("That Ability cannot be used currently").withStyle(Style.EMPTY.withColor(0xFFFFFF)),true);
            }

            //Sync Data
            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(),chaosEmeraldCap));
        });
    }

    public static void startTeleport(ServerPlayer player)
    {
        //Freeze World
        TickRateManager tickRateManager = player.serverLevel().tickRateManager();
        tickRateManager.setFrozen(true);

        //Store Current Game Mode
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap ->
        {
            chaosEmeraldCap.prevGameMode = (byte) player.gameMode.getGameModeForPlayer().getId();
            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(),chaosEmeraldCap));
        });

        //Change Gamemode to Spectator
        player.setGameMode(GameType.SPECTATOR);
    }

    public static void endTeleport(ServerPlayer player)
    {
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap ->
        {
            //Reset Data
            chaosEmeraldCap.teleport = 0;

            //Particle Effects
            player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.MASTER, 1.0f, 1.0f);

            //Particle
            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                    ParticleTypes.FLASH,
                    player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                    0.001,0.01F,player.getEyeHeight()/2,0.01F,
                    1,true));

            //Reset World
            player.serverLevel().tickRateManager().setFrozen(false);

            //Cooldown
            chaosEmeraldCap.chaosCooldownKey[EmeraldAbility.TELEPORT.ordinal()] = ChaosEmeraldHandler.TELEPORT_COOLDOWN;

            //Change Gamemode back
            player.setGameMode(GameType.byId(chaosEmeraldCap.prevGameMode));

            //Sync Data
            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(),chaosEmeraldCap));
        });
    }
}
