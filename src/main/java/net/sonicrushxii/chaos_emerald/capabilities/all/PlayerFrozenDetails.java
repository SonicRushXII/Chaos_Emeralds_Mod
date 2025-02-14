package net.sonicrushxii.chaos_emerald.capabilities.all;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@NotNull
public class PlayerFrozenDetails
{
    public float frozenRotX;
    public float frozenRotY;
    public double frozenPosX;
    public double frozenPosY;
    public double frozenPosZ;

    public PlayerFrozenDetails()
    {
        //Rotation
        frozenRotY = Float.MIN_VALUE;
        frozenRotX = Float.MIN_VALUE;

        //Position
        frozenPosX = Double.MIN_VALUE;
        frozenPosY = Double.MIN_VALUE;
        frozenPosZ = Double.MIN_VALUE;
    }

    public PlayerFrozenDetails(CompoundTag nbt)
    {
        // Rotation
        frozenRotY = nbt.contains("yaw") ? nbt.getFloat("yaw") : Float.MIN_VALUE;
        frozenRotX = nbt.contains("pitch") ? nbt.getFloat("pitch") : Float.MIN_VALUE;

        // Position
        frozenPosX = nbt.contains("x") ? nbt.getDouble("x") : Double.MIN_VALUE;
        frozenPosY = nbt.contains("y") ? nbt.getDouble("y") : Double.MIN_VALUE;
        frozenPosZ = nbt.contains("z") ? nbt.getDouble("z") : Double.MIN_VALUE;
    }

    public CompoundTag serialize()
    {
        CompoundTag nbt = new CompoundTag();

        //Rotation
        if(frozenRotY > Float.MIN_VALUE+0.1) nbt.putFloat("yaw",frozenRotY);
        if(frozenRotX > Float.MIN_VALUE+0.1) nbt.putFloat("pitch",frozenRotX);

        //Pos
        if(frozenPosX > Double.MIN_VALUE+0.1) nbt.putDouble("x",frozenPosX);
        if(frozenPosY > Double.MIN_VALUE+0.1) nbt.putDouble("y",frozenPosY);
        if(frozenPosZ > Double.MIN_VALUE+0.1) nbt.putDouble("z",frozenPosZ);

        return nbt;
    }

    public boolean isFrozen()
    {
        return (frozenPosX > Double.MIN_VALUE+0.1) || (frozenPosY > Double.MIN_VALUE+0.1) ||
                (frozenPosZ > Double.MIN_VALUE+0.1) || (frozenRotY > Float.MIN_VALUE+0.1) ||
                (frozenRotX > Float.MIN_VALUE+0.1);
    }
}