package amc.mendingreworked.mixin;

import amc.mendingreworked.util.ModTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu extends ItemCombinerMenu
{
    @Shadow private String itemName;
    @Shadow private int repairItemCountCost;
    @Shadow @Final private DataSlot cost;

    public MixinAnvilMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess)
    {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void onCreateResult(CallbackInfo ci)
    {
        ItemStack left = this.inputSlots.getItem(INPUT_SLOT);
        ItemStack right = this.inputSlots.getItem(ADDITIONAL_SLOT);
        if(left.isEmpty() || right.isEmpty()) return;

        Item requiredMaterial = getRequiredMaterial(left);
        if(requiredMaterial == null || !right.is(requiredMaterial)) return;

        if(!(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, left) > 0)) return;
        if(right.is(Items.ENCHANTED_BOOK)) return;

        int damage = left.getDamageValue();
        if(damage == 0 || damage >= left.getMaxDamage()) return;

        int repairPerItem = getRepairAmount(left, requiredMaterial);
        int materialsNeeded = (int) Math.ceil((float) damage / repairPerItem);
        int materialUsed    = Math.min(right.getCount(), materialsNeeded);

        ItemStack result = left.copy();
        result.setDamageValue(Math.max(0, damage - (repairPerItem * materialUsed)));

        this.resultSlots.setItem(0, result);
        this.cost.set(1);
        this.repairItemCountCost = materialUsed;

        ci.cancel();
    }

    private Item getRequiredMaterial(ItemStack itemStack)
    {
        // For wood and stone, need probably more implementations
        if(itemStack.is(ModTags.WOOD_REPAIR))            return Items.OAK_PLANKS;
        else if(itemStack.is(ModTags.STONE_REPAIR))      return Items.COBBLESTONE;

        else if(itemStack.is(ModTags.IRON_REPAIR))       return Items.IRON_INGOT;
        else if(itemStack.is(ModTags.GOLD_REPAIR))       return Items.GOLD_INGOT;
        else if(itemStack.is(ModTags.DIAMOND_REPAIR))    return Items.DIAMOND;
        else if(itemStack.is(ModTags.NETHERITE_REPAIR))  return Items.NETHERITE_SCRAP;

        else if(itemStack.is(ModTags.PRISMARINE_REPAIR)) return Items.PRISMARINE_SHARD;
        else if(itemStack.is(ModTags.STRING_REPAIR))     return Items.STRING;

        return null;
    }

    private int getRepairAmount(ItemStack itemStack, Item material)
    {
        // 50% repair
        if(material == Items.OAK_PLANKS || material == Items.NETHERITE_SCRAP || material == Items.COBBLESTONE || material == Items.STRING)
            return itemStack.getMaxDamage() / 2;

        // 25% repair
        if(material == Items.IRON_INGOT || material == Items.GOLD_INGOT || material == Items.DIAMOND)
            return itemStack.getMaxDamage() / 4;

        // 12.5% repair
        if(material == Items.PRISMARINE_SHARD)
            return itemStack.getMaxDamage() / 8;

        return 0;
    }
}
