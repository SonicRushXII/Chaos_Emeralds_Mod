package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.blue.IceHorizontalSpike;
import net.sonicrushxii.chaos_emerald.entities.yellow.ChaosSpear;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.grey.SyncDigPacketS2C;
import net.sonicrushxii.chaos_emerald.network.purple.SyncBlastPacketS2C;
import net.sonicrushxii.chaos_emerald.network.red.FireSyncPacketS2C;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.List;

public class ChaosEmeraldHandler {
    public static void aquaEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FFFF)),true);
                return;
            }

            Vec3 lookAngle = pPlayer.getLookAngle().scale(2);
            Vec3 displayPos = new Vec3(
                    pPlayer.getX()+lookAngle.x(),
                    pPlayer.getY()+lookAngle.y()+pPlayer.getEyeHeight(),
                    pPlayer.getZ()+lookAngle.z()
            );

            //Playsound
            pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                    SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.MASTER, 0.75f, 0.75f);
            pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                    SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.MASTER, 0.75f, 0.75f);

            //Particle Effects
            if(pLevel.isClientSide) {
                Utilities.displayParticle(pLevel, ParticleTypes.BUBBLE,displayPos.x(),displayPos.y(),displayPos.z(),
                        3.5f, 3.5f, 3.5f,
                        0.01, 30, false);
                Utilities.displayParticle(pLevel, ParticleTypes.ENCHANTED_HIT,displayPos.x(),displayPos.y(),displayPos.z(),
                        3.5f, 3.5f, 3.5f,
                        0.01, 100, true);
            }

            //Entity Effects
            lookAngle = pPlayer.getLookAngle().scale(0.2);
            for(LivingEntity target : pLevel.getEntitiesOfClass(LivingEntity.class,
                    new AABB(displayPos.x()+3.5,displayPos.y()+3.5,displayPos.z()+3.5,
                            displayPos.x()-3.5,displayPos.y()-3.5,displayPos.z()-3.5),
                    (enemy)->!enemy.is(pPlayer))
            )
            {
                target.addEffect(new MobEffectInstance(ModEffects.CHAOS_BIND.get(), 200, 0, false, false, false));
                target.addDeltaMovement(new Vec3(lookAngle.x,0.15,lookAngle.z));
            }

            //Set Cooldown(in Seconds)
            chaosEmeraldCap.chaosCooldownKey[EmeraldType.AQUA_EMERALD.ordinal()] = 40;
        });
    }

    public static void blueEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)),true);
                return;
            }

            Vec3 spawnPos = new Vec3(pPlayer.getX()+pPlayer.getLookAngle().x,
                    pPlayer.getY()+pPlayer.getLookAngle().y+1.0,
                    pPlayer.getZ()+pPlayer.getLookAngle().z);
            pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                    SoundEvents.EGG_THROW, SoundSource.MASTER, 1.0f, 1.0f);
            IceHorizontalSpike iceHorizontalSpike = new IceHorizontalSpike(ModEntityTypes.ICE_CHAOS_SPIKE.get(), pLevel);
            iceHorizontalSpike.setPos(spawnPos);
            iceHorizontalSpike.setMovementDirection(pPlayer.getLookAngle());
            iceHorizontalSpike.setOwner(pPlayer.getUUID());

            // Add the entity to the world
            pLevel.addFreshEntity(iceHorizontalSpike);

            //Set Cooldown(in Seconds)
            chaosEmeraldCap.chaosCooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] = 25;
        });
    }

    public static void greenEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.GREEN_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x00FF00)),true);
                return;
            }

            Vec3 currentPos = new Vec3(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ());
            Vec3 lookAngle = pPlayer.getLookAngle();
            LivingEntity tpTarget = null;

            //Scan Forward for enemies
            for (int i = 0; i < 10; ++i) {
                //Increment Current Position Forward
                currentPos = currentPos.add(lookAngle);
                AABB boundingBox = new AABB(currentPos.x() + 3, currentPos.y() + 3, currentPos.z() + 3,
                        currentPos.x() - 3, currentPos.y() - 3, currentPos.z() - 3);

                List<LivingEntity> nearbyEntities = pLevel.getEntitiesOfClass(
                        LivingEntity.class, boundingBox,
                        (enemy) -> !enemy.is(pPlayer) && enemy.isAlive());

                //If enemy is found then Target it
                if (!nearbyEntities.isEmpty()) {
                    //Select Closest target
                    tpTarget = Collections.min(nearbyEntities, (e1, e2) -> {
                        Vec3 e1Pos = new Vec3(e1.getX(), e1.getY(), e1.getZ());
                        Vec3 e2Pos = new Vec3(e2.getX(), e2.getY(), e2.getZ());

                        return (int) (e1Pos.distanceToSqr(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ()) - e2Pos.distanceToSqr(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ()));
                    });
                    break;
                }
            }

            if(tpTarget == null)    return;
            Vec3 targetPos = new Vec3(tpTarget.getX(),tpTarget.getY(),tpTarget.getZ());

            //Playsound
            pLevel.playSound(null,currentPos.x(),currentPos.y(),currentPos.z(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 0.75f, 0.75f);

            //Teleport
            Vec3 direction = currentPos.subtract(targetPos).normalize();
            Vec3 newPlayerPos = targetPos.add(direction);
            float[] yawPitch = Utilities.getYawPitchFromVec(targetPos.subtract(newPlayerPos));
            try {
                if (!pLevel.isClientSide) {
                    ServerPlayer player = (ServerPlayer) pPlayer;
                    pPlayer.teleportTo(player.serverLevel(), newPlayerPos.x, newPlayerPos.y+0.2, newPlayerPos.z,
                            Collections.emptySet(), yawPitch[0], yawPitch[1]);
                }
            }catch (ClassCastException e) {
                System.out.println("Teleport Failed");
            }

            //Update the motion
            if(!pLevel.isClientSide()) {
                pPlayer.setDeltaMovement(0, 0.6, 0);
                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(pPlayer.getId(),pPlayer.getDeltaMovement()));
            }
            tpTarget.setDeltaMovement(direction.reverse().scale(1.5));

            //Deal damage
            tpTarget.hurt(pLevel.damageSources().playerAttack(pPlayer), 4);
            tpTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,110,11,false,true,false),pPlayer);
            tpTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,110,11,false,true,false),pPlayer);

            //Particle
            if(pLevel.isClientSide)
                Utilities.displayParticle(pLevel, new DustParticleOptions(new Vector3f(0,1,0),2),
                            newPlayerPos.x(),newPlayerPos.y()+1,newPlayerPos.z(),
                            1.5f, 1.5f, 1.5f,
                            0.01, 100, false);

            //Playsound
            pLevel.playSound(null,newPlayerPos.x(),newPlayerPos.y(),newPlayerPos.z(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.MASTER, 0.75f, 0.75f);

            //Grant Immunity to Fall
            if(pPlayer.hasEffect(ModEffects.CHAOS_DASH_ATTACK.get())) pPlayer.getEffect(ModEffects.CHAOS_DASH_ATTACK.get()).update(new MobEffectInstance(ModEffects.CHAOS_DASH_ATTACK.get(),40,0,false,false,false));
            else pPlayer.addEffect(new MobEffectInstance(ModEffects.CHAOS_DASH_ATTACK.get(),40,0,false,false,false),pPlayer);

            //Set Cooldown(in Seconds)
            chaosEmeraldCap.chaosCooldownKey[EmeraldType.GREEN_EMERALD.ordinal()] = 7;
        });
    }

    public static void greyEmeraldUse(Level pLevel, Player pPlayer)
    {
        if(!pLevel.isClientSide)
            pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.GREY_EMERALD.ordinal()] > 0) {
                    pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xEEEEEE)),true);
                    return;
                }

                //Activate Grey Emerald
                if(chaosEmeraldCap.greyEmeraldUse == 0) chaosEmeraldCap.greyEmeraldUse = 1;
            });
    }

    public static void purpleEmeraldUse(Level pLevel ,Player pPlayer)
    {
        if(!pLevel.isClientSide)
            pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.PURPLE_EMERALD.ordinal()] > 0) {
                    pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xCC00FF)),true);
                    return;
                }

                //Launch up
                pPlayer.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);
                pPlayer.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
                pPlayer.setDeltaMovement(0,0.17,0);
                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(pPlayer.getId(),pPlayer.getDeltaMovement()));

                //Activate Purple Emerald
                if(chaosEmeraldCap.purpleEmeraldUse == 0) chaosEmeraldCap.purpleEmeraldUse = 1;
            });
    }

    public static void redEmeraldUse(Level pLevel ,Player pPlayer)
    {
        //Vec3 playerPos = new Vec3(pPlayer.getX(),pPlayer.getY(),pPlayer.getZ());
        BlockPos playerPos = pPlayer.blockPosition();

        if(!pLevel.isClientSide)
            pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.RED_EMERALD.ordinal()] > 0) {
                    pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)),true);
                    return;
                }

                //Launch up
                pPlayer.setDeltaMovement(0,1.0,0);
                PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(pPlayer.getId(),pPlayer.getDeltaMovement()));

                //Particle
                PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                        ParticleTypes.FLAME,
                        pPlayer.getX()+0.00, pPlayer.getY()+0.35, pPlayer.getZ()+0.00,
                        0.001, 5f, 0.25f, 5f, 500,
                        true)
                );

                //Play Sound
                pLevel.playSound(null,playerPos.getX(),playerPos.getY(),playerPos.getZ(),
                        SoundEvents.FIRECHARGE_USE, SoundSource.MASTER, 0.75f, 0.75f);

                //Set Blocks on Fire
                final int fireBlocks = 24, radius = 6;

                // Use BlockPos.betweenClosed to iterate over all positions in the cube
                for(int i=0;i<fireBlocks;++i)
                {
                    //Spawns Fire
                    BlockPos pos = new BlockPos(
                            playerPos.getX()+Utilities.random.nextInt(-radius,radius),
                            playerPos.getY()+Utilities.random.nextInt(-radius,radius),
                            playerPos.getZ()+Utilities.random.nextInt(-radius,radius)
                            );
                    for(byte h=-3;h<=3;++h)
                    {
                        if(Utilities.passableBlocks.contains(ForgeRegistries.BLOCKS.getKey(pLevel.getBlockState(pos.offset(0,h,0)).getBlock())+"")
                                && !Utilities.passableBlocks.contains(ForgeRegistries.BLOCKS.getKey(pLevel.getBlockState(pos.offset(0,h-1,0)).getBlock())+"")) {
                            pLevel.setBlock(pos.offset(0, h, 0), Blocks.FIRE.defaultBlockState(), 3);
                            PacketHandler.sendToALLPlayers(new FireSyncPacketS2C(pos.offset(0,h,0)));
                            break;
                        }
                    }
                }

                //Damage and Set on Fire
                AABB boundingBox = new AABB(pPlayer.getX()+radius,pPlayer.getY()+1,pPlayer.getZ()+radius,
                        pPlayer.getX()-radius,pPlayer.getY()-2,pPlayer.getZ()-radius);
                for(LivingEntity enemy : pLevel.getEntitiesOfClass(LivingEntity.class,boundingBox,(enemy)->!enemy.is(pPlayer))) {
                    enemy.hurt(pLevel.damageSources().playerAttack(pPlayer), 10);
                    enemy.setSecondsOnFire(10);
                }

                //Prevent Fall Damage
                if(pPlayer.hasEffect(ModEffects.CHAOS_FLAME_JUMP.get())) pPlayer.getEffect(ModEffects.CHAOS_FLAME_JUMP.get()).update(new MobEffectInstance(ModEffects.CHAOS_FLAME_JUMP.get(),40,0,false,false,false));
                else pPlayer.addEffect(new MobEffectInstance(ModEffects.CHAOS_FLAME_JUMP.get(),40,0,false,false,false),pPlayer);

                //Grant Fire Resistance
                if(pPlayer.hasEffect(MobEffects.FIRE_RESISTANCE)) pPlayer.getEffect(MobEffects.FIRE_RESISTANCE).update(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,200,0,false,false,false));
                else pPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,200,0,false,false,false),pPlayer);

                //Set Cooldown(in Seconds)
                chaosEmeraldCap.chaosCooldownKey[EmeraldType.RED_EMERALD.ordinal()] = 15;
            });
    }

    public static void yellowEmeraldUse(Level pLevel, Player pPlayer)
    {
        //Throw Chaos Spear
        if(!pLevel.isClientSide())
            pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.chaosCooldownKey[EmeraldType.YELLOW_EMERALD.ordinal()] > 0) {
                    pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0xFFFF00)),true);
                    return;
                }

                Vec3 spawnPos = new Vec3(pPlayer.getX()+pPlayer.getLookAngle().x,
                        pPlayer.getY()+pPlayer.getLookAngle().y+1.0,
                        pPlayer.getZ()+pPlayer.getLookAngle().z);
                pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                        SoundEvents.EGG_THROW, SoundSource.MASTER, 1.0f, 1.0f);
                ChaosSpear chaosSpear = new ChaosSpear(ModEntityTypes.CHAOS_SPEAR.get(), pLevel);

                chaosSpear.setPos(spawnPos);
                chaosSpear.setDuration(120);
                chaosSpear.setMovementDirection(pPlayer.getLookAngle());
                chaosSpear.setDestroyBlocks(pPlayer.isShiftKeyDown());
                chaosSpear.setOwner(pPlayer.getUUID());

                // Add the entity to the world
                pLevel.addFreshEntity(chaosSpear);

                //Set Cooldown(in Seconds)
                chaosEmeraldCap.chaosCooldownKey[EmeraldType.YELLOW_EMERALD.ordinal()] = 10;
            });
    }

    public static void serverTick(ServerPlayer player, int serverTick)
    {
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
                if(chaosEmeraldCap.greyEmeraldUse == 40)
                {
                    //Set Cooldown(in Seconds)
                    chaosEmeraldCap.greyEmeraldUse = -1;
                    chaosEmeraldCap.chaosCooldownKey[EmeraldType.GREY_EMERALD.ordinal()] = 30;

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
                    chaosEmeraldCap.chaosCooldownKey[EmeraldType.PURPLE_EMERALD.ordinal()] = 30;
                }
            });
        }
    }

    public static void clientTick(LocalPlayer player, int clientTick)
    {
        //Particle
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Grey Emerald Digging Particle
            if(chaosEmeraldCap.greyEmeraldUse > 0)
                Utilities.displayParticle(player.level(), new DustParticleOptions(new Vector3f(0.9f, 0.9f, 0.9f), 2),
                        player.getX(), player.getY() + 1, player.getZ(),
                        1.5f, 1.5f, 1.5f,
                        0.01, 20, false);

            //Blast Particle
            if(chaosEmeraldCap.purpleEmeraldUse > 0)
                Utilities.displayParticle(player.level(), new DustParticleOptions(new Vector3f(0.8f, 0.0f, 1f), 1),
                        player.getX(), player.getY() + 1, player.getZ(),
                        1f, 1.5f, 1f,
                        0.01, 30, false);
        });
    }
}
