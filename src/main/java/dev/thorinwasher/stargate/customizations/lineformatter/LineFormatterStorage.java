package dev.thorinwasher.stargate.customizations.lineformatter;

import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.exception.ParseException;
import dev.thorinwasher.stargate.customizations.metadata.MetaData;
import dev.thorinwasher.stargate.customizations.metadata.MetaDataReader;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.sgrewritten.stargate.api.network.RegistryAPI;
import org.sgrewritten.stargate.api.network.portal.BlockLocation;
import org.sgrewritten.stargate.api.network.portal.PortalPosition;
import org.sgrewritten.stargate.api.network.portal.PositionType;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

import java.util.Map;

public class LineFormatterStorage {

    private final RegistryAPI registry;

    public LineFormatterStorage(RegistryAPI registry){
        this.registry = registry;
    }
    public void load(FormatterRegistry formatterRegistry, ColorConfig config){
        Map<BlockLocation, PortalPosition> portalPositions = registry.getPortalPositions();
        for(BlockLocation location : portalPositions.keySet()){
            PortalPosition portalPosition = portalPositions.get(location);
            if(portalPosition.getPositionType() != PositionType.SIGN || !portalPosition.getPluginName().equals("Stargate")){
                continue;
            }
            RealPortal portal = registry.getPortalFromPortalPosition(portalPosition);
            Material signMaterial = location.getLocation().getBlock().getType();
            String metaDataString = portalPosition.getMetaData(portal);
            DyeColor color = null;
            try {
                String colorString = MetaDataReader.getData(metaDataString, MetaData.SIGN_COLOR);
                if(colorString != null) {
                    color = DyeColor.valueOf(colorString);
                }
            } catch (ParseException ignored) {}
            formatterRegistry.registerLineFormatter(location.getLocation(),new CustomLineFormatter(config,portal,signMaterial,color));
        }
    }
}
