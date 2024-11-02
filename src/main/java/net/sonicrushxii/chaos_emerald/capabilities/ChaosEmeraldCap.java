package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;

public class ChaosEmeraldCap {

    public byte[] cooldownKey = new byte[EmeraldType.values().length];
    public byte greyEmeraldUse = 0;
    public byte purpleEmeraldUse = 0;

    public void copyFrom(ChaosEmeraldCap source){
        this.cooldownKey = source.cooldownKey;
        this.greyEmeraldUse = source.greyEmeraldUse;
        this.purpleEmeraldUse = source.purpleEmeraldUse;
    }

    public void saveNBTData(CompoundTag nbt){
        //Copy Current Form
        nbt.putByteArray("ChaosCDKey", cooldownKey);
        nbt.putByte("DigTimer",this.greyEmeraldUse);
        nbt.putByte("BlastTimer",this.purpleEmeraldUse);
    }

    public void loadNBTData(CompoundTag nbt){
        //Copy Current Form
        cooldownKey = nbt.getByteArray("ChaosCDKey");
        greyEmeraldUse = nbt.getByte("DigTimer");
        purpleEmeraldUse = nbt.getByte("BlastTimer");
    }
}
