package net.sonicrushxii.chaos_emerald.network.transformations.form_hyper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.form_hyper.SuperEmeraldEntity;
import net.sonicrushxii.chaos_emerald.entities.form_super.ChaosEmeraldEntity;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.transformations.form_super.DeactivateSuperForm;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;

import java.util.function.Supplier;

public class ActivateHyperForm
{
    private static final int EMERALD_DURATION = 36;
    private static final float RADIUS = 2.5F;
    private static final float RADIAL_SPEED = 0.15F;
    private static final float ROTATION_SPEED = 0.16F;

    public ActivateHyperForm() {}

    public ActivateHyperForm(FriendlyByteBuf buffer){}

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

    public static boolean hasAllSuperEmeralds(Player player)
    {
        boolean hasAquaEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.AQUA_SUPER_EMERALD.get().asItem()));
        boolean hasBlueEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.BLUE_SUPER_EMERALD.get().asItem()));
        boolean hasGreenEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.GREEN_SUPER_EMERALD.get().asItem()));
        boolean hasGreyEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.GREY_SUPER_EMERALD.get().asItem()));
        boolean hasPurpleEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.PURPLE_SUPER_EMERALD.get().asItem()));
        boolean hasRedEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.RED_SUPER_EMERALD.get().asItem()));
        boolean hasYellowEmerald = hasSpecificItem(player,new ItemStack(ModBlocks.YELLOW_SUPER_EMERALD.get().asItem()));

        return hasAquaEmerald && hasBlueEmerald && hasGreenEmerald && hasGreyEmerald && hasPurpleEmerald && hasRedEmerald && hasYellowEmerald;
    }

    public static void spawnSuperEmerald(Player pPlayer, EmeraldType emeraldColor, Vec3 spawnPos, float thetaOffset)
    {
        Vec3 newSpawnPos = spawnPos.add(-RADIUS*Math.sin(thetaOffset), 0 ,-RADIUS*Math.cos(thetaOffset));

        Level pLevel = pPlayer.level();
        pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                SoundEvents.EGG_THROW, SoundSource.MASTER, 1.0f, 1.0f);
        SuperEmeraldEntity superEmeraldEntity = new SuperEmeraldEntity(ModEntityTypes.SUPER_EMERALD_ENTITY.get(), pLevel);

        superEmeraldEntity.setPos(newSpawnPos);
        superEmeraldEntity.setEmeraldType(emeraldColor);
        superEmeraldEntity.initializeDuration(EMERALD_DURATION);
        superEmeraldEntity.setCurrentRadius(RADIUS);
        superEmeraldEntity.setRadialSpeed(RADIAL_SPEED);
        superEmeraldEntity.setTheta(thetaOffset);
        superEmeraldEntity.setRotationSpeed(ROTATION_SPEED);

        // Add the entity to the world
        pLevel.addFreshEntity(superEmeraldEntity);
    }

    // Method to check if the player is not wearing any armor
    public static boolean isPlayerNotWearingArmor(Player player) {
        // Check each armor slot
        boolean noHelmet = player.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
        boolean noChestplate = player.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
        boolean noLeggings = player.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
        boolean noBoots = player.getItemBySlot(EquipmentSlot.FEET).isEmpty();

        // Return true if all armor slots are empty
        return noHelmet && noChestplate && noLeggings && noBoots;
    }

    public static void giveEffects(ServerPlayer player)
    {
        //Speed
        if(!player.hasEffect(MobEffects.MOVEMENT_SPEED)) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 3, false, false));
        else player.getEffect(MobEffects.MOVEMENT_SPEED).update(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 3, false, false));

        //Jump
        if(!player.hasEffect(MobEffects.JUMP)) player.addEffect(new MobEffectInstance(MobEffects.JUMP, -1, 4, false, false));
        else player.getEffect(MobEffects.JUMP).update(new MobEffectInstance(MobEffects.JUMP, -1, 4, false, false));

        //Haste
        if(!player.hasEffect(MobEffects.DIG_SPEED)) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 2, false, false));
        else player.getEffect(MobEffects.DIG_SPEED).update(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 2, false, false));

        //Strength
        if(!player.hasEffect(MobEffects.DAMAGE_BOOST)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 3, false, false));
        else player.getEffect(MobEffects.DAMAGE_BOOST).update(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 3, false, false));

        //Saturation
        if(!player.hasEffect(MobEffects.SATURATION)) player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 1, false, false));
        else player.getEffect(MobEffects.SATURATION).update(new MobEffectInstance(MobEffects.SATURATION, 20, 1, false, false));

        //Hero Of the Village
        if(!player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, -1, 1, false, false));
        else player.getEffect(MobEffects.HERO_OF_THE_VILLAGE).update(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, -1, 1, false, false));

        //Resistance
        if(!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 2, false, false));
        else player.getEffect(MobEffects.DAMAGE_RESISTANCE).update(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 2, false, false));

        //Fire Resistance
        if(!player.hasEffect(MobEffects.FIRE_RESISTANCE)) player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 1, false, false));
        else player.getEffect(MobEffects.FIRE_RESISTANCE).update(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 1, false, false));

        //Add Step Height
        if (!player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.HYPER_STEP_ADDITION))
            player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).addTransientModifier(AttributeMultipliers.HYPER_STEP_ADDITION);

        //Add Armor
        if (!player.getAttribute(Attributes.ARMOR).hasModifier(AttributeMultipliers.HYPER_ARMOR))
            player.getAttribute(Attributes.ARMOR).addTransientModifier(AttributeMultipliers.HYPER_ARMOR);

        //Add Walk Speed
        if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.HYPER_WALK_SPEED))
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(AttributeMultipliers.HYPER_WALK_SPEED);

        //Add KB Resistance
        if (!player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(AttributeMultipliers.HYPER_KB_RESIST))
            player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(AttributeMultipliers.HYPER_KB_RESIST);
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
                            if(chaosEmeraldCap.falseSuperTimer < 0 || chaosEmeraldCap.superFormTimer < 0) return;

                            //If Super is on Turn off Super
                            if(chaosEmeraldCap.superFormTimer > 0) DeactivateSuperForm.performDeactivateSuper(player);

                            clearSpecificItem(player, new ItemStack(ModBlocks.AQUA_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.AQUA_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,0F);
                            clearSpecificItem(player, new ItemStack(ModBlocks.BLUE_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.BLUE_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,0.8975979F);
                            clearSpecificItem(player, new ItemStack(ModBlocks.GREEN_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.GREEN_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,1.7951958F);
                            clearSpecificItem(player, new ItemStack(ModBlocks.GREY_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.GREY_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,2.6927937F);
                            clearSpecificItem(player, new ItemStack(ModBlocks.PURPLE_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.PURPLE_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,3.5903916F);
                            clearSpecificItem(player, new ItemStack(ModBlocks.RED_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.RED_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,4.48798951F);
                            clearSpecificItem(player, new ItemStack(ModBlocks.YELLOW_SUPER_EMERALD.get().asItem()));
                            spawnSuperEmerald(player, EmeraldType.YELLOW_EMERALD, new Vec3(player.getX(),player.getY()+1.0,player.getZ()) ,5.38558741F);

                            //Remove Gravity
                            player.setDeltaMovement(0,0,0);
                            player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);

                            //Set Super Time
                            chaosEmeraldCap.hyperFormTimer = -40;

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
