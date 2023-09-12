package dev.thorinwasher.stargate.customizations.lineformatter;

import org.bukkit.Location;
import org.sgrewritten.stargate.api.network.portal.BlockLocation;

import java.util.HashMap;
import java.util.Map;

public class FormatterRegistry {

    private final Map<BlockLocation, CustomLineFormatter> formatterMap;

    public FormatterRegistry() {
        this.formatterMap = new HashMap<>();
    }

    public CustomLineFormatter getLineFormatter(Location location){
        return this.formatterMap.get(new BlockLocation(location));
    }

    public void registerLineFormatter(Location location, CustomLineFormatter lineFormatter){
        this.formatterMap.put(new BlockLocation(location),lineFormatter);
    }
}
