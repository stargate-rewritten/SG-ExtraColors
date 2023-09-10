package dev.thorinwasher.stargate.customizations.lineformatter;

import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.exception.ParseException;
import dev.thorinwasher.stargate.customizations.metadata.MetaData;
import dev.thorinwasher.stargate.customizations.metadata.MetaDataReader;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.sgrewritten.stargate.api.network.Network;
import org.sgrewritten.stargate.api.network.RegistryAPI;
import org.sgrewritten.stargate.api.network.portal.*;
import org.sgrewritten.stargate.network.portal.formatting.LineFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
