package yes.mediumdifficulty.elytratime;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigMenu {
    public static Screen build(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("title.elytratime.config"))
                .setSavingRunnable(() -> ElytraTime.config.saveToFile());

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.elytratime.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        BooleanListEntry enableTooltipEntry = builder.entryBuilder().startBooleanToggle(Component.translatable("option.elytratime.enable_tooltip"), ElytraTime.config.tooltipEnabled)
                .setSaveConsumer(v -> ElytraTime.config.tooltipEnabled = v)
                .setTooltip(Component.translatable("tooltip.elytratime.enabled"))
                .setDefaultValue(true)
                .build();
        general.addEntry(enableTooltipEntry);

        general.addEntry(entryBuilder.startStrField(Component.translatable("option.elytratime.tooltip_format"), ClientTextUtils.getTooltipFormat())
                .setSaveConsumer(v -> ElytraTime.config.tooltipFormat = v)
                .setTooltip(Component.translatable("tooltip.elytratime.tooltip_format"))
                .setDefaultValue(ClientTextUtils.getValueFromKey("value.elytratime.tooltip_format"))
                .setRequirement(Requirement.isTrue(enableTooltipEntry))
                .build());

//        general.addEntry(entryBuilder.startIntSlider(Text.translatable("option.elytratime.alert_threshold"), ElytraTime.config.alertThreshold, 0, 100)
//                .setSaveConsumer(v -> ElytraTime.config.alertThreshold = v)
//                .setTooltip(Text.translatable("tooltip.elytratime.alert_threshold"))
//                .setDefaultValue(10)
//                .build());

        general.addEntry(entryBuilder.startStrField(Component.translatable("option.elytratime.time_format"), ClientTextUtils.getTimeFormat())
                .setSaveConsumer(v -> ElytraTime.config.timeFormat = v)
                .setTooltip(Component.translatable("tooltip.elytratime.time_format"))
                .setDefaultValue(ClientTextUtils.getValueFromKey("value.elytratime.time_format"))
                .build());

        general.addEntry(entryBuilder.startStrField(Component.translatable("option.elytratime.time_report_format"), ClientTextUtils.getTimeReportFormat())
                .setSaveConsumer(v -> ElytraTime.config.timeReportFormat = v)
                .setTooltip(Component.translatable("tooltip.elytratime.time_report_format"))
                .setDefaultValue(ClientTextUtils.getValueFromKey("value.elytratime.time_report_format"))
                .build());

//        general.addEntry(new NestedListListEntry<Config.Alert, MultiElementListEntry<Config.Alert>>(
//                Text.translatable("title.elytratime.alerts"),
//                ElytraTime.config.alerts,
//                false,
//                Optional::empty,
//                list -> ElytraTime.config.alerts = list,
//                () -> Lists.newArrayList(new Config.Alert(30)),
//                entryBuilder.getResetButtonKey(),
//                true,
//                false,
//                (element, nestedListListEntry) -> {
//                    if (element == null) {
//                        ElytraTime.LOGGER.info("element was null");
//                        Config.Alert newDefaultElemValue = new Config.Alert(10);
//                        return createAlertItem(newDefaultElemValue, entryBuilder);
//                    } else {
//                        return createAlertItem(element, entryBuilder);
//                    }
//                }
//        ));

        return builder.build();
    }

//    private static MultiElementListEntry createAlertItem(Config.Alert alert, ConfigEntryBuilder entryBuilder) {
//        return new MultiElementListEntry<>(Text.translatable("title.elytratime.alert"), alert, Lists.newArrayList(
//                entryBuilder.startIntSlider(Text.translatable("option.elytratime.alert_threshold"), 10, 0, 100)
//                        .setSaveConsumer(v -> alert.time = v)
//                        .build(),
//
//                entryBuilder.startStrField(Text.translatable("option.elytratime.alert_message"), ClientTextUtils.getValueFromKey("value.elytratime.alert_message"))
//                        .setSaveConsumer(v -> alert.message = v)
//                        .build(),
//
//                entryBuilder.startDropdownMenu(Text.translatable("option.elytratime.alert_sound"), DropdownMenuBuilder.TopCellElementBuilder.of(SoundEvents.BLOCK_NOTE_BLOCK_BELL, f -> SoundEvent.of(Identifier.tryParse(f))), e -> e.getId())
//                        .setSelections(Registries.SOUND_EVENT.stream()
////                                .sorted(Comparator.comparing(soundEvent -> soundEvent.getId().toString()))
//                                .map(soundEvent -> DropdownMenuBuilder.TopCellElementBuilder.of(soundEvent))
//                                .collect(Collectors.toCollection(ArrayList::new)))
//                        .build()
//        ), true);
//    }
}
