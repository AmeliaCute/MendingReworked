package AmeliaCute.mendingreworked.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class RepairEntry
{
    private final Identifier item;
    private final Identifier repair;
    private final float repairFraction;

    private RepairEntry(Identifier item, Identifier repair, float repairFraction)
    {
        this.item = item;
        this.repair = repair;
        this.repairFraction = repairFraction;
    }

    public static RepairEntry fromJson(JsonObject json)
    {
        String itemStr = json.get("item").getAsString();
        Identifier itemRL = Identifier.bySeparator(itemStr, ':');

        String repairStr = json.get("repair").getAsString();
        Identifier repairRL = Identifier.bySeparator(repairStr, ':');

        float fraction = json.get("repair_fraction").getAsFloat();
        return new RepairEntry(itemRL, repairRL, fraction);
    }

    public Item getItemOrThrown(Identifier obj)
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
