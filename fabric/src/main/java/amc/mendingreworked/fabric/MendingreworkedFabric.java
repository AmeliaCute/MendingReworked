package amc.mendingreworked.fabric;

import AmeliaCute.mendingreworked.Mendingreworked;
import AmeliaCute.mendingreworked.util.RepairConfigLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public final class MendingreworkedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Mendingreworked.init();


    }
}
