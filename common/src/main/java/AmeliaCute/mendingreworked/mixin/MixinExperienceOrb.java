package AmeliaCute.mendingreworked.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
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
        if (hasMending(serverPlayer.getMainHandItem(), serverPlayer.level().registryAccess()) || hasMending(serverPlayer.getOffhandItem(), serverPlayer.level().registryAccess()) || isWearingMending(serverPlayer, serverPlayer.level().registryAccess()))
        {
            serverPlayer.giveExperiencePoints(i);
            cir.cancel();
        }
    }

    @Unique
    private boolean hasMending(ItemStack itemStack, RegistryAccess registryAccess)
    {
        return EnchantmentHelper.getItemEnchantmentLevel(
                registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING),
                itemStack) > 0;
    }

    @Unique
    private boolean isWearingMending(ServerPlayer player, RegistryAccess registryAccess)
    {
        for (ItemStack stack : player.getInventory().getNonEquipmentItems())
        {
            if (hasMending(stack, registryAccess)) return true;
        }
        return false;
    }
}
