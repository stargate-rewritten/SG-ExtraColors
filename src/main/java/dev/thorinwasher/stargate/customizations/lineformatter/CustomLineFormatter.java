package dev.thorinwasher.stargate.customizations.lineformatter;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import dev.thorinwasher.stargate.customizations.util.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.sgrewritten.stargate.api.network.Network;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.network.portal.formatting.HighlightingStyle;
import org.sgrewritten.stargate.network.portal.formatting.LineFormatter;

import java.util.logging.Level;

public class CustomLineFormatter implements LineFormatter {

    private final ColorConfig config;
    private final RealPortal portal;
    private final Material signMaterial;
    private final ColorTheme theme;
    private DyeColor dyeColor;

    public CustomLineFormatter(ColorConfig config, RealPortal portal, Material signMaterial, @Nullable DyeColor dyeColor){
        this.config = config;
        this.portal = portal;
        this.signMaterial = signMaterial;
        this.theme = config.getPortalColorTheme(portal,signMaterial,null);
        this.dyeColor = dyeColor;
    }
    @Override
    public String formatPortalName(Portal portal, HighlightingStyle highlightingStyle) {
        if(this.dyeColor != null){
            return ColorUtils.getChatColorFromDyeColor(dyeColor) + highlightingStyle.getHighlightedName(portal.getName());
        }

        ColorTheme theme = this.theme;
        if(portal != this.portal && portal instanceof RealPortal destination){
            theme = config.getPortalColorTheme(this.portal,signMaterial,destination);
        }
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.textColor() + portal.getName() + theme.pointerColor());
    }

    @Override
    public String formatNetworkName(Network network, HighlightingStyle highlightingStyle) {
        if(this.dyeColor != null){
            return ColorUtils.getChatColorFromDyeColor(dyeColor) + highlightingStyle.getHighlightedName(network.getName());
        }
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.networkColor() + network.getName() + theme.pointerColor());
    }

    @Override
    public String formatStringWithHighlighting(String s, HighlightingStyle highlightingStyle) {
        if(this.dyeColor != null){
            return ColorUtils.getChatColorFromDyeColor(dyeColor) + highlightingStyle.getHighlightedName(s);
        }
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.textColor() + s + theme.pointerColor());
    }

    @Override
    public String formatLine(String line) {
        if(this.dyeColor != null){
            return ColorUtils.getChatColorFromDyeColor(dyeColor) + line;
        }
        return theme.textColor() + line;
    }

    @Override
    public String formatErrorLine(String s, HighlightingStyle highlightingStyle) {
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.errorColor() + s + theme.pointerColor());
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }
}
