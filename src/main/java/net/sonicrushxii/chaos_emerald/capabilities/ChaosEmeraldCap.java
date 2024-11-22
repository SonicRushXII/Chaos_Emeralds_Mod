package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;

public class ChaosEmeraldCap {

    public byte[] cooldownKey = new byte[EmeraldType.values().length];
    public byte manuscriptKey = 0;
    public byte greyEmeraldUse = 0;
    public byte purpleEmeraldUse = 0;

    public int falseSuperTimer = 0;
    public int superFormTimer = 0;
    public int hyperFormTimer = 0;

    public void foundManuscript(byte index)
    {
        assert index < 7;
        this.manuscriptKey = (byte) (manuscriptKey|1<<index);

        //If they've already found all scrolls, Activate Sign bit.
        if(manuscriptKey == Byte.MAX_VALUE || manuscriptKey == -1) manuscriptKey = -128;

    }

    public boolean hasManuscript(byte index)
    {
        return (manuscriptKey&1<<index) != 0;
    }

    public void copyFrom(ChaosEmeraldCap source){
        if(source.cooldownKey.length == 0) this.cooldownKey = new byte[EmeraldType.values().length];
        else this.cooldownKey = source.cooldownKey;

        this.greyEmeraldUse = source.greyEmeraldUse;
        this.purpleEmeraldUse = source.purpleEmeraldUse;
        this.manuscriptKey = source.manuscriptKey;
    }

    public void saveNBTData(CompoundTag nbt){
        //Copy Chaos Cooldown
        if(cooldownKey.length == 0) cooldownKey = new byte[EmeraldType.values().length];
        nbt.putByteArray("ChaosCooldown", cooldownKey);

        //Chaos Emerald Times
        nbt.putByte("DigTimer",this.greyEmeraldUse);
        nbt.putByte("BlastTimer",this.purpleEmeraldUse);
        nbt.putByte("ManuscriptKey",this.manuscriptKey);

        //Transformation Times
        nbt.putInt("FalseSuperDur",falseSuperTimer);
        nbt.putInt("SuperDur",superFormTimer);
        nbt.putInt("HyperDur",hyperFormTimer);
    }

    public void loadNBTData(CompoundTag nbt){
        //Load Chaos Cooldown
        cooldownKey = nbt.getByteArray("ChaosCooldown");
        if(cooldownKey.length == 0) cooldownKey = new byte[EmeraldType.values().length];

        //Chaos Emerald Times
        greyEmeraldUse = nbt.getByte("DigTimer");
        purpleEmeraldUse = nbt.getByte("BlastTimer");
        manuscriptKey = nbt.getByte("ManuscriptKey");

        //Transformation Times
        falseSuperTimer = nbt.getInt("FalseSuperDur");
        superFormTimer = nbt.getInt("SuperDur");
        hyperFormTimer = nbt.getInt("HyperDur");
    }
}
