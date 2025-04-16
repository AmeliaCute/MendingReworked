package amc.mendingreworked.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrb.class)
public abstract class MixinExperienceOrb
{
    @Inject(method = "repairPlayerItems", at = @At("HEAD"), cancellable = true)
    public void onRepairPlayerItems(ServerPlayer serverPlayer, int i, CallbackInfoReturnable<Integer> cir)
    {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA 1");
        if (hasMending(serverPlayer.getMainHandItem(), serverPlayer.level().registryAccess()) || hasMending(serverPlayer.getOffhandItem(), serverPlayer.level().registryAccess()))
            cir.cancel();
    }

    @Unique
    private boolean hasMending(ItemStack itemStack, RegistryAccess registryAccess)
    {
        Holder<Enchantment> mendingHolder = registryAccess.registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(Enchantments.MENDING);
        return EnchantmentHelper.getItemEnchantmentLevel(mendingHolder, itemStack) > 0;
    }
}
