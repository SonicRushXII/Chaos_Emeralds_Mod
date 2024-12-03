package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.blue.IceVerticalSpike;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;

import java.util.List;

public class SuperEmeraldHandler {

    public static void aquaEmeraldUse(Level pLevel, Player pPlayer)
    {

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
}
