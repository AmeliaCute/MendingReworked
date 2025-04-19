package AmeliaCute.mendingreworked.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MendingEnchantment.class)
public class MixinMendingEnchantment extends Enchantment
{
    protected MixinMendingEnchantment(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(Rarity.RARE, enchantmentCategory, equipmentSlots);
    }

    @Inject(method = "isTreasureOnly", at = @At("HEAD"), cancellable = true)
    private void isTreasureOnly(CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(false);
    }
}
