package amc.mendingreworked.forge;

import AmeliaCute.mendingreworked.Mendingreworked;
import AmeliaCute.mendingreworked.util.RepairConfigLoader;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mendingreworked.MOD_ID)
public final class MendingreworkedForge {
    public MendingreworkedForge() {
        EventBuses.registerModEventBus(Mendingreworked.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Mendingreworked.init();
    }
}
