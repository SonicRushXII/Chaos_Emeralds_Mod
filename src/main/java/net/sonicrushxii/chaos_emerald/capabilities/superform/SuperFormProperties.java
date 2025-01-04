package net.sonicrushxii.chaos_emerald.capabilities.superform;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.chaos_emerald.capabilities.all.FormProperties;

import java.util.UUID;

public class SuperFormProperties extends FormProperties
{
    private final byte[] abilityCooldowns;

    public SuperFormProperties()
    {
        abilityCooldowns = new byte[SuperFormAbility.values().length];
    }

    public SuperFormProperties(CompoundTag nbt)
    {
        //Common
        abilityCooldowns = nbt.getByteArray("AbilityCooldowns");
    }

    @Override
    public CompoundTag serialize()
    {
        CompoundTag nbt = new CompoundTag();

        nbt.putByteArray("AbilityCooldowns",abilityCooldowns);
        return nbt;
    }

    //Cooldown Manager
    public byte[] getAllCooldowns() {return abilityCooldowns;}
    public byte getCooldown(SuperFormAbility ability){return abilityCooldowns[ability.ordinal()];}
    public void setCooldown(SuperFormAbility ability, byte seconds){abilityCooldowns[ability.ordinal()] = seconds;}
}
