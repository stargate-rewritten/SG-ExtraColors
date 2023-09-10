package dev.thorinwasher.stargate.customizations.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.jetbrains.annotations.Nullable;

public class ColorUtils {

    public static @Nullable ChatColor getColorFromString(@Nullable String colorString){
        try{
            return ChatColor.of(colorString);
        } catch (IllegalArgumentException ignored){
            return null;
        }
    }

    /**
     * Gets the corresponding chat color for the given dye color
     *
     * @param dyeColor <p>The dye color to convert into a chat color</p>
     * @return <p>The chat color corresponding to the given dye color</p>
     */
    public static ChatColor getChatColorFromDyeColor(DyeColor dyeColor) {
        Color color = dyeColor.getColor();
        return ChatColor.of(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
