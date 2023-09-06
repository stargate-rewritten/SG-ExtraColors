package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

public class BaseColorDecider extends AbstractDecider {

    public BaseColorDecider(ColorTheme theme){
        super(theme);
    }

    @Override
    public boolean isApplicable(RealPortal portal, Material signMaterial) {
        return true;
    }
}
