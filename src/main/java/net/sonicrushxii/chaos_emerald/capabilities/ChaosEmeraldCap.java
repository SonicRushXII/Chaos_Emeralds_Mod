package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;

public class ChaosEmeraldCap {

    public byte[] chaosCooldownKey = new byte[EmeraldType.values().length];
    public byte[] superCooldownKey = new byte[EmeraldType.values().length];
    public byte manuscriptKey = 0;
    public byte greyEmeraldUse = 0;
    public byte purpleEmeraldUse = 0;

    public int falseSuperTimer = 0;
    public byte falseChaosSpaz = 0;

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

    public boolean hasAllManuscripts()
    {
        return manuscriptKey<0;
    }

    public void copyFrom(ChaosEmeraldCap source){
        if(source.chaosCooldownKey.length == 0) this.chaosCooldownKey = new byte[EmeraldType.values().length];
        else this.chaosCooldownKey = source.chaosCooldownKey;

        if(source.superCooldownKey.length == 0) this.superCooldownKey = new byte[EmeraldType.values().length];
        else this.superCooldownKey = source.superCooldownKey;

        this.greyEmeraldUse = source.greyEmeraldUse;
        this.purpleEmeraldUse = source.purpleEmeraldUse;
        this.manuscriptKey = source.manuscriptKey;
    }

    public void saveNBTData(CompoundTag nbt){
        //Copy Chaos Cooldown
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new byte[EmeraldType.values().length];
        nbt.putByteArray("ChaosEmeraldCooldown", chaosCooldownKey);

        //Copy Super Cooldown
        if(superCooldownKey.length == 0) superCooldownKey = new byte[EmeraldType.values().length];
        nbt.putByteArray("SuperEmeraldCooldown", superCooldownKey);

        //Chaos Emerald Times
        nbt.putByte("DigTimer",this.greyEmeraldUse);
        nbt.putByte("BlastTimer",this.purpleEmeraldUse);
        nbt.putByte("ManuscriptKey",this.manuscriptKey);

        //Transformations
        nbt.putInt("FalseSuperDur",falseSuperTimer);
        nbt.putByte("FalseChaosSpaz",falseChaosSpaz);

        nbt.putInt("SuperDur",superFormTimer);
        nbt.putInt("HyperDur",hyperFormTimer);
    }

    public void loadNBTData(CompoundTag nbt){
        //Load Chaos Emerald Cooldown
        chaosCooldownKey = nbt.getByteArray("ChaosEmeraldCooldown");
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new byte[EmeraldType.values().length];

        //Load Super Emerald Cooldown
        superCooldownKey = nbt.getByteArray("SuperEmeraldCooldown");
        if(superCooldownKey.length == 0) superCooldownKey = new byte[EmeraldType.values().length];

        //Chaos Emerald Times
        greyEmeraldUse = nbt.getByte("DigTimer");
        purpleEmeraldUse = nbt.getByte("BlastTimer");
        manuscriptKey = nbt.getByte("ManuscriptKey");

        //Transformation Times
        falseSuperTimer = nbt.getInt("FalseSuperDur");
        falseChaosSpaz = nbt.getByte("FalseChaosSpaz");

        superFormTimer = nbt.getInt("SuperDur");
        hyperFormTimer = nbt.getInt("HyperDur");
    }
}
