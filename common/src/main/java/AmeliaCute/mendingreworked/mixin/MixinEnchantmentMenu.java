package AmeliaCute.mendingreworked.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentMenu.class)
public class MixinEnchantmentMenu
{
    @Shadow @Final private RandomSource random;
    @Shadow @Final private DataSlot enchantmentSeed;

    /** Note for people who want to contribute:
     *  I know IN_ENCHANTING_TABLE is where I'm supposed to put minecraft:mending, but it didn't work.
     */
    @Inject(method = "getEnchantmentList", at = @At("HEAD"), cancellable = true)
    private void getEnchantmentList(RegistryAccess registryAccess, ItemStack itemStack, int i, int j, CallbackInfoReturnable<List<EnchantmentInstance>> cir)
    {
        this.random.setSeed((long)(this.enchantmentSeed.get() + i));
        Optional<HolderSet.Named<Enchantment>> optional = registryAccess.lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) cir.setReturnValue(List.of());

        List<Holder<Enchantment>> optionalL = new ArrayList<>(((HolderSet.Named)optional.get()).stream().toList());
        optionalL.add(registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING));

        List<EnchantmentInstance> list = EnchantmentHelper.selectEnchantment(this.random, itemStack, j, optionalL.stream());
        if (itemStack.is(Items.BOOK) && list.size() > 1) list.remove(this.random.nextInt(list.size()));

        cir.setReturnValue(list);
    }
}
