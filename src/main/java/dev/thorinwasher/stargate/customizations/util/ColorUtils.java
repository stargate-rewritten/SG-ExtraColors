package dev.thorinwasher.stargate.customizations.util;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    public static ChatColor getColorFromString(String colorString){
        try{
            return ChatColor.of(colorString);
        } catch (IllegalArgumentException ignored){
            return null;
        }
    }
}
