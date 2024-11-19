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
}
