package net.sonicrushxii.chaos_emerald;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final KeyBindings INSTANCE = new KeyBindings();

    private KeyBindings() {}

    private static final String CATEGORY = "key.categories."+ChaosEmerald.MOD_ID;

    public final KeyMapping transformButton = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".Transform",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_Y,-1),
            CATEGORY
    );

    public final KeyMapping useAbility1 = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".AbilitySlot_1",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_Z,-1),
            CATEGORY
    );
    public final KeyMapping useAbility2 = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".AbilitySlot_2",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_X,-1),
            CATEGORY
    );
    public final KeyMapping useAbility3 = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".AbilitySlot_3",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_C,-1),
            CATEGORY
    );
    public final KeyMapping useAbility4 = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".AbilitySlot_4",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_V,-1),
            CATEGORY
    );
    public final KeyMapping useAbility5 = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".AbilitySlot_5",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_G,-1),
            CATEGORY
    );
    public final KeyMapping useAbility6 = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".AbilitySlot_6",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_B,-1),
            CATEGORY
    );

    public final KeyMapping doubleJump = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".HyperDoubleJump",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_SPACE,-1),
            CATEGORY
    );
}
