package dev.thorinwasher.stargate.customizations.listener;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.exception.ParseException;
import dev.thorinwasher.stargate.customizations.lineformatter.CustomLineFormatter;
import dev.thorinwasher.stargate.customizations.lineformatter.FormatterRegistry;
import dev.thorinwasher.stargate.customizations.metadata.MetaData;
import dev.thorinwasher.stargate.customizations.metadata.MetaDataWriter;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.sgrewritten.stargate.api.event.StargateSignFormatEvent;
import org.sgrewritten.stargate.api.network.RegistryAPI;
import org.sgrewritten.stargate.api.network.portal.PortalPosition;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

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
    void onStargateFormatEvent(StargateSignFormatEvent event){
        if(!(event.getPortal() instanceof RealPortal realPortal)){
            return;
        }
        Location location = event.getSign().getLocation();
        CustomLineFormatter formatter = registry.getLineFormatter(location);
        if(formatter == null){
            formatter = new CustomLineFormatter(config,realPortal,event.getSign().getType(),null);
            registry.registerLineFormatter(location,formatter);
        } else if(event.getChangedSignColor() != null){
            PortalPosition portalPosition = stargateRegistry.getPortalPosition(location);
            if(portalPosition == null){
                StargateCustomizations.log(Level.WARNING,"Could not find portal position for sign originating from portal " + realPortal.getNetwork().getId() + ":" + realPortal.getNetwork());
                return;
            }
            String previousMetaData = portalPosition.getMetaData(realPortal);
            String newMetaData = null;
            try {
                newMetaData = MetaDataWriter.addMetaData(MetaData.SIGN_COLOR, event.getSignColor().name(), previousMetaData);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            portalPosition.setMetaData(realPortal,newMetaData);
            formatter.setDyeColor(event.getSignColor());
        }
        event.setLineFormatter(formatter);
    }
}
