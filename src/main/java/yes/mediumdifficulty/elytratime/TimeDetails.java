package yes.mediumdifficulty.elytratime;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class TimeDetails {
    int absolute;
    float fraction;

    public TimeDetails(ItemStack item, Level level) {
        int unbreaking = getUnbreakingLevel(item, level);
        int totalTime = timeRemaining(unbreaking, item.getMaxDamage());

        absolute = timeRemaining(unbreaking, item.getMaxDamage() - item.getDamageValue());
        fraction = (float)absolute/(float)totalTime;
    }

    public static int timeRemaining(int unbreaking, int durabilityRemaining) {
        return (durabilityRemaining * (unbreaking + 1)) - 1;
    }

    private static int getUnbreakingLevel(ItemStack item, Level level) {
        return level.registryAccess()
                .lookup(Registries.ENCHANTMENT).flatMap(lookup -> lookup
                        .get(Enchantments.UNBREAKING))
                .map(enchantmentReference -> EnchantmentHelper.getItemEnchantmentLevel(enchantmentReference, item))
                .orElse(0);
    }
}
