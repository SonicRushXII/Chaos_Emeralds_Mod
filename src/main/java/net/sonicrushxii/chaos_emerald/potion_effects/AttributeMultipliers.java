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

    public static final AttributeModifier SUPER_WALK_SPEED = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C14L),
            "super_speed", 0.05F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SUPER_BOOST_SPEED = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C15L),
            "super_speed", 0.75F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SUPER_STEP_ADDITION = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C16L),
            "super_step", 1.0F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SUPER_ARMOR = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C17L),
            "super_armor", 20.0F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier SUPER_KB_RESIST = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C18L),
            "super_kb_resist", 0.85F, AttributeModifier.Operation.ADDITION);

    public static final AttributeModifier GAMBIT_SLOW = new AttributeModifier(new UUID(0x4321767890AB6DEFL, 0xFEBCBA09F7654C19L),
            "chaos_gambit_slow", -1.0F, AttributeModifier.Operation.ADDITION);

    public static final AttributeModifier HYPER_WALK_SPEED = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C14L),
            "super_speed", 0.05F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HYPER_BOOST_SPEED = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C15L),
            "super_speed", 0.85F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HYPER_STEP_ADDITION = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C16L),
            "super_step", 2.0F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HYPER_ARMOR = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C17L),
            "super_armor", 20.0F, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HYPER_KB_RESIST = new AttributeModifier(new UUID(0x4321167890AB6DEFL, 0xFEBCBA01F7654C18L),
            "super_kb_resist", 1.0F, AttributeModifier.Operation.ADDITION);
}