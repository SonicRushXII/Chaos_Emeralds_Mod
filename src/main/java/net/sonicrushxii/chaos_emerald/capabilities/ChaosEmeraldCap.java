package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.chaos_emerald.capabilities.all.FormProperties;

public class ChaosEmeraldCap {
    public FormProperties formProperties = new FormProperties();
    public float atkRotPhaseX = 0.0f;
    public float atkRotPhaseY = 0.0f;

    public byte timeStop = 0;
    public byte teleport = 0;


    public void copyFrom(ChaosEmeraldCap source) {
        //Attack Phase Rotation
        this.atkRotPhaseX = source.atkRotPhaseX;
        this.atkRotPhaseY = source.atkRotPhaseY;

        //Chaos Emerald Usage
        this.formProperties = source.formProperties;

        //Time Stop
        this.timeStop = source.timeStop;

        //Teleport
        this.teleport = source.teleport;
    }


    public void saveNBTData(CompoundTag nbt){
        //Attack Rotation Phase
        nbt.putFloat("AtkRotPhaseX",this.atkRotPhaseX);
        nbt.putFloat("AtkRotPhaseY",this.atkRotPhaseY);

        //Serialize Form Abilities
        nbt.put("FormAbilities", formProperties.serialize());

        //Common Abilities
        nbt.putByte("TimeStop", this.timeStop);
        nbt.putByte("Teleport", this.teleport);
    }

    public void loadNBTData(CompoundTag nbt)
    {
        CompoundTag formDetails = nbt.getCompound("FormAbilities");

        //Attack Rotation Phase
        this.atkRotPhaseX = nbt.getFloat("AtkRotPhaseX");
        this.atkRotPhaseY = nbt.getFloat("AtkRotPhaseY");

        //Load Form Properties Tag
        formProperties = new FormProperties(formDetails);

        //Common Abilities
        this.timeStop = nbt.getByte("TimeStop");
        this.teleport = nbt.getByte("Teleport");
    }
}