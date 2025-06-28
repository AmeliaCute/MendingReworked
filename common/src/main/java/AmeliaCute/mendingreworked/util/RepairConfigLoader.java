package AmeliaCute.mendingreworked.util;

import AmeliaCute.mendingreworked.Mendingreworked;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
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

    public RepairConfigLoader()
    {
        super(new GsonBuilder().create(), FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        configs_entry.clear();

        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet())
        {
            JsonArray array = entry.getValue().getAsJsonArray();
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
