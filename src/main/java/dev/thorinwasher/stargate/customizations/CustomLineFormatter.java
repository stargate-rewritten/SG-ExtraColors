package dev.thorinwasher.stargate.customizations;

import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.Network;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.network.portal.formatting.HighlightingStyle;
import org.sgrewritten.stargate.network.portal.formatting.LineFormatter;

public class CustomLineFormatter implements LineFormatter {

    private final ColorConfig config;
    private final RealPortal portal;
    private final Material signMaterial;
    private final ColorTheme theme;

    public CustomLineFormatter(ColorConfig config, RealPortal portal, Material signMaterial){
        this.config = config;
        this.portal = portal;
        this.signMaterial = signMaterial;
        this.theme = config.getPortalColorTheme(portal,signMaterial,null);
    }
    @Override
    public String formatPortalName(Portal portal, HighlightingStyle highlightingStyle) {
        ColorTheme theme = this.theme;
        if(portal != this.portal && portal instanceof RealPortal destination){
            theme = config.getPortalColorTheme(this.portal,signMaterial,destination);
        }
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.textColor() + portal.getName() + theme.pointerColor());
    }

    @Override
    public String formatNetworkName(Network network, HighlightingStyle highlightingStyle) {
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.networkColor() + network.getName() + theme.pointerColor());
    }

    @Override
    public String formatStringWithHighlighting(String s, HighlightingStyle highlightingStyle) {
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.textColor() + s + theme.pointerColor());
    }

    @Override
    public String formatLine(String s) {
        return theme.textColor() + s;
    }

    @Override
    public String formatErrorLine(String s, HighlightingStyle highlightingStyle) {
        return theme.pointerColor() + highlightingStyle.getHighlightedName(theme.errorColor() + s + theme.pointerColor());
    }
}
