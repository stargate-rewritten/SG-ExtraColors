package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.api.network.portal.flag.PortalFlag;

public class PortalFlagColorDecider extends AbstractDecider {
    private final PortalFlag portalFlag;


    public PortalFlagColorDecider(ColorTheme theme, PortalFlag portalFlag){
        super(theme);
        this.portalFlag = portalFlag;
    }

    @Override
    public boolean isApplicable(Portal portal, Material signMaterial){
        return portal.hasFlag(this.portalFlag);
    }
}
