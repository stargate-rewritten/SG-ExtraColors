package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

import java.util.UUID;

public class OwnerDecider extends AbstractDecider {
    private final UUID ownerUUID;

    public OwnerDecider(ColorTheme theme, UUID ownerUUID) {
        super(theme);
        this.ownerUUID = ownerUUID;
    }

    @Override
    public boolean isApplicable(RealPortal portal, Material signMaterial) {
        return portal.getOwnerUUID().equals(ownerUUID);
    }
}
