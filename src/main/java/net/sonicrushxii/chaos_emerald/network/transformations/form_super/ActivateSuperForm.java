package net.sonicrushxii.chaos_emerald.network.transformations.form_super;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.form_super.ChaosEmeraldEntity;
import net.sonicrushxii.chaos_emerald.entities.yellow.ChaosSpear;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.modded.ModItems;
import net.sonicrushxii.chaos_emerald.modded.ModSounds;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;

import java.util.function.Supplier;

public class ActivateSuperForm
{
    private static final int EMERALD_DURATION = 36;
    private static final float RADIUS = 2.5F;
    private static final float RADIAL_SPEED = 0.15F;
    private static final float ROTATION_SPEED = 0.16F;

    public ActivateSuperForm() {}

    public ActivateSuperForm(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public static boolean hasSpecificItem(Player player, ItemStack targetItem) {
        // Iterate through the player's inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            // Check if the current stack matches the target item
            if (ItemStack.isSameItemSameTags(stack, targetItem)) {
                return true;
            }
        }
        return false;
    }

    public static void clearSpecificItem(Player player, ItemStack targetItem) {
        // Loop through the player's inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            // Check if the current stack matches the target item
            if (ItemStack.isSameItemSameTags(stack, targetItem)) {
                player.getInventory().setItem(i, ItemStack.EMPTY); // Clear the slot
            }
        }
    }

    public static boolean hasAllChaosEmeralds(Player player)
    {
        boolean hasAquaEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.AQUA_CHAOS_EMERALD.get().asItem()));
        boolean hasBlueEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.BLUE_CHAOS_EMERALD.get().asItem()));
        boolean hasGreenEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.GREEN_CHAOS_EMERALD.get().asItem()));
        boolean hasGreymerald = hasSpecificItem(player,new ItemStack(ModBlocks.GREY_CHAOS_EMERALD.get().asItem()));
        boolean hasPurpleEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.PURPLE_CHAOS_EMERALD.get().asItem()));
        boolean hasRedEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.RED_CHAOS_EMERALD.get().asItem()));
        boolean hasYellowEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.YELLOW_CHAOS_EMERALD.get().asItem()));

        return hasAquaEmerald && hasBlueEmerald && hasGreenEmerald && hasGreymerald && hasPurpleEmerald && hasRedEmerald && hasYellowEmerald;
    }

    public static void spawnChaosEmerald(Player pPlayer, EmeraldType emeraldColor, Vec3 spawnPos, float thetaOffset)
    {
        Vec3 newSpawnPos = spawnPos.add(-RADIUS*Math.sin(thetaOffset), 0 ,-RADIUS*Math.cos(thetaOffset));

        Level pLevel = pPlayer.level();
        pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                SoundEvents.EGG_THROW, SoundSource.MASTER, 1.0f, 1.0f);
        ChaosEmeraldEntity chaosEmeraldEntity = new ChaosEmeraldEntity(ModEntityTypes.CHAOS_EMERALD_ENTITY.get(), pLevel);

        chaosEmeraldEntity.setPos(newSpawnPos);
        chaosEmeraldEntity.setEmeraldType(emeraldColor);
        chaosEmeraldEntity.initializeDuration(EMERALD_DURATION);
        chaosEmeraldEntity.setCurrentRadius(RADIUS);
        chaosEmeraldEntity.setRadialSpeed(RADIAL_SPEED);
        chaosEmeraldEntity.setTheta(thetaOffset);
        chaosEmeraldEntity.setRotationSpeed(ROTATION_SPEED);

        // Add the entity to the world
        pLevel.addFreshEntity(chaosEmeraldEntity);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    //Check if Standing on the Master Emerald
                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            //Don't Play if any other Transformations are going on
                            if(chaosEmeraldCap.falseSuperTimer < 0 || chaosEmeraldCap.hyperFormTimer != 0) return;

                            //clearSpecificItem(player, new ItemStack(ModBlocks.AQUA_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.AQUA_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,0F);
                            //clearSpecificItem(player, new ItemStack(ModBlocks.BLUE_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.BLUE_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,0.8975979F);
                            //clearSpecificItem(player, new ItemStack(ModBlocks.GREEN_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.GREEN_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,1.7951958F);
                            //clearSpecificItem(player, new ItemStack(ModBlocks.GREY_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.GREY_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,2.6927937F);
                            //clearSpecificItem(player, new ItemStack(ModBlocks.PURPLE_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.PURPLE_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,3.5903916F);
                            //clearSpecificItem(player, new ItemStack(ModBlocks.RED_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.RED_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,4.48798951F);
                            //clearSpecificItem(player, new ItemStack(ModBlocks.YELLOW_CHAOS_EMERALD.get().asItem()));
                            spawnChaosEmerald(player, EmeraldType.YELLOW_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,5.38558741F);

                            //Remove Gravity
                            player.setDeltaMovement(0,0,0);
                            player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);

                            //Set Super Time
                            chaosEmeraldCap.superFormTimer = -40;

                            //Play the Sound
                            player.level().playSound(null,player.getX(),player.getY(),player.getZ(),SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 1.0f, 1.0f);

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
