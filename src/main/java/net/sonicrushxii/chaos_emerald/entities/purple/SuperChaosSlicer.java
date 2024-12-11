package net.sonicrushxii.chaos_emerald.entities.purple;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.entities.all.LinearMovingEntity;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SuperChaosSlicer extends LinearMovingEntity {
    public static final EntityDataAccessor<Float> MOVEMENT_X = SynchedEntityData.defineId(SuperChaosSlicer.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> MOVEMENT_Y = SynchedEntityData.defineId(SuperChaosSlicer.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> MOVEMENT_Z = SynchedEntityData.defineId(SuperChaosSlicer.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> INVERTED = SynchedEntityData.defineId(SuperChaosSlicer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(SuperChaosSlicer.class, EntityDataSerializers.OPTIONAL_UUID);
    private int MAX_DURATION = 200;
    private static final float DAMAGE = 9.0F;

    public SuperChaosSlicer(EntityType<? extends PointEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public void setMovementDirection(Vec3 movementDirection) {
        this.setMotionX((float)movementDirection.x);
        this.setMotionY((float)movementDirection.y);
        this.setMotionZ((float)movementDirection.z);
        this.movementDirection = movementDirection;
    }

    @Override
    public void setDuration(int duration) {
        super.setDuration(duration);
        MAX_DURATION = duration;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOVEMENT_X, 0.0f);
        this.entityData.define(MOVEMENT_Y, 0.0f);
        this.entityData.define(MOVEMENT_Z, 0.0f);
        this.entityData.define(INVERTED, false);
        this.entityData.define(OWNER,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        // Inverted
        if(tag.contains("Inverted")) setInverted(tag.getBoolean("Inverted"));
        if(tag.contains("MotionX")) setMotionX(tag.getFloat("MotionX"));
        if(tag.contains("MotionY")) setMotionY(tag.getFloat("MotionY"));
        if(tag.contains("MotionZ")) setMotionZ(tag.getFloat("MotionZ"));
        // Load the owner's UUID
        if(tag.hasUUID("OwnerUUID")) this.setOwner(tag.getUUID("OwnerUUID"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Save the owner's UUID
        super.addAdditionalSaveData(tag);
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) tag.putUUID("OwnerUUID", ownerUuid);
        tag.putBoolean("Inverted", isInverted());
        tag.putFloat("MotionX",getMotionX());
        tag.putFloat("MotionY",getMotionY());
        tag.putFloat("MotionZ",getMotionZ());
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
        if (ownerUuid != null) return this.level().getPlayerByUUID(ownerUuid);
        return null;
    }

    public boolean isInverted() {
        return this.entityData.get(INVERTED);
    }

    public void setInverted(boolean inverted) {
        this.entityData.set(INVERTED, inverted);
    }

    public void setMotionX(float motionX) {this.entityData.set(MOVEMENT_X,motionX);}
    public void setMotionY(float motionY) {this.entityData.set(MOVEMENT_Y,motionY);}
    public void setMotionZ(float motionZ) {this.entityData.set(MOVEMENT_Z,motionZ);}

    public float getMotionX()   { return this.entityData.get(MOVEMENT_X);}
    public float getMotionY()   { return this.entityData.get(MOVEMENT_Y);}
    public float getMotionZ()   { return this.entityData.get(MOVEMENT_Z);}

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide) {
            Vec3 currPos = new Vec3(this.getX(),this.getY(),this.getZ());

            Vec3 movementDirection = new Vec3(getMotionX(),getMotionY(),getMotionZ());

            Vec3 upDiag = movementDirection.scale(2.0).cross(new Vec3(0,(isInverted())?1:-1,0)).add(0,1.2,0);
            Vec3 downDiag = movementDirection.scale(2.0).cross(new Vec3(0,(isInverted())?-1:1,0)).add(0,-1.2,0);

            Utilities.particleRaycast(this.level(),
                    new DustParticleOptions(new Vector3f(0.75f, 0f, 1f), 1.4f),
                    currPos.add(upDiag),currPos.add(downDiag));
            Utilities.particleRaycast(this.level(),
                    ParticleTypes.ELECTRIC_SPARK,
                    currPos.add(upDiag),currPos.add(downDiag));
        }
        else{
            // Check for collision
            if(this.onGround() || this.horizontalCollision || this.verticalCollision && this.getDeltaMovement().y > 0)  kill();

            // Check for entity collisions and apply damage
            List<Entity> enemies = this.level().getEntitiesOfClass(Entity.class,
                    new AABB(this.getX() - 1.0, this.getY() - 1.0, this.getZ() - 1.0,
                            this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0),
                    enemy -> !(enemy.is(this))
            );
            if (!enemies.isEmpty() && this.getDuration() < this.MAX_DURATION-4) {
                try {// Synchronize on server only
                    for (Entity enemy : enemies) {
                        if(enemy instanceof LivingEntity livingEnemy) {
                            livingEnemy.hurt(this.damageSources().playerAttack((Player) this.getOwner()), DAMAGE);
                            livingEnemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1, false, true));
                            livingEnemy.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, false, true));
                        }
                    }
                }catch(NullPointerException ignored){}
            }
        }
    }
}
