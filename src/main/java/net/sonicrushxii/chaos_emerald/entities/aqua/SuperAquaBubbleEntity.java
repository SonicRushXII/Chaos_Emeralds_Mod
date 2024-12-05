package net.sonicrushxii.chaos_emerald.entities.aqua;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;
import net.sonicrushxii.chaos_emerald.entities.false_super.ChaosSpaz;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SuperAquaBubbleEntity extends PointEntity
{
    public Vec3 getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Vec3 movementDirection) {
        this.movementDirection = movementDirection;
    }

    protected Vec3 movementDirection;

    public SuperAquaBubbleEntity(EntityType<? extends PointEntity> type, Level world) {
        super(type, world);
        movementDirection = new Vec3(0.0,0.0,0.0);
    }

    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(ChaosSpaz.class, EntityDataSerializers.OPTIONAL_UUID);
    private int MAX_DURATION = 200;
    private static final float STRENGTH = 1.0f;
    private static final float DAMAGE = 4.0F;

    @Override
    public void setDuration(int duration) {
        super.setDuration(duration);
        MAX_DURATION = duration;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        // Load the owner's UUID
        if (tag.hasUUID("OwnerUUID")) this.setOwner(tag.getUUID("OwnerUUID"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Save the owner's UUID
        super.addAdditionalSaveData(tag);
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) tag.putUUID("OwnerUUID", ownerUuid);
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

    @Override
    public void tick() {
        super.tick();
        if(getDuration() > 38)  this.setDeltaMovement(movementDirection);
        if(getDuration() == 38)  this.setDeltaMovement(movementDirection.scale(0.1));

        if(this.level().isClientSide) {
            Utilities.displayParticle(this.level(), new DustParticleOptions(new Vector3f(0.0F, 1F, 1F), 1f),
                    this.getX(), this.getY(), this.getZ(), 0.3f, 0.3f, 0.3f, 0, 1, false);
            Utilities.displayParticle(this.level(), ParticleTypes.BUBBLE,
                    this.getX(),this.getY(),this.getZ(),0.3f,0.3f,0.3f,0,1,false);
        }
        else{
            // Check for collision
            if(this.onGround() || this.horizontalCollision || this.verticalCollision && this.getDeltaMovement().y > 0)  explode();
        }

        // Check for entity collisions and apply damage
        List<Entity> enemies = this.level().getEntitiesOfClass(Entity.class,
                new AABB(this.getX() - 1.0, this.getY() - 1.0, this.getZ() - 1.0,
                        this.getX() + 1.0, this.getY() + 1.0, this.getZ() + 1.0),
                enemy -> !(enemy.is(this) || enemy.getUUID().equals(this.getOwnerUUID()) || enemy instanceof SuperAquaBubbleEntity)
        );
        if (!enemies.isEmpty())
        {
            try {// Synchronize on server only
                for (Entity enemy : enemies) {
                    enemy.setDeltaMovement(this.movementDirection);
                }
            }catch(NullPointerException ignored){}
            explode();
        }
    }

    private void explode()
    {
        this.kill();
        this.level().explode(
                /* Exploder (null if no specific entity causes it) */ getOwner(),
                /* Center x, y, z positions */ this.getX(), this.getY(), this.getZ(),
                /* Strength */ STRENGTH,
                /* Causes fire */ false,
                /* Block Interaction Mode */ Level.ExplosionInteraction.NONE
        );
    }
}
