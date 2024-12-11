package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.aqua.SuperAquaBubbleEntity;
import net.sonicrushxii.chaos_emerald.entities.blue.IceVerticalSpike;
import net.sonicrushxii.chaos_emerald.entities.false_super.ChaosSpaz;
import net.sonicrushxii.chaos_emerald.entities.green.ChaosDiveRipple;
import net.sonicrushxii.chaos_emerald.entities.purple.SuperChaosSlicer;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.red.SuperInfernoParticleS2C;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;
import net.sonicrushxii.chaos_emerald.scheduler.Scheduler;
import org.joml.Vector3f;

import java.util.List;

public class SuperEmeraldHandler {

    public static void aquaEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(pPlayer instanceof ServerPlayer player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.isUsingActiveAbility()) return;

                if (chaosEmeraldCap.superCooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] > 0) {
                    player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FFFF)), true);
                    return;
                }

                //Super Aqua Emerald
                chaosEmeraldCap.aquaSuperUse = 1;

                //Get Saturation
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION,6,0,false,false,false),player);

                //Add Step Height
                if (!player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.BUBBLE_BOOST_STEP))
                    player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).addTransientModifier(AttributeMultipliers.BUBBLE_BOOST_STEP);

                //Add Speed
                if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.BUBBLE_BOOST_SPEED))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(AttributeMultipliers.BUBBLE_BOOST_SPEED);

                //Sync To Client
                PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                        player.getId(),chaosEmeraldCap
                ));
            });

        }

    }

    public static void blueEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(pPlayer instanceof ServerPlayer player) {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                try {
                    if (chaosEmeraldCap.isUsingActiveAbility()) return;

                    if (chaosEmeraldCap.superCooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] > 0) {
                        player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)), true);
                        return;
                    }

                    //If Looking Down don't scale further
                    if (player.getXRot() >= 85.0 && player.getXRot() <= 90.0) {
                        IceVerticalSpike iceSuperSpike = new IceVerticalSpike(ModEntityTypes.ICE_SUPER_SPIKE.get(), pLevel);
                        iceSuperSpike.setPos(new Vec3(player.getX(), player.getY() + 0.1, player.getZ()));
                        iceSuperSpike.setOwner(player.getUUID());
                        iceSuperSpike.setMovementDirection(
                                new Vec3(0, 1, 0)
                                        .add(Utilities.calculateViewVector(0, player.getYRot()).scale(0.4))
                        );

                        Vec3 launchVec = Utilities.calculateViewVector(-65f, player.getYRot()).scale(1.85);
                        player.setDeltaMovement(launchVec);
                        PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(), launchVec));
                        pLevel.addFreshEntity(iceSuperSpike);

                        //Give Padding Effect
                        Scheduler.scheduleTask(()->{
                            MobEffectInstance iceLaunchEffect = new MobEffectInstance(ModEffects.SUPER_ICE_LAUNCH.get(), 200, 0, false, false, false);
                            if (player.hasEffect(ModEffects.SUPER_CHAOS_DIVE.get()))    player.getEffect(ModEffects.SUPER_ICE_LAUNCH.get()).update(iceLaunchEffect);
                            else                                                        player.addEffect(iceLaunchEffect, player);
                            player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(),iceLaunchEffect));
                        },2);

                        return;
                    }

                    Vec3 startPos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                    Vec3 motionDir = player.getLookAngle();

                    //Look Forward and Spawn Ice spike over there
                    for (byte i = 0; i < 16; ++i) {
                        Vec3 destPos = startPos.add(motionDir.scale(i));
                        BlockPos pos = Utilities.convertToBlockPos(destPos);
                        List<LivingEntity> targetEntities = pLevel.getEntitiesOfClass(
                                LivingEntity.class, new AABB(destPos.x() - 0.5, destPos.y() - 0.5, destPos.z() - 0.5,
                                        destPos.x() + 0.5, destPos.y() + 0.5, destPos.z() + 0.5),
                                target -> !(target.is(player))
                        );

                        //If Entities are found then target them
                        if (!targetEntities.isEmpty()) {
                            //Grab the first Enemy
                            LivingEntity target = targetEntities.get(0);

                            //Grab
                            List<LivingEntity> nearbyEntities = pLevel.getEntitiesOfClass(
                                    LivingEntity.class, new AABB(target.getX() - 2.5, target.getY() - 2.5, target.getZ() - 2.5,
                                            target.getX() + 2.5, target.getY() + 2.5, target.getZ() + 2.5),
                                    adjacentEnemy -> !(adjacentEnemy.is(player))
                            );
                            for (LivingEntity enemy : nearbyEntities) {
                                IceVerticalSpike iceSuperSpike = new IceVerticalSpike(ModEntityTypes.ICE_SUPER_SPIKE.get(), pLevel);
                                iceSuperSpike.setPos(new Vec3(enemy.getX(), enemy.getY(), enemy.getZ()));
                                iceSuperSpike.setOwner(player.getUUID());
                                iceSuperSpike.setMovementDirection(new Vec3(0, 1, 0)
                                        .add(destPos.subtract(
                                                        new Vec3(
                                                                player.getX(),
                                                                player.getY(),
                                                                player.getZ()
                                                        )
                                                ).normalize().scale(0.4)
                                        )
                                );
                                pLevel.addFreshEntity(iceSuperSpike);
                            }

                            return;
                        }

                        //If Block is found target them
                        if (!Utilities.passableBlocks.contains(ForgeRegistries.BLOCKS.getKey(pLevel.getBlockState(pos).getBlock()) + "")) {
                            IceVerticalSpike iceSuperSpike = new IceVerticalSpike(ModEntityTypes.ICE_SUPER_SPIKE.get(), pLevel);
                            iceSuperSpike.setPos(destPos.add(0, 1, 0));
                            iceSuperSpike.setOwner(player.getUUID());
                            iceSuperSpike.setMovementDirection(new Vec3(0, 1, 0)
                                    .add(destPos.subtract(
                                                    new Vec3(
                                                            player.getX(),
                                                            player.getY(),
                                                            player.getZ()
                                                    )
                                            ).normalize().scale(0.4)
                                    )
                            );
                            pLevel.addFreshEntity(iceSuperSpike);

                            return;
                        }
                    }
                }
                finally {
                    PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                            player.getId(),chaosEmeraldCap
                    ));
                }
            });
        }
    }

    public static void greenEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(pPlayer instanceof ServerPlayer player) {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if (chaosEmeraldCap.greenSuperUse > 0 && !player.onGround()) {
                    Vec3 motionDir = Utilities.calculateViewVector(0, player.getYRot()).scale(0.3);
                    player.addDeltaMovement(motionDir);
                    PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(),player.getDeltaMovement().add(motionDir)));
                }

                if (chaosEmeraldCap.isUsingActiveAbility()) return;

                if (chaosEmeraldCap.superCooldownKey[EmeraldType.GREEN_EMERALD.ordinal()] > 0) {
                    player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FF00)), true);
                    return;
                }

                //Super Green Emerald
                chaosEmeraldCap.greenSuperUse = 1;

                //Give Effect
                MobEffectInstance diveEffect = new MobEffectInstance(ModEffects.SUPER_CHAOS_DIVE.get(), 120, 0, false, false, false);
                if (player.hasEffect(ModEffects.SUPER_CHAOS_DIVE.get()))    player.getEffect(ModEffects.SUPER_CHAOS_DIVE.get()).update(diveEffect);
                else                                                        player.addEffect(diveEffect, player);

                Vec3 launchVec = Utilities.calculateViewVector(-65f, player.getYRot()).scale(1.05);
                player.setDeltaMovement(launchVec);
                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(), launchVec));

                PacketHandler.sendToALLPlayers( new EmeraldDataSyncS2C(
                        pPlayer.getId(),chaosEmeraldCap
                ));
            });
        }
    }

    public static void greyEmeraldUse(Level pLevel, Player pPlayer)
    {

    }

    public static void purpleEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(pPlayer instanceof ServerPlayer player) {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if (chaosEmeraldCap.isUsingActiveAbility()) return;

                if (chaosEmeraldCap.superCooldownKey[EmeraldType.PURPLE_EMERALD.ordinal()] > 0) {
                    player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xFF00FF)), true);
                    return;
                }

                chaosEmeraldCap.purpleSuperUse = 1;

                PacketHandler.sendToALLPlayers( new EmeraldDataSyncS2C(
                        pPlayer.getId(),chaosEmeraldCap
                ));
            });
        }
    }

    public static void redEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(pPlayer instanceof ServerPlayer player) {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if (chaosEmeraldCap.isUsingActiveAbility()) return;

                if (chaosEmeraldCap.superCooldownKey[EmeraldType.RED_EMERALD.ordinal()] > 0) {
                    player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xFF00FF)), true);
                    return;
                }

                chaosEmeraldCap.redSuperUse = 1;

                PacketHandler.sendToALLPlayers( new EmeraldDataSyncS2C(
                        pPlayer.getId(),chaosEmeraldCap
                ));
            });
        }
    }

    public static void yellowEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(pPlayer instanceof ServerPlayer player) {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if (chaosEmeraldCap.isUsingActiveAbility()) return;

                if (chaosEmeraldCap.superCooldownKey[EmeraldType.YELLOW_EMERALD.ordinal()] > 0) {
                    player.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FFFF)), true);
                    return;
                }

                //Super Yellow Emerald
                chaosEmeraldCap.yellowSuperUse = 1;

                //Add Slowness
                if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.GAMBIT_SLOW))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(AttributeMultipliers.GAMBIT_SLOW);

                PacketHandler.sendToALLPlayers( new EmeraldDataSyncS2C(
                        pPlayer.getId(),chaosEmeraldCap
                ));
            });
        }
    }

    public static void serverTick(ServerPlayer player, int tick)
    {
        Level world = player.level();
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            try {

                //Bubble Boost
                {
                    if (chaosEmeraldCap.aquaSuperUse > 0) {
                        //Add Timer
                        chaosEmeraldCap.aquaSuperUse += 1;

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.BUBBLE,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.50f, 1.00f, 0.50f, 10,
                                false));
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                new DustParticleOptions(new Vector3f(0f, 1f, 1f), 1.5f),
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.50f, 1.00f, 0.50f, 5,
                                false));

                        //Spawn Bubbles
                        if (player.isSprinting()) {
                            //Spawn Right Bubble
                            SuperAquaBubbleEntity superRightBubble = new SuperAquaBubbleEntity(ModEntityTypes.AQUA_BOOST_BUBBLE.get(), player.serverLevel());
                            superRightBubble.setPos(new Vec3(player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ()));
                            superRightBubble.setDuration(40);
                            superRightBubble.setOwner(player.getUUID());
                            superRightBubble.setMovementDirection(player.getLookAngle().scale(Utilities.random.nextFloat(0.5f, 1.5f)).cross(new Vec3(0, 1, 0)));

                            player.level().addFreshEntity(superRightBubble);

                            //Spawn Left Bubble
                            SuperAquaBubbleEntity superLeftBubble = new SuperAquaBubbleEntity(ModEntityTypes.AQUA_BOOST_BUBBLE.get(), player.serverLevel());
                            superLeftBubble.setPos(new Vec3(player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ()));
                            superLeftBubble.setDuration(40);
                            superLeftBubble.setOwner(player.getUUID());
                            superLeftBubble.setMovementDirection(player.getLookAngle().scale(Utilities.random.nextFloat(0.5f, 1.5f)).cross(new Vec3(0, -1, 0)));

                            player.level().addFreshEntity(superLeftBubble);
                        }

                    }

                    if (chaosEmeraldCap.aquaSuperUse == 100) {
                        //Reset Timer
                        chaosEmeraldCap.aquaSuperUse = 0;

                        //Remove Step Height
                        if (player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.BUBBLE_BOOST_STEP))
                            player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).removeModifier(AttributeMultipliers.BUBBLE_BOOST_STEP.getId());

                        //Remove Speed
                        if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.BUBBLE_BOOST_SPEED))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.BUBBLE_BOOST_SPEED.getId());

                        //Set Cooldown(in Seconds)
                        chaosEmeraldCap.superCooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] = 1;
                    }
                }

                //Chaos Dive
                {
                    //Duration
                    if (chaosEmeraldCap.greenSuperUse > 0) {
                        //Add Timer
                        chaosEmeraldCap.greenSuperUse += 1;

                        //Particle
                        if(chaosEmeraldCap.greenSuperUse < 38)
                        {
                            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                    ParticleTypes.HAPPY_VILLAGER,
                                    player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                    0.001, 0.50f, 1.00f, 0.50f, 2,
                                    false));
                            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                    new DustParticleOptions(new Vector3f(0f, 1f, 0f), 1.5f),
                                    player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                    0.001, 0.50f, 1.00f, 0.50f, 2,
                                    false));
                        }
                        else
                        {
                            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                    ParticleTypes.HAPPY_VILLAGER,
                                    player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                    0.001, 0.50f, 1.00f, 0.50f, 1,
                                    false));
                        }
                    }

                    //Move to touching the ground later
                    if (chaosEmeraldCap.greenSuperUse > 3 && chaosEmeraldCap.greenSuperUse < 38 && player.onGround())
                        chaosEmeraldCap.greenSuperUse = 38;

                    //Lock Ground Position
                    if (chaosEmeraldCap.greenSuperUse > 3 && player.onGround()) {
                        player.setDeltaMovement(0, player.getDeltaMovement().y(), 0);
                        PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(), new Vec3(0, player.getDeltaMovement().y(), 0)));

                        //Spawn Ripples
                        if((chaosEmeraldCap.greenSuperUse-1) % 20 == 0)
                        {
                            Vec3 spawnPos = new Vec3(player.getX(),
                                    player.getY()-0.1,
                                    player.getZ());
                            world.playSound(null,player.getX(),player.getY(),player.getZ(),
                                    SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.MASTER, 0.4f, 2.0f);
                            ChaosDiveRipple chaosDiveRipple = new ChaosDiveRipple(ModEntityTypes.CHAOS_DIVE_RIPPLE.get(), world);
                            chaosDiveRipple.setPos(spawnPos);
                            chaosDiveRipple.setOwner(player.getUUID());
                            chaosDiveRipple.setLevelGround(player.isShiftKeyDown());
                            chaosDiveRipple.setDuration(50);

                            // Add the entity to the world
                            world.addFreshEntity(chaosDiveRipple);
                        }
                    }

                    if (chaosEmeraldCap.greenSuperUse > 115) {
                        //Reset Timer
                        chaosEmeraldCap.greenSuperUse = 0;

                        //Set Cooldown(in Seconds)
                        chaosEmeraldCap.superCooldownKey[EmeraldType.GREEN_EMERALD.ordinal()] = 1;

                        //Remove Effect
                        if (player.hasEffect(ModEffects.SUPER_CHAOS_DIVE.get()))
                            player.removeEffect(ModEffects.SUPER_CHAOS_DIVE.get());
                    }
                }

                //Chaos Gambit
                {
                    if (chaosEmeraldCap.yellowSuperUse > 0) {
                        //Add Timer
                        chaosEmeraldCap.yellowSuperUse += 1;

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.ELECTRIC_SPARK,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.50f, 1.00f, 0.50f, 4,
                                false));
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                new DustParticleOptions(new Vector3f(1f, 1f, 0f), 1.5f),
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.50f, 1.00f, 0.50f, 2,
                                false));

                        player.setDeltaMovement(0, 0, 0);
                        PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(), new Vec3(0, 0, 0)));
                    }

                    if (chaosEmeraldCap.yellowSuperUse == 10) {
                        chaosEmeraldCap.atkRotPhaseX = player.getXRot();
                        chaosEmeraldCap.atkRotPhaseY = player.getYRot();
                    }

                    if (chaosEmeraldCap.yellowSuperUse > 10)
                    {
                        Vec3 playerPos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                        Vec3 lookAngle = Utilities.calculateViewVector(chaosEmeraldCap.atkRotPhaseX, chaosEmeraldCap.atkRotPhaseY);

                        for (int i = 1; i <= Math.min(chaosEmeraldCap.yellowSuperUse - 10, 24); i += 1) {
                            Vec3 destPos = playerPos.add(lookAngle.scale(i * 1.8));

                            //Display Particles in Gaussian Distribution
                            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                    ParticleTypes.END_ROD,
                                    destPos.x, destPos.y, destPos.z,
                                    0.001, 1.95f, 1.75f, 1.95f, 4,
                                    false));
                            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                    new DustParticleOptions(new Vector3f(1f, 1f, 0f), 2f),
                                    destPos.x, destPos.y, destPos.z,
                                    0.001, 1.95f, 1.75f, 1.95f, 8,
                                    true));

                            for (BlockPos pos : BlockPos.betweenClosed(Utilities.convertToBlockPos(destPos).offset(-1,-1,-1),
                                    Utilities.convertToBlockPos(destPos).offset(1,1,1)))
                            {
                                BlockState blockState = player.level().getBlockState(pos);
                                if(!Utilities.unbreakableBlocks.contains(ForgeRegistries.BLOCKS.getKey(blockState.getBlock())+""))
                                    player.level().destroyBlock(pos, player.isShiftKeyDown());
                            }

                            //Deal Damage
                            for(LivingEntity enemy : world.getEntitiesOfClass(LivingEntity.class, new AABB(
                                    destPos.x+2.75, destPos.y+2.75, destPos.z+2.75,
                                    destPos.x-2.75, destPos.y-2.75, destPos.z-2.75
                                    ), (enemy)->(!enemy.is(player))
                            ))
                            {
                                //Damage
                                enemy.hurt(player.damageSources().playerAttack(player), 50.0f);

                                //Updated Motion
                                Vec3 motionDirection = (destPos).subtract(new Vec3(enemy.getX(),enemy.getY(),enemy.getZ())).scale(0.3);
                                enemy.setDeltaMovement(motionDirection);
                                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(enemy.getId(),motionDirection));
                            }
                        }
                    }

                    if (chaosEmeraldCap.yellowSuperUse > 115) {
                        //Reset Timer
                        chaosEmeraldCap.yellowSuperUse = 0;

                        //Remove Slowness
                        if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.GAMBIT_SLOW))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.GAMBIT_SLOW.getId());

                        //Apply Tired Effects
                        MobEffectInstance blindnessEffect = new MobEffectInstance(MobEffects.BLINDNESS, 20, 4, false, false, false);
                        if (player.hasEffect(MobEffects.BLINDNESS))    player.getEffect(MobEffects.BLINDNESS).update(blindnessEffect);
                        else                                           player.addEffect(blindnessEffect, player);

                        MobEffectInstance weaknessEffect = new MobEffectInstance(MobEffects.WEAKNESS, 20, 4, false, false, false);
                        if (player.hasEffect(MobEffects.WEAKNESS))    player.getEffect(MobEffects.WEAKNESS).update(weaknessEffect);
                        else                                           player.addEffect(weaknessEffect, player);

                        MobEffectInstance slownessEffect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4, false, false, false);
                        if (player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))    player.getEffect(MobEffects.MOVEMENT_SLOWDOWN).update(slownessEffect);
                        else                                           player.addEffect(slownessEffect, player);

                        //Set Cooldown(in Seconds)
                        chaosEmeraldCap.superCooldownKey[EmeraldType.YELLOW_EMERALD.ordinal()] = 1;
                    }
                }

                //Chaos Slicer
                {
                    if(chaosEmeraldCap.purpleSuperUse > 0)
                    {
                        chaosEmeraldCap.purpleSuperUse += 1;

                        //Throw Slicer Projectile
                        if(chaosEmeraldCap.purpleSuperUse == 12 || chaosEmeraldCap.purpleSuperUse == 22)
                        {
                            SuperChaosSlicer superChaosSlicer = new SuperChaosSlicer(ModEntityTypes.CHAOS_SLICER.get(), player.level());

                            Vec3 sideDir = player.getLookAngle().cross(new Vec3(0,(chaosEmeraldCap.purpleSuperUse==22)?-1:1,0));

                            Vec3 spawnPos = new Vec3(player.getX()+player.getLookAngle().x,
                                    player.getY()+player.getLookAngle().y+1.0,
                                    player.getZ()+player.getLookAngle().z).add(sideDir);

                            superChaosSlicer.setPos(spawnPos);
                            superChaosSlicer.setDuration(120);
                            superChaosSlicer.setMovementDirection(player.getLookAngle());
                            superChaosSlicer.setOwner(player.getUUID());
                            superChaosSlicer.setInverted( chaosEmeraldCap.purpleSuperUse==22 );

                            // Add the entity to the world
                            player.level().addFreshEntity(superChaosSlicer);
                        }

                        //End Chaos Inferno
                        if(chaosEmeraldCap.purpleSuperUse > 25) {
                            chaosEmeraldCap.purpleSuperUse = 0;
                            //Set Cooldown(in Seconds)
                            chaosEmeraldCap.superCooldownKey[EmeraldType.PURPLE_EMERALD.ordinal()] = 1;
                        }
                    }
                }

                //Chaos Inferno
                {
                    if(chaosEmeraldCap.redSuperUse > 0)
                    {
                        chaosEmeraldCap.redSuperUse += 1;

                        //Super Inferno
                        if(tick % 4 == 0) {
                            PacketHandler.sendToALLPlayers(new SuperInfernoParticleS2C(
                                    player.getX(), player.getY()+player.getEyeHeight()/3, player.getZ(), (byte)(tick/4+1)
                            ));

                            for(LivingEntity enemy : player.level().getEntitiesOfClass(LivingEntity.class,
                                    new AABB(player.getX()+3.0,player.getY()-1.0,player.getZ()+3.0,
                                            player.getX()-3.0,player.getY()+5.0,player.getZ()-3.0),
                                    (entity)->!entity.is(player)))
                            {
                                //Damage Enemy
                                enemy.hurt(player.damageSources().playerAttack(player), 2.0f);
                                if(Utilities.random.nextInt(100) < 20)  enemy.setRemainingFireTicks(200);
                            }
                        }

                        //Display Particle Every Second
                        if (tick == 0) {
                            PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                    ParticleTypes.LAVA,
                                    player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                    0.001, 0.5F, player.getEyeHeight() / 2, 0.5F, 3, false));
                        }
                    }

                    //End Chaos Inferno
                    if(chaosEmeraldCap.redSuperUse > 600) {
                        chaosEmeraldCap.redSuperUse = 0;
                        //Set Cooldown(in Seconds)
                        chaosEmeraldCap.superCooldownKey[EmeraldType.RED_EMERALD.ordinal()] = 1;
                    }
                }

            }finally
            {
                PacketHandler.sendToALLPlayers( new EmeraldDataSyncS2C(
                        player.getId(),chaosEmeraldCap
                ));
            }
        });
    }

    public static void clientTick(LocalPlayer player, int tick)
    {
    }
}
