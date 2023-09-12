package dev.thorinwasher.stargate.customizations.listener;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.exception.ParseException;
import dev.thorinwasher.stargate.customizations.lineformatter.CustomLineFormatter;
import dev.thorinwasher.stargate.customizations.lineformatter.FormatterRegistry;
import dev.thorinwasher.stargate.customizations.metadata.MetaData;
import dev.thorinwasher.stargate.customizations.metadata.MetaDataWriter;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.sgrewritten.stargate.api.event.gate.StargateSignFormatGateEvent;
import org.sgrewritten.stargate.api.event.portal.StargateSignDyeChangePortalEvent;
import org.sgrewritten.stargate.api.network.RegistryAPI;
import org.sgrewritten.stargate.api.network.portal.PortalPosition;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.api.network.portal.format.SignLine;

import java.util.logging.Level;

public class StargateListener implements Listener {

    private final ColorConfig config;
    private final FormatterRegistry registry;
    private final RegistryAPI stargateRegistry;

    public StargateListener(ColorConfig config, FormatterRegistry registry, RegistryAPI stargateRegistry){
        this.config = config;
        this.registry = registry;
        this.stargateRegistry = stargateRegistry;
    }

    @EventHandler
    void onStargateFormatEvent(StargateSignFormatGateEvent event){
        Sign sign = event.getSign();
        CustomLineFormatter formatter = registry.getLineFormatter(sign.getLocation());
        if(formatter == null){
            formatter = new CustomLineFormatter(config,stargateRegistry.getPortalFromPortalPosition(event.getPortalPosition()),event.getSign().getType());
            registry.registerLineFormatter(sign.getLocation(),formatter);
        } else{
            for(SignLine line : event.getLines()){
                formatter.modifyLine(line);
            }
        }
    }
}
