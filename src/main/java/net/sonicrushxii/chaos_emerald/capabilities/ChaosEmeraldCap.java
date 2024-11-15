package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;

public class ChaosEmeraldCap {

    public byte[] cooldownKey = new byte[EmeraldType.values().length];
    public byte manuscriptKey = 0;
    public byte greyEmeraldUse = 0;
    public byte purpleEmeraldUse = 0;

    public void foundManuscript(byte index)
    {
        assert index < 7;
        //If they've already found all scrolls, reset the Manuscript key to zero
        if(manuscriptKey == Byte.MAX_VALUE) manuscriptKey = 0;
        this.manuscriptKey = (byte) (manuscriptKey|1<<index);
    }

    public boolean hasManuscript(byte index)
    {
        return (manuscriptKey&1<<index) != 0;
    }

    public void copyFrom(ChaosEmeraldCap source){
        this.cooldownKey = source.cooldownKey;
        this.greyEmeraldUse = source.greyEmeraldUse;
        this.purpleEmeraldUse = source.purpleEmeraldUse;
        this.manuscriptKey = source.manuscriptKey;
    }

    public void saveNBTData(CompoundTag nbt){
        //Copy Current Form
        nbt.putByteArray("ChaosCooldown", cooldownKey);
        nbt.putByte("DigTimer",this.greyEmeraldUse);
        nbt.putByte("BlastTimer",this.purpleEmeraldUse);
        nbt.putByte("ManuscriptKey",this.manuscriptKey);
    }

    public void loadNBTData(CompoundTag nbt){
        //Copy Current Form
        cooldownKey = nbt.getByteArray("ChaosCooldown");
        greyEmeraldUse = nbt.getByte("DigTimer");
        purpleEmeraldUse = nbt.getByte("BlastTimer");
        manuscriptKey = nbt.getByte("ManuscriptKey");
    }
}
