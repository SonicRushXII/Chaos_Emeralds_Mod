package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class AttributeMultipliers {
    public static final AttributeModifier FALSE_SUPER_STEP_ADDITION = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C10L),
            "false_super_step", 1.0F, AttributeModifier.Operation.ADDITION);

    public static final AttributeModifier BUBBLE_BOOST_STEP = new AttributeModifier(new UUID(0x4321767890AB6DEFL, 0xFEBCBA09F7654C11L),
            "bubble_boost_step", 1.0F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier BUBBLE_BOOST_SPEED = new AttributeModifier(new UUID(0x4321767890AB6DEFL, 0xFEBCBA09F7654C12L),
            "bubble_boost_speed", 1.0F, AttributeModifier.Operation.ADDITION);

    public static final AttributeModifier GAMBIT_SLOW = new AttributeModifier(new UUID(0x4321767890AB6DEFL, 0xFEBCBA09F7654C13L),
            "chaos_gambit_slow", -1.0F, AttributeModifier.Operation.ADDITION);
}