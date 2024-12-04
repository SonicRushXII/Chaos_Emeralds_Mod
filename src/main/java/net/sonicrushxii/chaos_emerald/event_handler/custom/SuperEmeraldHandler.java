package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.blue.IceVerticalSpike;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;
import org.joml.Vector3f;

import java.util.List;

public class SuperEmeraldHandler {

    public static void aquaEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if (chaosEmeraldCap.superCooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)), true);
                return;
            }

            //Set Cooldown(in Seconds)
            chaosEmeraldCap.superCooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] = 25;

            //Add Step Height
            if (!pPlayer.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.BUBBLE_BOOST_STEP))
                pPlayer.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).addTransientModifier(AttributeMultipliers.BUBBLE_BOOST_STEP);
        });
    }

    public static void blueEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.superCooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)),true);
                return;
            }

            //Set Cooldown(in Seconds)
            chaosEmeraldCap.superCooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] = 25;

            //If Looking Down don't scale further
            if(pPlayer.getXRot() >= -90.0 && pPlayer.getXRot() <= -85.0)
            {
                IceVerticalSpike iceSuperSpike = new IceVerticalSpike(ModEntityTypes.ICE_SUPER_SPIKE.get(),pLevel);
                iceSuperSpike.setPos(new Vec3(pPlayer.getX(), pPlayer.getY()-0.5, pPlayer.getZ()));
                iceSuperSpike.setOwner(pPlayer.getUUID());
                pLevel.addFreshEntity(iceSuperSpike);

                return;
            }

            Vec3 startPos = new Vec3(pPlayer.getX(), pPlayer.getY()+pPlayer.getEyeHeight(), pPlayer.getZ());
            Vec3 motionDir = pPlayer.getLookAngle();

            //Look Forward and Spawn Ice spike over there
            for(byte i=0;i<16;++i)
            {
                Vec3 destPos = startPos.add(motionDir.scale(i));
                BlockPos pos = Utilities.convertToBlockPos(destPos);
                List<LivingEntity> targetEntities = pLevel.getEntitiesOfClass(
                        LivingEntity.class, new AABB(destPos.x() - 0.5, destPos.y() - 0.5, destPos.z() - 0.5,
                                destPos.x() + 0.5, destPos.y() + 0.5, destPos.z() + 0.5),
                        target -> !(target.is(pPlayer))
                );

                //If Entities are found then target them
                if(!targetEntities.isEmpty())
                {
                    //Grab the first Enemy
                    LivingEntity target = targetEntities.get(0);

                    //Grab
                    List<LivingEntity> nearbyEntities = pLevel.getEntitiesOfClass(
                            LivingEntity.class, new AABB(target.getX() - 2.5, target.getY() - 2.5, target.getZ() - 2.5,
                                                        target.getX() + 2.5, target.getY() + 2.5, target.getZ() + 2.5),
                            adjacentEnemy -> !(adjacentEnemy.is(pPlayer))
                    );
                    for(LivingEntity enemy : nearbyEntities)
                    {
                        IceVerticalSpike iceSuperSpike = new IceVerticalSpike(ModEntityTypes.ICE_SUPER_SPIKE.get(),pLevel);
                        iceSuperSpike.setPos(new Vec3(enemy.getX(), enemy.getY()-0.5, enemy.getZ()));
                        iceSuperSpike.setOwner(pPlayer.getUUID());
                        pLevel.addFreshEntity(iceSuperSpike);
                    }
                    return;
                }

                //If Block is found target them
                if(!Utilities.passableBlocks.contains(ForgeRegistries.BLOCKS.getKey(pLevel.getBlockState(pos).getBlock())+""))
                {
                    IceVerticalSpike iceSuperSpike = new IceVerticalSpike(ModEntityTypes.ICE_SUPER_SPIKE.get(),pLevel);
                    iceSuperSpike.setPos(destPos);
                    iceSuperSpike.setOwner(pPlayer.getUUID());
                    pLevel.addFreshEntity(iceSuperSpike);

                    return;
                }
            }
        });


    }

    public static void greenEmeraldUse(Level pLevel, Player pPlayer)
    {

    }
    public static void greyEmeraldUse(Level pLevel, Player pPlayer)
    {

    }

    public static void purpleEmeraldUse(Level pLevel, Player pPlayer)
    {

    }

    public static void redEmeraldUse(Level pLevel, Player pPlayer)
    {

    }

    public static void yellowEmeraldUse(Level pLevel, Player pPlayer)
    {

    }

    public static void serverTick(ServerPlayer player, int tick)
    {

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Bubble Boost
            {
                if(chaosEmeraldCap.aquaSuperUse > 0)
                {
                    //Add Timer
                    chaosEmeraldCap.aquaSuperUse += 1;
                    player.setDeltaMovement((Utilities.calculateViewVector(0,player.getYRot())));
                    player.connection.send(new ClientboundSetEntityMotionPacket(player));

                    //Particle
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            ParticleTypes.BUBBLE,
                            player.getX(), player.getY()+player.getEyeHeight()/2, player.getZ(),
                            0.001, 0.50f, 1.00f, 0.50f, 10,
                            false));
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            new DustParticleOptions(new Vector3f(0f,1f,1f),1.5f),
                            player.getX(), player.getY()+player.getEyeHeight()/2, player.getZ(),
                            0.001, 0.50f, 1.00f, 0.50f, 5,
                            false));
                }

                if(chaosEmeraldCap.aquaSuperUse == 30)
                {
                    //Reset Timer
                    chaosEmeraldCap.aquaSuperUse = 0;

                    //Remove Step Height
                    if (player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.BUBBLE_BOOST_STEP))
                        player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).removeModifier(AttributeMultipliers.BUBBLE_BOOST_STEP.getId());
                }
            }

        });
    }

    public static void clientTick(LocalPlayer player, int tick)
    {

    }
}
