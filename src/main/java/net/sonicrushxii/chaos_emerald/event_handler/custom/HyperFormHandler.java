package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormProperties;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.transformations.form_hyper.ActivateHyperForm;
import net.sonicrushxii.chaos_emerald.network.transformations.form_hyper.DeactivateHyperForm;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;
import org.joml.Vector3f;

public class HyperFormHandler
{
    public static final int HYPERFORM_DURATION = 300; //SECONDS
    public static final int HYPERFORM_COOLDOWN = 10; //SECONDS

    public static void serverTick(ServerPlayer player, int tick)
    {
        //Server Tick
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Hyper Form Handler
            if (chaosEmeraldCap.hyperFormTimer != 0)
            {
                //Increase Timer
                chaosEmeraldCap.hyperFormTimer += 1;

                //Transformation Animation
                if(chaosEmeraldCap.hyperFormTimer < 0)
                {
                    //Lock in Place
                    player.setDeltaMovement(0,0,0);

                    //Display Particle
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            ParticleTypes.ELECTRIC_SPARK,
                            player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                            0.001,0.5F,player.getEyeHeight()/2,0.5F,2,false));

                    //Activation Point
                    if(chaosEmeraldCap.hyperFormTimer == -4)
                    {
                        //Play the Sound
                        player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.MASTER, 0.75f, 1.0f);

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.FLASH,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.01F, 0.01F, 0.01F, 1, true));

                        //Give Potion Effects
                        ActivateHyperForm.giveEffects(player);
                    }

                    //Flight Effects
                    {
                        //Flight
                        player.getAbilities().mayfly = true;
                        player.getAbilities().flying = !player.onGround();
                        player.onUpdateAbilities();
                    }

                    //Return Gravity
                    player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
                }

                //Activate Hyper Form
                if(chaosEmeraldCap.hyperFormTimer == 0)
                {
                    chaosEmeraldCap.hyperFormTimer = 1;
                    //Change Data
                    chaosEmeraldCap.formProperties = new HyperFormProperties();

                }

                //Hyper Form Duration
                if(chaosEmeraldCap.hyperFormTimer > 0)
                {
                    //Display Particle Every Tick
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            new DustParticleOptions(new Vector3f(0.9F, 1.0F, 1.0F),1.0F),
                            player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                            0.001,0.5F,player.getEyeHeight()/2,0.5F,2,true));
                    //Handle Flight
                    {
                        if (player.getAbilities().flying && player.isSprinting()) {
                            //Move in Direction you are looking
                            Vec3 lookAngle = player.getLookAngle().scale(1.95);
                            player.setDeltaMovement(lookAngle);
                            PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(), lookAngle));
                        }
                        //Re-enable Flight if it isn't enabled
                        if (tick == 0 && !player.getAbilities().mayfly) {
                            player.getAbilities().mayfly = true;
                            player.onUpdateAbilities();
                        }

                        //Brakes
                        if (!player.isSprinting() && player.getAbilities().flying) {
                            Vec3 movementSpeed = player.getDeltaMovement();
                            double movementCoefficient = Math.abs(movementSpeed.x) + Math.abs(movementSpeed.y) + Math.abs(movementSpeed.z);

                            if (movementCoefficient > 1.8) {
                                Vec3 motionSlow = movementSpeed.scale(0.05);
                                player.setDeltaMovement(motionSlow);
                                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(), motionSlow));
                            }
                        }
                    }

                    //Water Running
                    {
                        if (player.isSprinting() && !player.isInWater())
                        {
                            try {
                                if (ForgeRegistries.BLOCKS.getKey(player.level().getBlockState(player.blockPosition().offset(0, -1, 0)).getBlock())
                                        .equals(ForgeRegistries.BLOCKS.getKey(Blocks.WATER)) || ForgeRegistries.BLOCKS.getKey(player.level().getBlockState(player.blockPosition().offset(0, -1, 0)).getBlock())
                                        .equals(ForgeRegistries.BLOCKS.getKey(Blocks.LAVA))) {
                                    //Get Motion
                                    Vec3 playerDirection = Utilities.calculateViewVector(0,player.getYRot());

                                    if (chaosEmeraldCap.isWaterBoosting == false) {
                                        player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);
                                        chaosEmeraldCap.isWaterBoosting = true;

                                        //Slight upward
                                        playerDirection = Utilities.calculateViewVector(-1,player.getYRot());
                                    }

                                    //Move Forward
                                    player.setDeltaMovement(playerDirection.scale(3.5));
                                    player.connection.send(new ClientboundSetEntityMotionPacket(player));
                                }
                            } catch (NullPointerException ignored) {}
                        }
                    }

                    //Give the Player the Effects every Second
                    if(tick == 0)
                    {
                        //Display The Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.END_ROD,
                                player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                                0.001,0.5F,player.getEyeHeight()/2,0.5F,3,false));

                        ActivateHyperForm.giveEffects(player);
                    }

                    //Sprint Boost
                    if(player.isSprinting()) {
                        if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.HYPER_BOOST_SPEED))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(AttributeMultipliers.HYPER_BOOST_SPEED);
                    }
                    else {
                        if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.HYPER_BOOST_SPEED))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.HYPER_BOOST_SPEED);
                    }

                }

                //Hyper Form End
                if(chaosEmeraldCap.hyperFormTimer > 20*HYPERFORM_DURATION)
                    DeactivateHyperForm.performDeactivateHyper(player);
            }

            //Decrement Hyper Cooldown
            if(tick == 0)
            {
                //Give Back Chaos Emeralds
                if(chaosEmeraldCap.hyperFormCooldown == 1)  DeactivateHyperForm.giveBackSuperEmeralds(player);
                chaosEmeraldCap.hyperFormCooldown = Math.max(chaosEmeraldCap.hyperFormCooldown - 1, 0);

                //Cooldown Management
                try
                {
                    HyperFormProperties hyperFormProperties = (HyperFormProperties) chaosEmeraldCap.formProperties;
                    {
                        byte[] allCooldowns = hyperFormProperties.getAllCooldowns();
                        for (int i = 0; i < allCooldowns.length; ++i) {
                            if (allCooldowns[i] != (byte) -1)
                                allCooldowns[i] = (byte) Math.max(0, allCooldowns[i] - 1);
                        }
                    }
                }catch (ClassCastException ignored) {}
            }
            //Sync Data to Client
            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                    player.getId(),chaosEmeraldCap
            ));
        });
    }

    public static void clientTick(LocalPlayer player, int clientTick)
    {
        //Sends a Packet To Activate Hyper form if you have all Seven Emeralds.
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Transform
            if(KeyBindings.INSTANCE.transformButton.isDown() && chaosEmeraldCap.hyperFormTimer == 0 && chaosEmeraldCap.hyperFormCooldown == 0)
            {
                if(ActivateHyperForm.hasAllSuperEmeralds(player) && ActivateHyperForm.isPlayerNotWearingArmor(player)) {
                    PacketHandler.sendToServer(new ActivateHyperForm());
                    player.setDeltaMovement(0,0,0);
                }
            }

            //DeTransform
            if(KeyBindings.INSTANCE.transformButton.isDown() && (chaosEmeraldCap.hyperFormTimer > 0 && chaosEmeraldCap.hyperFormTimer < HYPERFORM_DURATION *20) && chaosEmeraldCap.hyperFormCooldown == 0) {
                PacketHandler.sendToServer(new DeactivateHyperForm());
            }

            //Lock Position
            if(chaosEmeraldCap.hyperFormTimer < 0)  player.setDeltaMovement(0,0,0);

            //Hyper Form Duration
            if(chaosEmeraldCap.hyperFormTimer > 0)
            {
                HyperFormProperties hyperFormProperties = (HyperFormProperties) chaosEmeraldCap.formProperties;

                /*if (KeyBindings.INSTANCE.useAbility1.isDown() && hyperFormProperties.getCooldown(HyperFormAbility.SUPER_CHAOS_SPEAR_EX) == 0)
                    PacketHandler.sendToServer(new SuperChaosSpearEX());

                if (KeyBindings.INSTANCE.useAbility2.isDown() && hyperFormProperties.getCooldown(HyperFormAbility.SUPER_CHAOS_CONTROL_EX) == 0)
                    PacketHandler.sendToServer(new SuperChaosControlEX());

                if (KeyBindings.INSTANCE.useAbility3.isDown() && hyperFormProperties.getCooldown(HyperFormAbility.SUPER_CHAOS_BLAST_EX) == 0)
                    PacketHandler.sendToServer(new SuperChaosBlastEX());

                if (KeyBindings.INSTANCE.useAbility4.isDown() && hyperFormProperties.getCooldown(HyperFormAbility.SUPER_CHAOS_PORTAL) == 0)
                    PacketHandler.sendToServer(new SuperPortal());*/
            }
        });
    }
}
