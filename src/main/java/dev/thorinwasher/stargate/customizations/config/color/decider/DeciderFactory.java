package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfigOption;
import dev.thorinwasher.stargate.customizations.config.color.ColorDeciderType;
import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import dev.thorinwasher.stargate.customizations.config.color.ColorThemeOption;
import dev.thorinwasher.stargate.customizations.exception.ParseException;
import dev.thorinwasher.stargate.customizations.util.ColorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.flag.CustomFlag;
import org.sgrewritten.stargate.api.network.portal.flag.PortalFlag;
import org.sgrewritten.stargate.api.network.portal.flag.StargateFlag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class DeciderFactory {

    public static ColorDecider getDecider(ColorDecider parent, String key, Map<String, Object> data) throws ParseException {
        if (data == null) {
            throw new ParseException("Unknown exception, contact developers");
        }
        Object value = data.get(ColorConfigOption.VALUE.name().toLowerCase());
        if (value != null) {
            ColorTheme theme = createTheme(parent.getTheme(), data);
            return createColorDecider(key, value, theme);
        } else {
            throw new ParseException("Missing key '" + ColorConfigOption.VALUE.name().toLowerCase() + "'");
        }
    }

    public static BaseColorDecider getBaseColorDecider(Map<String, Object> data, ColorTheme defaultTheme) {
        ColorTheme theme = createTheme(defaultTheme, data);
        StargateCustomizations.log(Level.FINEST, data.keySet().toString());
        return new BaseColorDecider(theme);
    }

    private static ColorTheme createTheme(ColorTheme parentTheme, Map<String, Object> data) {
        Map<ColorThemeOption, ChatColor> colorThemeOptions = new HashMap<>();
        for (ColorThemeOption option : ColorThemeOption.values()) {
            if (data.get(option.name().toLowerCase()) instanceof String optionValue) {
                colorThemeOptions.put(option, ColorUtils.getColorFromString(optionValue));
            }
        }
        ColorTheme overrideTheme = new ColorTheme(colorThemeOptions.get(ColorThemeOption.TEXT),
                colorThemeOptions.get(ColorThemeOption.POINTER),
                colorThemeOptions.get(ColorThemeOption.ERROR),
                colorThemeOptions.get(ColorThemeOption.NETWORK),
                (Boolean) data.get(ColorConfigOption.GLOWING.name().toLowerCase()));
        return new ColorTheme(overrideTheme, parentTheme);
    }

    private static ColorDecider createColorDecider(String key, Object value, ColorTheme theme) throws ParseException {
        ColorDeciderType type = ColorDeciderType.valueOf(key.toUpperCase());
        try {
            return switch (type) {
                case STARGATE_NETWORK -> new NetworkDecider(theme, (String) value);
                case STARGATE_SIGN_MATERIAL ->
                        new SignMaterialColorDecider(theme, Material.getMaterial((String) value));
                case STARGATE_PLAYER_OWNER_UUID -> new OwnerDecider(theme, UUID.fromString((String) value));
                case STARGATE_PLAYER_ACTIVATOR_UUID -> new ActivatorDecider(theme, UUID.fromString((String) value));
                case STARGATE_PORTAL_DESIGN -> new GateFormatColorDecider(theme, (String) value);
                case STARGATE_PORTAL_WORLD -> new WorldDecider(theme, (String) value);
                case STARGATE_FLAG -> new PortalFlagColorDecider(theme, fetchFlag((String) value));
            };
        } catch (ClassCastException e) {
            throw new ParseException("Invalid input type for " + ColorConfigOption.VALUE.name().toLowerCase() + " '" + value + "'");
        }
    }

    private static PortalFlag fetchFlag(String aString) throws ParseException {
        if (aString.length() == 1) {
            return CustomFlag.getOrCreate(aString.charAt(0));
        }
        try {
            return StargateFlag.valueOf(aString.toUpperCase());
        } catch (IllegalArgumentException e) {
            StringBuilder builder = new StringBuilder(ColorConfigOption.VALUE.name().toLowerCase() + " must be either a character, or one of the following strings: \n");
            for (PortalFlag flag : StargateFlag.values()) {
                builder.append(" ");
                builder.append(flag);
            }
            throw new ParseException(builder.toString());
        }
    }
}
