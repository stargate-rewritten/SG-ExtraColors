package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.sgrewritten.stargate.api.network.portal.Portal;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

import java.util.UUID;

public class ActivatorDecider extends AbstractDecider {
    private final UUID activatorUUID;

    public ActivatorDecider(ColorTheme theme, UUID activatorUUID) {
        super(theme);
        this.activatorUUID = activatorUUID;
    }

    @Override
    public boolean isApplicable(Portal portal, Material signMaterial) {
        Player possiblePlayer = Bukkit.getPlayer(activatorUUID);
        if(possiblePlayer == null){
            return false;
        }
        return portal.isOpenFor(possiblePlayer);
    }
}
