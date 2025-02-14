package net.sonicrushxii.chaos_emerald.capabilities.all.form_properties;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@NotNull
public class FormProperties {

    public FormProperties(){}

    public FormProperties(CompoundTag nbt){}

    public CompoundTag serialize()
    {
        return new CompoundTag();
    }
}
