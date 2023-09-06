package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

import java.util.List;

public interface ColorDecider {

    boolean isApplicable(RealPortal portal, Material signMaterial);

    List<ColorDecider> getChildren();

    ColorTheme getTheme();

    void addChildDecider(ColorDecider child);
}
