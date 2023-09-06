package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

public class SignMaterialColorDecider extends AbstractDecider {

    private final Material material;

    public SignMaterialColorDecider(ColorTheme theme, Material material){
        super(theme);
        this.material = material;
    }

    @Override
    public boolean isApplicable(RealPortal portal, Material signMaterial) {
        return this.material == signMaterial;
    }

}
