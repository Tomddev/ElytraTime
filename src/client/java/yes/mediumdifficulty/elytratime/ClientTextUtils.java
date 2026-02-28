package yes.mediumdifficulty.elytratime;

import net.minecraft.locale.Language;

public class ClientTextUtils {
    public static String getTooltipFormat() {
        return ElytraTime.config.tooltipFormat.isEmpty() ? getValueFromKey("value.elytratime.tooltip_format") : ElytraTime.config.tooltipFormat;
    }

    public static String getTimeFormat() {
        return ElytraTime.config.timeFormat.isEmpty() ? getValueFromKey("value.elytratime.time_format") : ElytraTime.config.timeFormat;
    }

    public static String getTimeReportFormat() {
        return ElytraTime.config.timeReportFormat.isEmpty() ? getValueFromKey("value.elytratime.time_report_format") : ElytraTime.config.timeReportFormat;
    }

    public static String getValueFromKey(String key) {
        return Language.getInstance().getOrDefault(key);
    }
}
