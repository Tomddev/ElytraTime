package yes.mediumdifficulty.elytratime;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Optional;

public class Util {
    public static String formatTimePercent(ItemStack item, String format, String timeFormat, World world) {

        String timeLeft = formatTime(Calculator.timeRemaining(item, world), timeFormat);

        // Get how many minutes are left for color formatting based on %
        String[] MinutesSplit = timeLeft.split("m");
        int MinutesLeft = Integer.parseInt(MinutesSplit[0]);

        if (MinutesLeft == 0) {
            timeLeft = ("§c"+timeLeft);
        } else if (MinutesLeft < 2) {
            timeLeft = ("§e"+timeLeft);
        }


        int percent = (int)(Calculator.fractionRemaining(item, world) * 100.0);

        return format
                .replaceAll("\\[TIME]", timeLeft)
                .replaceAll("\\[%]", String.valueOf(percent));
    }

    public static String formatTime(int time, String format) {
        return format
                .replaceAll("\\[M]", String.valueOf(time / 60))
                .replaceAll("\\[S]", String.valueOf(time % 60));
    }

    public static Optional<ItemStack> findElytra(PlayerEntity player) {
        ItemStack chestPlate = player.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId());

        if (chestPlate.getItem() instanceof ElytraItem) {
            return Optional.of(chestPlate);
        }

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            var found = TrinketsApi.getTrinketComponent(player).flatMap(trinketComponent -> {
                for (var entry : trinketComponent.getAllEquipped()) {
                    ItemStack itemStack = entry.getRight();
                    if (itemStack.getItem() instanceof ElytraItem) {
                        return Optional.of(itemStack);
                    }
                }
                return Optional.empty();
            });

            if (found.isPresent()) {
                return found;
            }
        }

        return Optional.empty();
    }
}
