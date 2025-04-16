package amc.mendingreworked.util;

import amc.mendingreworked.Mendingreworked;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    private static TagKey<Item> tag(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, name);
        return TagKey.create(Registries.ITEM, id);
    }

    public static final TagKey<Item> WOOD_REPAIR = tag("wood_repairable");
    public static final TagKey<Item> STONE_REPAIR = tag("stone_repairable");
    public static final TagKey<Item> IRON_REPAIR = tag("iron_repairable");
    public static final TagKey<Item> GOLD_REPAIR = tag("gold_repairable");
    public static final TagKey<Item> DIAMOND_REPAIR = tag("diamond_repairable");
    public static final TagKey<Item> NETHERITE_REPAIR = tag("netherite_repairable");
    public static final TagKey<Item> PRISMARINE_REPAIR = tag("prismarine_repairable");
    public static final TagKey<Item> STRING_REPAIR = tag("string_repairable");
}
