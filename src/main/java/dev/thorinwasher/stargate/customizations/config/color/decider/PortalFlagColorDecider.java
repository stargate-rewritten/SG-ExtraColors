package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.flag.PortalFlag;

import java.util.List;

public class PortalFlagColorDecider extends AbstractDecider {
    private final List<PortalFlag> portalFlags;


    public PortalFlagColorDecider(ColorTheme theme, List<PortalFlag> portalFlags){
        super(theme);
        this.portalFlags = portalFlags;
    }

    @Override
    public boolean isApplicable(Portal portal, Material signMaterial){
        return portalFlags.stream().anyMatch(portal::hasFlag);
    }
}
