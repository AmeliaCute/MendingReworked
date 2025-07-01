package AmeliaCute.mendingreworked.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
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
        ResourceLocation itemRL = ResourceLocation.bySeparator(itemStr, ':');

        String repairStr = json.get("repair").getAsString();
        ResourceLocation repairRL = ResourceLocation.bySeparator(repairStr, ':');

        float fraction = json.get("repair_fraction").getAsFloat();
        return new RepairEntry(itemRL, repairRL, fraction);
    }

    public Item getItemOrThrown(ResourceLocation obj)
    {
        Supplier<RegistrarManager> managerSupplier = Suppliers.memoize(() -> RegistrarManager.get(item.getNamespace()));
        Registrar<Item> items = managerSupplier.get().get(Registries.ITEM);

        return items.get(obj);
    }

    public Item getItem()
    {
        return getItemOrThrown(item);
    }

    public Item getRepair()
    {
        return getItemOrThrown(repair);
    }

    public int computeRepair(ItemStack stack)
    {
        return Math.max(1, (int)(stack.getMaxDamage() * repairFraction));
    }
}
