package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;

public class ChaosEmeraldCap {

    public byte[] chaosCooldownKey = new byte[EmeraldType.values().length];
    public byte[] superCooldownKey = new byte[EmeraldType.values().length];

    //Manuscript Key
    public byte manuscriptKey = 0;

    //Chaos Usage
    public byte greyChaosUse = 0;
    public byte purpleChaosUse = 0;

    //False Super Form
    public int falseSuperTimer = 0;
    public byte falseChaosSpaz = 0;

    //Super Form
    public int superFormTimer = 0;

    //Super Emerald Usage
    public byte aquaSuperUse = 0;

    //Hyper Form
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
        //Chaos Emerald Usage
        if(source.chaosCooldownKey.length == 0) this.chaosCooldownKey = new byte[EmeraldType.values().length];
        else this.chaosCooldownKey = source.chaosCooldownKey;
        this.greyChaosUse = source.greyChaosUse;
        this.purpleChaosUse = source.purpleChaosUse;

        //Manuscript
        this.manuscriptKey = source.manuscriptKey;

        //Super Emerald Usage
        this.aquaSuperUse = source.aquaSuperUse;
        if(source.superCooldownKey.length == 0) this.superCooldownKey = new byte[EmeraldType.values().length];
        else this.superCooldownKey = source.superCooldownKey;
    }

    public void saveNBTData(CompoundTag nbt){
        //Copy Chaos Cooldown
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new byte[EmeraldType.values().length];
        nbt.putByteArray("ChaosEmeraldCooldown", chaosCooldownKey);

        //Chaos Emerald Times
        nbt.putByte("DigTimer",this.greyChaosUse);
        nbt.putByte("BlastTimer",this.purpleChaosUse);
        nbt.putByte("ManuscriptKey",this.manuscriptKey);

        //False Super
        nbt.putInt("FalseSuperDur",falseSuperTimer);
        nbt.putByte("FalseChaosSpaz",falseChaosSpaz);

        //Super Form
        nbt.putInt("SuperDur",superFormTimer);

        //Copy Super Cooldown
        if(superCooldownKey.length == 0) superCooldownKey = new byte[EmeraldType.values().length];
        nbt.putByteArray("SuperEmeraldCooldown", superCooldownKey);

        //Super Emerald Times
        nbt.putByte("BubbleBoost",this.aquaSuperUse);

        //Hyper Form
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
        greyChaosUse = nbt.getByte("DigTimer");
        purpleChaosUse = nbt.getByte("BlastTimer");
        manuscriptKey = nbt.getByte("ManuscriptKey");

        //Transformation Times
        falseSuperTimer = nbt.getInt("FalseSuperDur");
        falseChaosSpaz = nbt.getByte("FalseChaosSpaz");

        //Super Form
        superFormTimer = nbt.getInt("SuperDur");

        //Super Emerald Times
        aquaSuperUse = nbt.getByte("BubbleBoost");

        //Hyper Form
        hyperFormTimer = nbt.getInt("HyperDur");
    }
}
