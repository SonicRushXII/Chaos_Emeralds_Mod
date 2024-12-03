package net.sonicrushxii.chaos_emerald.entities.blue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;

import javax.annotation.Nullable;
import java.util.*;

public class IceHorizontalSpike extends Entity {
    public static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(IceHorizontalSpike.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(IceHorizontalSpike.class, EntityDataSerializers.OPTIONAL_UUID);

    int[] xIcePositions;
    int[] yIcePositions;
    int[] zIcePositions;
    protected Vec3 movementDirection;

    public Vec3 getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Vec3 movementDirection) {
        this.movementDirection = movementDirection;
    }

    public IceHorizontalSpike(EntityType<? extends Entity> type, Level world) {
        super(type, world);
        this.movementDirection = new Vec3(0,0,0);
        this.xIcePositions = new int[getDuration()];
        this.yIcePositions = new int[getDuration()];
        this.zIcePositions = new int[getDuration()];
    }

    public int getDuration() {
        return this.entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        this.entityData.set(DURATION, duration);
    }

    // Sets the owner by UUID
    public void setOwner(UUID ownerUuid) {
        this.entityData.set(OWNER, Optional.of(ownerUuid));
    }

    // Retrieves the owner's UUID, if it exists
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER).orElse(null);
    }

    // Gets the actual owner Entity, if they are loaded in the world
    @Nullable
    public LivingEntity getOwner() {
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) {
            return this.level().getPlayerByUUID(ownerUuid);
        }
        return null;
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(DURATION, 40);
        this.entityData.define(OWNER,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

        // Load the owner's UUID
        if (tag.hasUUID("OwnerUUID")) {
            this.setOwner(tag.getUUID("OwnerUUID"));
        }

        if(tag.contains("Duration")) setDuration(tag.getInt("Duration"));
        if(tag.contains("IcePositionsX")) this.xIcePositions = tag.getIntArray("IcePositionsX");
        if(tag.contains("IcePositionsY")) this.yIcePositions = tag.getIntArray("IcePositionsY");
        if(tag.contains("IcePositionsZ")) this.zIcePositions = tag.getIntArray("IcePositionsZ");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Save the owner's UUID
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) {
            tag.putUUID("OwnerUUID", ownerUuid);
        }

        tag.putInt("Duration", getDuration());
        tag.putIntArray("IcePositionsX",this.xIcePositions);
        tag.putIntArray("IcePositionsY",this.yIcePositions);
        tag.putIntArray("IcePositionsZ",this.zIcePositions);

    }

    @Override
    public void tick() {
        super.tick();

        // Server Side Only: Process all critical game state changes
        if (!this.level().isClientSide) {
            // Immediately handle zero duration movement reset
            if (getDuration() == 0) {
                this.setMovementDirection(new Vec3(0, 0, 0));
                this.setDeltaMovement(0, 0, 0);
            }

            // Only perform actions if duration is not zero
            if (getDuration() > 1) {
                // Set entity movement
                this.setDeltaMovement(movementDirection);
                this.move(MoverType.SELF, this.getDeltaMovement());

                // Place ice blocks and manage collisions
                BlockPos icePlacePos = this.blockPosition().offset(0, -1, 0);
                if(Utilities.passableBlocks.contains(ForgeRegistries.BLOCKS.getKey(this.level().getBlockState(icePlacePos).getBlock()) + ""))
                    this.level().setBlock(icePlacePos, Blocks.BLUE_ICE.defaultBlockState(), 3);
                xIcePositions[getDuration()-1] = icePlacePos.getX();
                yIcePositions[getDuration()-1] = icePlacePos.getY();
                zIcePositions[getDuration()-1] = icePlacePos.getZ();

                // Handle collision checks
                if (this.horizontalCollision || (this.verticalCollision && this.getDeltaMovement().y > 0)) {
                    this.setDuration(0);  // Synchronize on server only
                }

                // Check for entity collisions and apply damage
                List<LivingEntity> enemies = this.level().getEntitiesOfClass(LivingEntity.class,
                        new AABB(this.getX() - 1, this.getY() - 0.5, this.getZ() - 1, this.getX() + 0.5, this.getY() + 2.5, this.getZ() + 1),
                        enemy -> !(enemy.is(this))
                );

                if (!enemies.isEmpty()) {
                    this.setDuration(0);
                    try {// Synchronize on server only
                        for (LivingEntity enemy : enemies) {
                            enemy.hurt(this.damageSources().playerAttack((Player) this.getOwner()), (enemy.isInWater()) ? 8.0f : 4.0f);
                            enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,3,false,false));
                        }
                    }catch(NullPointerException ignored){}
                }
            }

            // Handle duration < -200 for ice destruction
            if (getDuration() < -200) {
                int idx = -(getDuration() + 200);
                if (idx < xIcePositions.length) {
                    BlockPos icePosition = new BlockPos(
                            xIcePositions[xIcePositions.length - idx],
                            yIcePositions[yIcePositions.length - idx],
                            zIcePositions[zIcePositions.length - idx]
                    );
                    if (this.level().getBlockState(icePosition).getBlock() == Blocks.BLUE_ICE) {
                        this.level().destroyBlock(icePosition, false);
                    }
                }
            }

            // Final cleanup to discard entity
            if (getDuration() < -270) {
                this.discard();
            }

            // Decrement duration at the end of the tick
            this.setDuration(getDuration() - 1);
        } else {
            // Client Side Only: Render particles or other visuals
            if (getDuration() > 0) {
                Utilities.displayParticle(this.level(), ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY() + 0.5, this.getZ(),
                        0.5f, 0.5f, 0.5f, 0.01, 5, false);
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public boolean isOwnedBy(Player player) {
        return player.getUUID().equals(this.getOwnerUUID());
    }
}
