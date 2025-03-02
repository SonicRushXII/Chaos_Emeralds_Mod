package net.sonicrushxii.chaos_emerald.entities.form_super;

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
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.entities.all.LinearMovingEntity;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChaosSpearEXEntity extends LinearMovingEntity {
    public static final EntityDataAccessor<Boolean> DESTROY_BLOCKS = SynchedEntityData.defineId(ChaosSpearEXEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(ChaosSpearEXEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private int MAX_DURATION = 200;
    private static final float STRENGTH = 1.85F;
    private static final float DAMAGE = 8.0F;

    public ChaosSpearEXEntity(EntityType<? extends PointEntity> type, Level world) {
        super(type, world);
    }

    public void initializeDuration(int duration)
    {
        super.setDuration(duration);
        MAX_DURATION = duration;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DESTROY_BLOCKS, true);
        this.entityData.define(OWNER,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        // Destroy Blocks
        if(tag.contains("DestroyBlocks")) setDestroyBlocks(tag.getBoolean("DestroyBlocks"));
        // Load the owner's UUID
        if (tag.hasUUID("OwnerUUID")) this.setOwner(tag.getUUID("OwnerUUID"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Save the owner's UUID
        super.addAdditionalSaveData(tag);
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) tag.putUUID("OwnerUUID", ownerUuid);
        tag.putBoolean("DestroyBlocks", isDestroyBlocks());
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

    public boolean isDestroyBlocks() {
        return this.entityData.get(DESTROY_BLOCKS);
    }

    public void setDestroyBlocks(boolean destroyBlocks) {
        this.entityData.set(DESTROY_BLOCKS, destroyBlocks);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide) {
            Utilities.displayParticle(this.level(), new DustParticleOptions(new Vector3f(1f, 1f, 0f), 1f),
                    this.getX(), this.getY(), this.getZ(), 0.3f, 0.3f, 0.3f, 0, 15, true);
            Utilities.displayParticle(this.level(),ParticleTypes.END_ROD,
                    this.getX(),this.getY(),this.getZ(),0.3f,0.3f,0.3f,0,1,false);
        }
        else{
            // Check for collision
            if(this.onGround() || this.horizontalCollision || this.verticalCollision && this.getDeltaMovement().y > 0)  explode();

            // Check for entity collisions and apply damage
            List<Entity> enemies = this.level().getEntitiesOfClass(Entity.class,
                    new AABB(this.getX() - 1.5, this.getY() - 1.5, this.getZ() - 1.5,
                            this.getX() + 1.5, this.getY() + 1.5, this.getZ() + 1.5),
                    enemy -> !(enemy instanceof ChaosSpearEXEntity)
            );
            if (!enemies.isEmpty() && this.getDuration() < this.MAX_DURATION-4)
            {
                try {
                    for (Entity enemy : enemies) {
                        if(enemy instanceof LivingEntity)
                            enemy.hurt(this.damageSources().playerAttack((Player) this.getOwner()),DAMAGE);
                    }
                }catch(NullPointerException ignored){}
                explode();
            }
        }
    }

    private void explode()
    {
        this.kill();
        if(this.isDestroyBlocks() && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))
        {
            this.level().explode(
                    /* Exploder (null if no specific entity causes it) */ getOwner(),
                    /* Center x, y, z positions */ this.getX(), this.getY(), this.getZ(),
                    /* Strength */ STRENGTH,
                    /* Causes fire */ false,
                    /* Block Interaction Mode */ Level.ExplosionInteraction.TNT
            );
        }
        else
        {
            //Deal Damage and Use Particle Effects
            this.level().explode(
                    /* Exploder (null if no specific entity causes it) */ getOwner(),
                    /* Center x, y, z positions */ this.getX(), this.getY(), this.getZ(),
                    /* Strength */ STRENGTH,
                    /* Causes fire */ false,
                    /* Block Interaction Mode */ Level.ExplosionInteraction.NONE
            );
        }
    }
}
