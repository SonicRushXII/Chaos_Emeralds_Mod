package net.sonicrushxii.chaos_emerald.entities.form_hyper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;
import net.sonicrushxii.chaos_emerald.entities.form_super.ChaosEmeraldEntity;

public class SuperEmeraldEntity extends PointEntity
{
    public static final EntityDataAccessor<Byte> EMERALD_COLOR = SynchedEntityData.defineId(SuperEmeraldEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(SuperEmeraldEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> RADIAL_SPEED = SynchedEntityData.defineId(SuperEmeraldEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> THETA = SynchedEntityData.defineId(SuperEmeraldEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ROTATION_SPEED = SynchedEntityData.defineId(SuperEmeraldEntity.class, EntityDataSerializers.FLOAT);

    public static final int MAX_DURATION = 100;

    public SuperEmeraldEntity(EntityType<? extends PointEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DURATION, MAX_DURATION);
        this.entityData.define(EMERALD_COLOR, (byte)0);
        this.entityData.define(RADIUS, 2.0F);
        this.entityData.define(RADIAL_SPEED, 0.05F);
        this.entityData.define(THETA, 0F);
        this.entityData.define(ROTATION_SPEED, (float)(Math.PI/10));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if(tag.contains("EmeraldType"))         this.entityData.set(EMERALD_COLOR,tag.getByte("EmeraldType"));
        if(tag.contains("CurrRadius"))          this.entityData.set(RADIUS,tag.getFloat("CurrRadius"));
        if(tag.contains("RadialSpeed"))         this.entityData.set(RADIAL_SPEED,tag.getFloat("RadialSpeed"));
        if(tag.contains("CurrPhase"))           this.entityData.set(THETA,tag.getFloat("CurrPhase"));
        if(tag.contains("RotationSpeed"))       this.entityData.set(ROTATION_SPEED,tag.getFloat("RotationSpeed"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        tag.putByte("EmeraldType", (byte)getEmeraldType().ordinal());
        tag.putFloat("CurrRadius", getCurrentRadius());
        tag.putFloat("RadialSpeed", getRadialSpeed());
        tag.putFloat("CurrPhase", getTheta());
        tag.putFloat("RotationSpeed", getRotationSpeed());

    }

    @Override
    public void tick() {
        super.tick();

        // Apply Circular Motion r*sin(T) - (r-dr)*sin(T+dT)     , 0    , r*cos(T) - (r-dr)*cos(T-dT)
        Vec3 circularMotionVec = new Vec3(
                (getCurrentRadius()*Math.sin(getTheta()) - (getCurrentRadius()-getRadialSpeed())*Math.sin(getTheta()+getRotationSpeed())),
                0,
                (getCurrentRadius()*Math.cos(getTheta()) - (getCurrentRadius()-getRadialSpeed())*Math.cos(getTheta()+getRotationSpeed())));

        this.setDeltaMovement(circularMotionVec.scale(1.0));

        if(!this.level().isClientSide && this.getCurrentRadius() < 0.0) {
            this.discard(); // Removes the entity from the world
        }

        //Update Radius and Theta
        setCurrentRadius(getCurrentRadius()-getRadialSpeed());
        setTheta(getTheta()+getRotationSpeed());
    }

    public EmeraldType getEmeraldType() {
        try
        {
            return EmeraldType.values()[this.entityData.get(EMERALD_COLOR)];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return EmeraldType.GREY_EMERALD;
        }
    }
    public void setEmeraldType(byte emeraldType) {this.entityData.set(EMERALD_COLOR,emeraldType);}
    public void setEmeraldType(EmeraldType emeraldType) {this.entityData.set(EMERALD_COLOR,(byte)emeraldType.ordinal());}

    public float getCurrentRadius(){return this.entityData.get(RADIUS);}
    public void setCurrentRadius(float currentRadius){this.entityData.set(RADIUS,currentRadius);}

    public float getRadialSpeed(){
        return (getDuration()>MAX_DURATION-20)?0.0F:this.entityData.get(RADIAL_SPEED);
    }
    public void setRadialSpeed(float radialSpeed){this.entityData.set(RADIAL_SPEED,radialSpeed);}

    public float getTheta(){
        return this.entityData.get(THETA);
    }
    public void setTheta(float theta){this.entityData.set(THETA,theta);}

    public float getRotationSpeed(){return this.entityData.get(ROTATION_SPEED);}
    public void setRotationSpeed(float rotationSpeed){this.entityData.set(ROTATION_SPEED,rotationSpeed);}
}
