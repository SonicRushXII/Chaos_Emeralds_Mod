package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.chaos_emerald.capabilities.all.FormProperties;

public class ChaosEmeraldCap
{
    public FormProperties formProperties = new FormProperties();

    public byte[] chaosCooldownKey = new byte[EmeraldAbility.values().length];

    public float atkRotPhaseX = 0.0f;
    public float atkRotPhaseY = 0.0f;

    //Time Stop
    public byte timeStop = 0;
    public boolean playerIsFrozen;
    public double playerFrozenX = 0.0;
    public double playerFrozenY = 0.0;
    public double playerFrozenZ = 0.0;

    //Teleport
    public byte teleport = 0;
    public byte prevGameMode = 0;

    public void copyDeathFrom(ChaosEmeraldCap source)
    {
        //Chaos Emerald Usage
        if(source.chaosCooldownKey.length == 0) this.chaosCooldownKey = new byte[EmeraldAbility.values().length];
        else this.chaosCooldownKey = source.chaosCooldownKey;

        //Attack Phase Rotation
        this.atkRotPhaseX = source.atkRotPhaseX;
        this.atkRotPhaseY = source.atkRotPhaseY;

        //Chaos Emerald Usage
        this.formProperties = source.formProperties;

        //Time Stop
        this.timeStop = source.timeStop;
        this.playerIsFrozen = false;
        this.playerFrozenX = source.playerFrozenX;
        this.playerFrozenY = source.playerFrozenY;
        this.playerFrozenZ = source.playerFrozenZ;

        //Teleport
        this.teleport = source.teleport;
        this.prevGameMode = source.prevGameMode;
    }

    public void copyPerfectFrom(ChaosEmeraldCap source)
    {
        this.copyDeathFrom(source);
        this.playerIsFrozen = source.playerIsFrozen;
    }

    public void saveNBTData(CompoundTag nbt)
    {
        //Copy Chaos Cooldown
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new byte[EmeraldAbility.values().length];
        nbt.putByteArray("ChaosEmeraldCooldown", chaosCooldownKey);

        //Attack Rotation Phase
        nbt.putFloat("AtkRotPhaseX",this.atkRotPhaseX);
        nbt.putFloat("AtkRotPhaseY",this.atkRotPhaseY);

        //Serialize Form Abilities
        nbt.put("FormAbilities", formProperties.serialize());

        //Common Abilities
        //TimeStop
        {
            nbt.putByte("TimeStop", this.timeStop);
            nbt.putBoolean("isFrozenInTime",this.playerIsFrozen);
            nbt.putDouble("frozenPlayerX",this.playerFrozenX);
            nbt.putDouble("frozenPlayerY",this.playerFrozenY);
            nbt.putDouble("frozenPlayerZ",this.playerFrozenZ);
        }

        //Teleport
        {
            nbt.putByte("Teleport", this.teleport);
            nbt.putByte("PrevGameMode",this.prevGameMode);
        }
    }

    public void loadNBTData(CompoundTag nbt)
    {
        //Load Chaos Emerald Cooldown
        chaosCooldownKey = nbt.getByteArray("ChaosEmeraldCooldown");
        if(chaosCooldownKey.length == 0) chaosCooldownKey = new byte[EmeraldAbility.values().length];

        CompoundTag formDetails = nbt.getCompound("FormAbilities");

        //Attack Rotation Phase
        this.atkRotPhaseX = nbt.getFloat("AtkRotPhaseX");
        this.atkRotPhaseY = nbt.getFloat("AtkRotPhaseY");

        //Load Form Properties Tag
        formProperties = new FormProperties(formDetails);

        //Common Abilities
        //TimeStop
        {
            this.timeStop = nbt.getByte("TimeStop");
            this.playerIsFrozen = nbt.getBoolean("isFrozenInTime");
            this.playerFrozenX = nbt.getDouble("frozenPlayerX");
            this.playerFrozenY = nbt.getDouble("frozenPlayerY");
            this.playerFrozenZ = nbt.getDouble("frozenPlayerZ");
        }

        //Teleport
        {
            this.teleport = nbt.getByte("Teleport");
            this.prevGameMode = nbt.getByte("PrevGameMode");
        }
    }
}