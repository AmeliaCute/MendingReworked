package AmeliaCute.mendingreworked.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class RepairConfigLoader extends SimpleJsonResourceReloadListener
{
    public static final String FOLDER = "repair_configs";
    public static final RepairConfigLoader INSTANCE = new RepairConfigLoader();
    private final HashMap<String, RepairEntry> configs_entry = new HashMap<>();


    private RepairConfigLoader() {
        super(Codec.PASSTHROUGH, new FileToIdConverter(FOLDER, ".json"));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void apply(Object object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Map<Identifier, Dynamic<JsonElement>> list =
                (Map<Identifier, Dynamic<JsonElement>>) object;

        configs_entry.clear();
        for (Map.Entry<Identifier, Dynamic<JsonElement>> entry : list.entrySet())
        {
            JsonArray array = entry.getValue().getValue().getAsJsonArray();
            for(JsonElement element : array)
            {
                RepairEntry repairEntry = RepairEntry.fromJson(element.getAsJsonObject());

                configs_entry.put(repairEntry.getItem().toString(), repairEntry);
            }
        }

        System.out.println("Repair Config Registry has been loaded! (" + configs_entry.size() + " items)");
    }

    public RepairEntry GetEntry(String key)
    {
        if(configs_entry.containsKey(key))
            return configs_entry.get(key);

        return null;
    }
}
