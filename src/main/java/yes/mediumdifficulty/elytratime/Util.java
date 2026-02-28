package yes.mediumdifficulty.elytratime;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import java.util.Optional;

public class Util {
    public static String formatTimePercent(ItemStack item, String format, String timeFormat, Level level) {
        var details = new TimeDetails(item, level);

        var timeLeft = formatTime(details.absolute, timeFormat);

        return format
                .replaceAll("\\[TIME]", timeLeft)
                .replaceAll("\\[%]", String.valueOf(Math.round(details.fraction * 100f)));
    }

    public static String formatTime(int time, String format) {
        return format
                .replaceAll("\\[M]", String.valueOf(time / 60))
                .replaceAll("\\[S]", String.valueOf(time % 60));
    }

    public static Optional<ItemStack> findElytra(Player player) {
        var slot = EquipmentSlot.CHEST.getIndex(36);

        var chestPlate = player.getInventory().getItem(slot);

        if (chestPlate.getItem() == Items.ELYTRA)
            return Optional.of(chestPlate);

        return Optional.empty();
    }
}
