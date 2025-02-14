package net.sonicrushxii.chaos_emerald.capabilities.all;

import net.minecraft.nbt.CompoundTag;

public class ChaosUseDetails
{
    //Color of the Chaos Emerald Being Used
    public int useColor;

    //Time Stop
    public byte timeStop;
    //Teleport
    public byte teleport;

    public ChaosUseDetails()
    {
        useColor = Integer.MIN_VALUE;

        //Timestop info
        timeStop = 0;
        //Teleport Info
        teleport = 0;
    }

    public ChaosUseDetails(CompoundTag nbt)
    {
        useColor = nbt.contains("Color")?nbt.getInt("Color"):Integer.MIN_VALUE;

        //Timestop Info
        timeStop = nbt.contains("Timestop")?nbt.getByte("Timestop"):0;
        //Teleport Info
        teleport = nbt.contains("Teleport")?nbt.getByte("Teleport"):0;
    }

    public CompoundTag serialize()
    {
        CompoundTag nbt = new CompoundTag();

        if(useColor > -1)                   nbt.putInt("Color", useColor);

        //Timestop
        if(timeStop != 0)                   nbt.putByte("Timestop",timeStop);
        //Teleport
        if(teleport != 0)                   nbt.putByte("Teleport",teleport);

        return nbt;
    }
}