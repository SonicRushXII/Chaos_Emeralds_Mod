package net.sonicrushxii.chaos_emerald.entities.form_super;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.entities.all.LinearMovingEntity;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;

public class PortalRingEntity extends LinearMovingEntity {
    public static final EntityDataAccessor<Byte> PORTAL_TYPE = SynchedEntityData.defineId(PortalRingEntity.class, EntityDataSerializers.BYTE);
    public static final int PORTAL_DURATION = 201;
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

    public static float quantizePortalRotation(float portalRotation)
    {
        // Normalize the portalRotation angle to be between 0 and 360 degrees.
        portalRotation = portalRotation % 360.0F;
        if (portalRotation < 0.0F) {
            portalRotation += 360.0F;
        }

        // Check the angle ranges and return the closest rotation.
        if (portalRotation <= 45F || portalRotation > 315F) return 0.0F;  // Facing positive Z (South)
        if (portalRotation > 45F && portalRotation <= 135F) return 90.0F; // Facing West
        if (portalRotation > 135F && portalRotation <= 225F) return 180.0F; // Facing negative Z (North)
        if (portalRotation > 225F && portalRotation <= 315F) return 270.0F; // Facing East

        // Default return value (shouldn't be reached if all cases are handled).
        return 0.0F;
    }

    public void createPortal()
    {
        //Drawing Vector
        float portalRot = quantizePortalRotation(this.getYRot());
        float portalPerpendicularRot = portalRot + 90F;

        Vec3 drawingVector = Utilities.calculateViewVector(0f,portalPerpendicularRot);
        Vec3 portalPos = new Vec3(this.getX(),this.getY(),this.getZ());

        //Draw in the Border
        Block PORTAL_BORDER = (getPortalType() == (byte)0)? Blocks.YELLOW_STAINED_GLASS : Blocks.WHITE_STAINED_GLASS;
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(3))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-3))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(3).add(0,1,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-3).add(0,1,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(3).add(0,-1,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-3).add(0,-1,0))), PORTAL_BORDER.defaultBlockState(), 3);

        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,2,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,2,0))), PORTAL_BORDER.defaultBlockState(), 3);

        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,-2,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,-2,0))), PORTAL_BORDER.defaultBlockState(), 3);

        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(0,3,0)), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(0,-3,0)), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,3,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,-3,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,3,0))), PORTAL_BORDER.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,-3,0))), PORTAL_BORDER.defaultBlockState(), 3);

        //Portal Interior
        Block PORTAL_INSIDE = (getPortalType() == (byte)0)? ModBlocks.CHAOS_NETHER_PORTAL_BLOCK.get() : ModBlocks.CHAOS_END_PORTAL_BLOCK.get();
        this.level().setBlock(Utilities.convertToBlockPos(portalPos), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1))), PORTAL_INSIDE.defaultBlockState(), 3);

        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(0,-1,0)), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(0,1,0)), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,-1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,-1,0))), PORTAL_INSIDE.defaultBlockState(), 3);

        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(0,-2,0)), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(0,2,0)), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,-2,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,2,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,2,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,-2,0))), PORTAL_INSIDE.defaultBlockState(), 3);

        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,-1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
        this.level().setBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,-1,0))), PORTAL_INSIDE.defaultBlockState(), 3);
    }

    public void destroyPortal()
    {
        //Drawing Vector
        float portalRot = quantizePortalRotation(this.getYRot());
        float portalPerpendicularRot = portalRot + 90F;
        portalPerpendicularRot = (portalPerpendicularRot > 180.0F) ? (-180.0F +(portalPerpendicularRot-180.0F)) : portalPerpendicularRot;

        Vec3 drawingVector = Utilities.calculateViewVector(0f,portalPerpendicularRot);
        Vec3 portalPos = new Vec3(this.getX(),this.getY(),this.getZ());

        //Destroy the Blocks on the Border
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(3))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-3))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(3).add(0,1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-3).add(0,1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(3).add(0,-1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-3).add(0,-1,0))), false);

        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,2,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,2,0))), false);

        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,-2,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,-2,0))), false);

        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(0,3,0)), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(0,-3,0)), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,3,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,-3,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,3,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,-3,0))), false);

        //Destroy the Blocks Inside the Portal
        Block PORTAL_INSIDE = (getPortalType() == (byte)0)? ModBlocks.CHAOS_NETHER_PORTAL_BLOCK.get() : ModBlocks.CHAOS_END_PORTAL_BLOCK.get();
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1))), false);

        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(0,-1,0)), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(0,1,0)), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,-1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,-1,0))), false);

        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(0,-2,0)), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(0,2,0)), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,-2,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-1).add(0,2,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,2,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(1).add(0,-2,0))), false);

        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,-1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(-2).add(0,1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,1,0))), false);
        this.level().destroyBlock(Utilities.convertToBlockPos(portalPos.add(drawingVector.scale(2).add(0,-1,0))), false);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(getDuration() == PORTAL_DURATION)
        {
            if(!this.level().isClientSide()) createPortal();
        }

        if(getDuration() == 1)
        {
            if(!this.level().isClientSide()) destroyPortal();
        }
    }
}
