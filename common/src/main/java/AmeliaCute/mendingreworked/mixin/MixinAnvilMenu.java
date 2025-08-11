package AmeliaCute.mendingreworked.mixin;

import AmeliaCute.mendingreworked.Mendingreworked;
import AmeliaCute.mendingreworked.util.RepairConfigLoader;
import AmeliaCute.mendingreworked.util.RepairEntry;
import net.minecraft.core.registries.Registries;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu extends ItemCombinerMenu
{
    @Shadow private String itemName;
    @Shadow private int repairItemCountCost;
    @Shadow @Final private DataSlot cost;

    public MixinAnvilMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }


    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void onCreateResult(CallbackInfo ci)
    {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);
        if(left.isEmpty() || right.isEmpty() || !left.isDamageableItem()) return;
        RepairEntry entry;

        try
        { entry = RepairConfigLoader.INSTANCE.GetEntry(left.getItem().builtInRegistryHolder().getRegisteredName()); }
        catch (Exception e)
        { return; }         // Need a datapack

        Item requiredMaterial = entry.getRepair();
        if(requiredMaterial == null || !right.is(requiredMaterial)) return;

        if (!(EnchantmentHelper.getItemEnchantmentLevel(
            player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
            left) > 0)) return;

        int damage = left.getDamageValue();
        if(damage == 0 || damage >= left.getMaxDamage()) return;

        int repairPerItem   = entry.computeRepair(left);
        int materialsNeeded = (int) Math.ceil((float) damage / repairPerItem);
        int materialUsed    = Math.min(right.getCount(), materialsNeeded);

        ItemStack result = left.copy();
        result.setDamageValue(Math.max(0, damage - (repairPerItem * materialUsed)));

        this.resultSlots.setItem(0, result);
        this.cost.set(1);
        this.repairItemCountCost = materialUsed;

        ci.cancel();
    }
}
