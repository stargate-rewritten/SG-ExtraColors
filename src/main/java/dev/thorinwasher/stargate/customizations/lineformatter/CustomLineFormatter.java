package dev.thorinwasher.stargate.customizations.lineformatter;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import dev.thorinwasher.stargate.customizations.util.ColorUtils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.sgrewritten.stargate.api.network.Network;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.api.network.portal.format.PortalLine;
import org.sgrewritten.stargate.api.network.portal.format.SignLine;
import org.sgrewritten.stargate.api.network.portal.format.StargateComponent;
import org.sgrewritten.stargate.network.portal.formatting.HighlightingStyle;
import org.sgrewritten.stargate.network.portal.formatting.LineFormatter;

import java.util.List;
import java.util.logging.Level;

public class CustomLineFormatter {

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

    public void modifyLine(SignLine line){
        switch(line.getType()){
            case TEXT -> handleTextLine(line);
            case ERROR -> handleErrorLine(line);
            case THIS_PORTAL -> handleThisPortalLine(line);
            case PORTAL, DESTINATION_PORTAL -> handleOtherPortalLine(line);
            case NETWORK -> handleNetworkLine(line);
        }
    }

    private void changeColorOfComponent(StargateComponent component, ChatColor color){
        String text = ChatColor.stripColor(component.getLegacyText());
        component.setLegacyText(color + text);
    }

    private void handleGeneralLine(SignLine line, ChatColor pointerColor, ChatColor textColor){
        List<StargateComponent> components = line.getComponents();
        if(components.size() == 3){
            changeColorOfComponent(components.get(0),pointerColor);
            changeColorOfComponent(components.get(1),textColor);
            changeColorOfComponent(components.get(2),pointerColor);
        } else {
            for(StargateComponent component : components){
                changeColorOfComponent(component,textColor);
            }
        }
    }

    private void handleTextLine(SignLine line){
        handleGeneralLine(line,theme.pointerColor(),theme.textColor());
    }

    private void handleErrorLine(SignLine line){
        handleGeneralLine(line,theme.pointerColor(),theme.errorColor());
    }

    private void handleThisPortalLine(SignLine line){
        handleGeneralLine(line,theme.pointerColor(),theme.textColor());
    }

    private void handleOtherPortalLine(SignLine line){
        if(line instanceof PortalLine portalLine){
            ColorTheme theme = config.getPortalColorTheme(this.portal,this.signMaterial,portalLine.getPortal());
            handleGeneralLine(line,theme.pointerColor(), theme.textColor());
        }
    }

    private void handleNetworkLine(SignLine line){
        handleGeneralLine(line,theme.pointerColor(),theme.networkColor());
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }
}
