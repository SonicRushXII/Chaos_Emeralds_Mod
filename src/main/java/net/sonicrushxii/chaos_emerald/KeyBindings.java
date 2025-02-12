package net.sonicrushxii.chaos_emerald;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final KeyBindings INSTANCE = new KeyBindings();

    private KeyBindings() {}

    private static final String CATEGORY = "key.categories."+ChaosEmerald.MOD_ID;

    public final KeyMapping chaosTimeStop = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".TimeStop",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_C,-1),
            CATEGORY
    );
    public final KeyMapping chaosTeleport = new KeyMapping(
            "key."+ ChaosEmerald.MOD_ID+".Teleport",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_V,-1),
            CATEGORY
    );
}
