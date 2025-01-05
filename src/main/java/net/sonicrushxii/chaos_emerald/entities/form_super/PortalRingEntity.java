package net.sonicrushxii.chaos_emerald.entities.form_super;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.entities.all.LinearMovingEntity;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;

public class PortalRingEntity extends LinearMovingEntity {
    public static final EntityDataAccessor<Byte> PORTAL_TYPE = SynchedEntityData.defineId(PortalRingEntity.class, EntityDataSerializers.BYTE);
    private int PORTAL_DURATION = 201;
    private int MAX_DURATION = 300;

    public PortalRingEntity(EntityType<? extends PointEntity> type, Level world) {
        super(type, world);
    }

    public void initializeDuration(int duration)
    {
        super.setDuration(duration+PORTAL_DURATION);
        MAX_DURATION = duration+PORTAL_DURATION;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PORTAL_TYPE, (byte)0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        // Load the owner's UUID
        if (tag.contains("PortalType")) this.setPortalType(tag.getByte("PortalType"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Save the owner's UUID
        super.addAdditionalSaveData(tag);
        tag.putByte("PortalType", getPortalType());
    }

    public byte getPortalType() {
        return this.entityData.get(PORTAL_TYPE);
    }

    public void setPortalType(byte portalType) {
        this.entityData.set(PORTAL_TYPE, portalType);
    }

    @Override
    public boolean isPushable() {
        return false; // Prevent the entity from being pushed
    }

    @Override
    public boolean canBeCollidedWith() {
        return false; // Disable collision with this entity
    }

    @Override
    public void push(double x, double y, double z) {
        // Prevent the entity from being affected by pushes
    }

    @Override
    public void move(MoverType type, Vec3 movement) {
        // Allow movement through blocks by ignoring collisions
        if(getDuration() > PORTAL_DURATION)
            this.setPos(this.getX() + movement.x, this.getY() + movement.y, this.getZ() + movement.z);
    }

    @Override
    public void tick() {
        super.tick();

        if(getDuration() == PORTAL_DURATION)
        {
            System.err.println("Spawn Portal at, "+this.getPosition(0f));
        }

        if(getDuration() == 1)
        {
            System.err.println("Closed Portal at, "+this.getPosition(0f));
        }
    }
}
