package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;

public class ChaosEmeraldCooldown {

    public byte[] cooldownKey = new byte[EmeraldType.values().length];

    public void copyFrom(ChaosEmeraldCooldown source){
        this.cooldownKey = source.cooldownKey;
    }

    public void saveNBTData(CompoundTag nbt){
        //Copy Current Form
        nbt.putByteArray("ChaosCDKey", cooldownKey);
    }

    public void loadNBTData(CompoundTag nbt){
        //Copy Current Form
        cooldownKey = nbt.getByteArray("ChaosCDKey");
    }
}
