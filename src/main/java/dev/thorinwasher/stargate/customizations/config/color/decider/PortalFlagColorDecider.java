package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

public class PortalFlagColorDecider extends AbstractDecider {
    private final Character portalFlag;


    public PortalFlagColorDecider(ColorTheme theme, Character portalFlag){
        super(theme);
        this.portalFlag = portalFlag;
    }

    @Override
    public boolean isApplicable(RealPortal portal, Material signMaterial){
        return portal.hasFlag(this.portalFlag);
    }
}
