package net.sonicrushxii.chaos_emerald.capabilities.hyperform;

import net.minecraft.nbt.CompoundTag;
import net.sonicrushxii.chaos_emerald.capabilities.all.FormProperties;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormAbility;

public class HyperFormProperties extends FormProperties
{
    private final byte[] abilityCooldowns;

    public HyperFormProperties()
    {
        abilityCooldowns = new byte[HyperFormAbility.values().length];
    }

    public HyperFormProperties(CompoundTag nbt)
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
    public byte getCooldown(HyperFormAbility ability){return abilityCooldowns[ability.ordinal()];}
    public void setCooldown(HyperFormAbility ability, byte seconds){abilityCooldowns[ability.ordinal()] = seconds;}
}
