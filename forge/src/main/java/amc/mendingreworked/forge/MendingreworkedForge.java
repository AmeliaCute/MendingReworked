package amc.mendingreworked.forge;

import amc.mendingreworked.Mendingreworked;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mendingreworked.MOD_ID)
public final class MendingreworkedForge {
    public MendingreworkedForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Mendingreworked.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Mendingreworked.init();
    }
}
