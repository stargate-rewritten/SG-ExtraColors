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
}
