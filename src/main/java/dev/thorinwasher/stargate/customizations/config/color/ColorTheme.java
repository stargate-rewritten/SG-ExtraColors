package dev.thorinwasher.stargate.customizations.config.color;

import net.md_5.bungee.api.ChatColor;

public record ColorTheme(ChatColor textColor, ChatColor pointerColor, ChatColor errorColor, ChatColor networkColor, Boolean isGlowing) {
    public static final ColorTheme DEFAULT = new ColorTheme(ChatColor.BLACK,ChatColor.WHITE, ChatColor.RED, ChatColor.BLACK, false);
    public static final ColorTheme NONE = new ColorTheme(null,null,null,null,null);

    public ColorTheme(ColorTheme overrideTheme, ColorTheme baseTheme){
        this(overrideTheme.textColor != null ? overrideTheme.textColor : baseTheme.textColor,
                overrideTheme.pointerColor != null ? overrideTheme.pointerColor : baseTheme.pointerColor,
                overrideTheme.errorColor != null ? overrideTheme.errorColor : baseTheme.errorColor,
                overrideTheme.networkColor != null ? overrideTheme.networkColor : baseTheme.networkColor,
                overrideTheme.isGlowing != null ? overrideTheme.isGlowing : baseTheme.isGlowing);
    }
    public ChatColor getColor(ColorThemeOption option){
        return switch (option){
            case TEXT -> this.textColor();
            case POINTER -> this.pointerColor();
            case ERROR -> this.errorColor();
            case NETWORK -> this.networkColor();
        };
    }
}
