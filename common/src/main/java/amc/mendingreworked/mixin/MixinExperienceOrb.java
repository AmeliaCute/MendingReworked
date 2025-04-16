package amc.mendingreworked.mixin;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrb.class)
public abstract class MixinExperienceOrb
{
    @Inject(method = "repairPlayerItems", at = @At("HEAD"), cancellable = true)
    public void onRepairPlayerItems(Player player, int i, CallbackInfoReturnable<Integer> cir)
    {
        if(hasMending(player.getMainHandItem()) || hasMending(player.getOffhandItem())) cir.cancel();
    }

    @Unique
    private boolean hasMending(ItemStack itemStack)
    {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, itemStack) > 0;
    }
}
