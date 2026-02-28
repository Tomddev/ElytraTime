package yes.mediumdifficulty.elytratime;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public class ElytraTimeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerEvents();
        registerKeybindings();

        ElytraTime.LOGGER.info("[ElytraTime] Initialised on client");
    }

    private static final KeyMapping.Category ELYTRA_TIME_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("elytratime", "controls"));

    private void registerKeybindings() {
        KeyMapping printTime = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.elytratime.show",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_F4,
                ELYTRA_TIME_CATEGORY
        ));

        KeyMapping openConfig = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.elytratime.config",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                ELYTRA_TIME_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (printTime.consumeClick() && client.player != null) {
                var found = Util.findElytra(client.player);

                if (found.isPresent())
                    client.player.displayClientMessage(Component.literal(
                            Util.formatTimePercent(found.get(),ClientTextUtils.getTimeReportFormat(), ClientTextUtils.getTimeFormat(), Minecraft.getInstance().level))
                            .withStyle(ChatFormatting.GREEN), false);
                else
                    client.player.displayClientMessage(Component.translatable("message.elytratime.no_elytra").withStyle(ChatFormatting.RED), false);
            }

            if (openConfig.consumeClick()) {
                client.setScreen(ConfigMenu.build(client.screen));
            }
        });
    }

    private static void registerEvents() {
        ItemTooltipCallback.EVENT.register((itemStack, context, type, lines) -> {
            var item = itemStack.getItem();

            if (item == Items.ELYTRA && ElytraTime.config.tooltipEnabled) {
                var text = Component.literal(
                        Util.formatTimePercent(itemStack, ClientTextUtils.getTooltipFormat(), ClientTextUtils.getTimeFormat(), Minecraft.getInstance().level)
                    ).withStyle(ChatFormatting.GREEN);

                lines.add(1, text);
            }
        });
    }
}
