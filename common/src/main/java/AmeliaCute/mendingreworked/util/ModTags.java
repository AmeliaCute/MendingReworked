package AmeliaCute.mendingreworked.util;

import AmeliaCute.mendingreworked.Mendingreworked;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    public static final TagKey<Item> WOOD_REPAIR        =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "wood_repairable"));

    public static final TagKey<Item> STONE_REPAIR       =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "stone_repairable"));

    public static final TagKey<Item> IRON_REPAIR        =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "iron_repairable"));

    public static final TagKey<Item> GOLD_REPAIR        =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "gold_repairable"));

    public static final TagKey<Item> DIAMOND_REPAIR     =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "diamond_repairable"));

    public static final TagKey<Item> NETHERITE_REPAIR   =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "netherite_repairable"));

    public static final TagKey<Item> PRISMARINE_REPAIR  =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "prismarine_repairable"));

    public static final TagKey<Item> STRING_REPAIR      =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "string_repairable"));

    public static final TagKey<Item> BREEZE_REPAIR      =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Mendingreworked.MOD_ID, "breeze_repairable"));
}
