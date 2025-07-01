package AmeliaCute.mendingreworked;

import AmeliaCute.mendingreworked.util.RepairConfigLoader;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.server.packs.PackType;

public final class Mendingreworked
{
    public static final String MOD_ID = "mendingreworked";

    public static void init()
    {
        System.err.println("If you see this, Cute Mending Reworked has been initialized correctly!");
        ReloadListenerRegistry.register(PackType.SERVER_DATA, RepairConfigLoader.INSTANCE);
    }
}
