package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class AttributeMultipliers {
    public static final AttributeModifier FALSE_SUPER_STEP_ADDITION = new AttributeModifier(new UUID(0x1234167890AB6DEFL, 0xFEBCBA01F7654C21L),
            "false_super_step", 1.0F, AttributeModifier.Operation.ADDITION);
}