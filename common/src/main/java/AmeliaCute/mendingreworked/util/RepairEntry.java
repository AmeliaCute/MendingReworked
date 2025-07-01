package AmeliaCute.mendingreworked.util;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RepairEntry
{
    private final ResourceLocation item;
    private final ResourceLocation repair;
    private final float repairFraction;

    private RepairEntry(ResourceLocation item, ResourceLocation repair, float repairFraction)
    {
        this.item = item;
        this.repair = repair;
        this.repairFraction = repairFraction;
    }

    public static RepairEntry fromJson(JsonObject json) {
        String itemStr = json.get("item").getAsString();
        String[] itemParts = itemStr.split(":", 2);
        String itemMod = itemParts.length == 2 ? itemParts[0] : "minecraft";
        ResourceLocation itemRL = parseResourceLocation(itemStr, itemMod);

        String repairStr = json.get("repair").getAsString();
        String[] repairParts = repairStr.split(":", 2);
        String repairMod = repairParts.length == 2 ? repairParts[0] : "minecraft";
        ResourceLocation repairRL = parseResourceLocation(repairStr, repairMod);

        float fraction = json.get("repair_fraction").getAsFloat();
        return new RepairEntry(itemRL, repairRL, fraction);
    }

    private static ResourceLocation parseResourceLocation(String str, String defaultModId) {
        String[] parts = str.split(":", 2);

        if (parts.length == 2) return new ResourceLocation(parts[0], parts[1]);
        else return new ResourceLocation(defaultModId, parts[0]);
    }

    public Item getItem(RegistryAccess access)
    {
        return access.registryOrThrow(Registry.ITEM_REGISTRY).get(item);
    }

    public ResourceLocation getRawItem()
    {
        return item;
    }

    public Item getRepair(RegistryAccess access)
    {
        return access.registryOrThrow(Registry.ITEM_REGISTRY).get(repair);
    }

    public int computeRepair(ItemStack stack)
    {
        return Math.max(1, (int)(stack.getMaxDamage() * repairFraction));
    }
}
