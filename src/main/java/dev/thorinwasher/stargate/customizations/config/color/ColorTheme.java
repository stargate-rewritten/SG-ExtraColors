package dev.thorinwasher.stargate.customizations.config.color;

import net.md_5.bungee.api.ChatColor;

public record ColorTheme(ChatColor textColor, ChatColor pointerColor, ChatColor errorColor, ChatColor networkColor, boolean isGlowing) {
    public static final ColorTheme DEFAULT = new ColorTheme(ChatColor.BLACK,ChatColor.WHITE, ChatColor.RED, ChatColor.BLACK, false);

    public ChatColor getColor(ColorThemeOption option){
        return switch (option){
            case TEXT -> this.textColor();
            case POINTER -> this.pointerColor();
            case ERROR -> this.errorColor();
            case NETWORK -> this.networkColor();
        };
    }
}
