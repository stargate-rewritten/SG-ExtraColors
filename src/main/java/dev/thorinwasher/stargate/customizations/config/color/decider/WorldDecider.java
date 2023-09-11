package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Material;
import org.bukkit.World;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

public class WorldDecider extends AbstractDecider {
    private final String worldName;

    public WorldDecider(ColorTheme theme, String worldName) {
        super(theme);
        this.worldName = worldName;
    }

    @Override
    public boolean isApplicable(Portal portal, Material signMaterial) {
        if(portal instanceof RealPortal realPortal) {
            World world = realPortal.getExit().getWorld();
            if (world == null) {
                return false;
            }
            return world.getName().equals(worldName);
        }
        return false;
    }
}
