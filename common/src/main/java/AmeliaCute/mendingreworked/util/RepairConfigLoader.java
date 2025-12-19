package AmeliaCute.mendingreworked.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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


    private RepairConfigLoader() {
        super(new Gson(), FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        configs_entry.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet())
        {
            JsonArray array = entry.getValue().getAsJsonArray();
            for(JsonElement element : array)
            {
                RepairEntry repairEntry = RepairEntry.fromJson(element.getAsJsonObject());

                try
                {
                    configs_entry.put(repairEntry.getItem().toString(), repairEntry);
                } catch (Exception e)
                {
                    System.err.println("[Mending Reworked] Invalid repair entry in " + entry.getKey());
                    e.printStackTrace();
                }
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