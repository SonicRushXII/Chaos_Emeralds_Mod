package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.chaos_emerald.capabilities.all.FormProperties;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormProperties;

public class ChaosEmeraldCap {
    public FormProperties formProperties = new FormProperties();
    public float atkRotPhaseX = 0.0f;
    public float atkRotPhaseY = 0.0f;
    public int[] chaosCooldownKey = new int[EmeraldType.values().length];
    public int[] superCooldownKey = new int[EmeraldType.values().length];

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
    public int superFormCooldown = 0;

    //Super Emerald Usage
    public byte aquaSuperUse = 0;
    public boolean isWaterBoosting = false;
    public byte greenSuperUse = 0;
    public byte yellowSuperUse = 0;
    public byte purpleSuperUse = 0;
    public short redSuperUse = 0;
    public int greySuperUse = 0;
    public int[] greySuperPos = new int[5];
    public String currentDimension = "minecraft:overworld";

    //Hyper Form
    public int hyperFormTimer = 0;
    public int hyperFormCooldown = 0;

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
        //Attack Phase Rotation
        this.atkRotPhaseX = source.atkRotPhaseX;
        this.atkRotPhaseY = source.atkRotPhaseY;

        //Chaos Emerald Usage
        if(source.chaosCooldownKey.length == 0) this.chaosCooldownKey = new int[EmeraldType.values().length];
        else this.chaosCooldownKey = source.chaosCooldownKey;
        this.greyChaosUse = source.greyChaosUse;
        this.purpleChaosUse = source.purpleChaosUse;

        //Super Form Timer
        this.superFormTimer = source.superFormTimer;
        this.superFormCooldown = source.superFormCooldown;
        this.formProperties = source.formProperties;

        //Manuscript
        this.manuscriptKey = source.manuscriptKey;

        //Super Emerald Usage
        this.aquaSuperUse = source.aquaSuperUse;
        this.isWaterBoosting = source.isWaterBoosting;
        this.greenSuperUse = source.greenSuperUse;
        this.yellowSuperUse = source.yellowSuperUse;
        this.purpleSuperUse = source.purpleSuperUse;
        this.redSuperUse = source.redSuperUse;
        this.greySuperUse = source.greySuperUse;
        if(source.greySuperPos.length == 0) this.greySuperPos = new int[5];
        else this.greySuperPos = source.greySuperPos;
        this.currentDimension = source.currentDimension;

        if(source.superCooldownKey.length == 0) this.superCooldownKey = new int[EmeraldType.values().length];
        else this.superCooldownKey = source.superCooldownKey;

        //Hyper Form Timer
        this.hyperFormTimer = source.hyperFormTimer;
        this.hyperFormCooldown = source.hyperFormCooldown;
    }

    public void saveNBTData(CompoundTag nbt){
        //Attack Rotation Phase
        nbt.putFloat("AtkRotPhaseX",this.atkRotPhaseX);
        nbt.putFloat("AtkRotPhaseY",this.atkRotPhaseY);

        //Copy Chaos Cooldown
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new int[EmeraldType.values().length];
        nbt.putIntArray("ChaosEmeraldCooldown", chaosCooldownKey);

        //Chaos Emerald Times
        nbt.putByte("DigTimer",this.greyChaosUse);
        nbt.putByte("BlastTimer",this.purpleChaosUse);
        nbt.putByte("ManuscriptKey",this.manuscriptKey);

        //False Super
        nbt.putInt("FalseSuperDur",falseSuperTimer);
        nbt.putByte("FalseChaosSpaz",falseChaosSpaz);

        //Super Form
        nbt.putInt("SuperDur",superFormTimer);
        nbt.putInt("SuperCooldown",superFormCooldown);
        nbt.put("FormAbilities", formProperties.serialize());

        //Copy Super Cooldown
        if(superCooldownKey.length == 0) superCooldownKey = new int[EmeraldType.values().length];
        nbt.putIntArray("SuperEmeraldCooldown", superCooldownKey);

        //Super Emerald Times
        nbt.putByte("BubbleBoost",this.aquaSuperUse);
        nbt.putBoolean("isWaterBoosting",this.isWaterBoosting);
        nbt.putByte("SuperChaosDive",this.greenSuperUse);
        nbt.putByte("SuperChaosGambit",this.yellowSuperUse);
        nbt.putByte("SuperChaosSlicer",this.purpleSuperUse);
        nbt.putShort("SuperChaosInferno",this.redSuperUse);
        nbt.putInt("SuperReprieveTime",this.greySuperUse);
        if(greySuperPos.length == 0) greySuperPos = new int[5];
        nbt.putIntArray("SuperDimReturnCoords", greySuperPos);
        nbt.putString("CurrentDimension",this.currentDimension);

        //Hyper Form
        nbt.putInt("HyperDur",hyperFormTimer);
        nbt.putInt("HyperCooldown",hyperFormCooldown);
    }

    public void loadNBTData(CompoundTag nbt)
    {
        CompoundTag formDetails = nbt.getCompound("FormAbilities");

        //Attack Rotation Phase
        this.atkRotPhaseX = nbt.getFloat("AtkRotPhaseX");
        this.atkRotPhaseY = nbt.getFloat("AtkRotPhaseY");

        //Load Chaos Emerald Cooldown
        chaosCooldownKey = nbt.getIntArray("ChaosEmeraldCooldown");
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new int[EmeraldType.values().length];

        //Load Super Emerald Cooldown
        superCooldownKey = nbt.getIntArray("SuperEmeraldCooldown");
        if(superCooldownKey.length == 0) superCooldownKey = new int[EmeraldType.values().length];

        //Chaos Emerald Times
        greyChaosUse = nbt.getByte("DigTimer");
        purpleChaosUse = nbt.getByte("BlastTimer");
        manuscriptKey = nbt.getByte("ManuscriptKey");

        //Transformation Times
        falseSuperTimer = nbt.getInt("FalseSuperDur");
        falseChaosSpaz = nbt.getByte("FalseChaosSpaz");

        //Super Form
        superFormTimer = nbt.getInt("SuperDur");
        superFormCooldown = nbt.getInt("SuperCooldown");


        //Super Emerald Times
        aquaSuperUse = nbt.getByte("BubbleBoost");
        isWaterBoosting = nbt.getBoolean("isWaterBoosting");
        greenSuperUse = nbt.getByte("SuperChaosDive");
        yellowSuperUse = nbt.getByte("SuperChaosGambit");
        purpleSuperUse = nbt.getByte("SuperChaosSlicer");
        redSuperUse = nbt.getShort("SuperChaosInferno");
        greySuperUse = nbt.getInt("SuperReprieveTime");
        greySuperPos = nbt.getIntArray("SuperDimReturnCoords");
        if(greySuperPos.length == 0) greySuperPos = new int[5];
        currentDimension = nbt.getString("CurrentDimension");

        //Hyper Form
        hyperFormTimer = nbt.getInt("HyperDur");
        hyperFormCooldown = nbt.getInt("HyperCooldown");

        //Form Property Details
        if(superFormTimer > 0) formProperties = new SuperFormProperties(formDetails);
        else if(hyperFormTimer > 0) formProperties = new net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormProperties(formDetails);
        else formProperties = new FormProperties(formDetails);
    }

    public boolean isUsingActiveAbility()
    {
        boolean purpleChaos = this.purpleChaosUse > 0;
        boolean greyChaos = this.greyChaosUse > 0;
        boolean greenSuper = this.greenSuperUse > 0;
        boolean yellowSuper = this.yellowSuperUse > 0;
        boolean purpleSuper = this.purpleSuperUse > 0;

        return greyChaos || purpleChaos || greenSuper || yellowSuper || purpleSuper;
    }
}