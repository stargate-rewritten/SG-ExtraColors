package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

public class NetworkDecider extends AbstractDecider {
    private final String networkName;

    public NetworkDecider(ColorTheme theme, String networkName) {
        super(theme);
        this.networkName = networkName;
    }

    @Override
    public boolean isApplicable(RealPortal portal, Material signMaterial) {
        return portal.getNetwork().getName().equals(networkName);
    }
}
