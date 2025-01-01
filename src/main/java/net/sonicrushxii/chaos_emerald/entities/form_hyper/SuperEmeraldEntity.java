package net.sonicrushxii.chaos_emerald.entities.form_hyper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;

public class SuperEmeraldEntity extends PointEntity
{
    public static final EntityDataAccessor<Byte> EMERALD_COLOR = SynchedEntityData.defineId(SuperEmeraldEntity.class, EntityDataSerializers.BYTE);

    public SuperEmeraldEntity(EntityType<? extends PointEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(EMERALD_COLOR, (byte)0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("EmeraldType")) {
            this.entityData.set(EMERALD_COLOR,tag.getByte("EmeraldType"));
        }
    }

    public EmeraldType getEmeraldType() {return EmeraldType.values()[this.entityData.get(EMERALD_COLOR)];}
    public void setEmeraldType(byte emeraldType) {this.entityData.set(EMERALD_COLOR,emeraldType);}
    public void setEmeraldType(EmeraldType emeraldType) {this.entityData.set(EMERALD_COLOR,(byte)emeraldType.ordinal());}
}
