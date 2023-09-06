package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

import java.util.logging.Level;

public class GateFormatColorDecider extends AbstractDecider {
    private final String gateFormatName;
    public GateFormatColorDecider(ColorTheme theme, String gateFormatName){
        super(theme);
        this.gateFormatName = gateFormatName.strip().toLowerCase();
    }

    @Override
    public boolean isApplicable(RealPortal portal, Material signMaterial) {
        String otherGateFormat = portal.getGate().getFormat().getFileName().toLowerCase();
        StargateCustomizations.log(Level.FINEST, "Matching expected gate format '" + gateFormatName + "' with '" + otherGateFormat + "'");
        return otherGateFormat.equals(gateFormatName);
    }
}
