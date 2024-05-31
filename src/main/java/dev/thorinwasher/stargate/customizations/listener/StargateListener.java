package dev.thorinwasher.stargate.customizations.listener;

import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.lineformatter.CustomLineFormatter;
import dev.thorinwasher.stargate.customizations.lineformatter.FormatterRegistry;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.sgrewritten.stargate.api.event.portal.StargateSignFormatPortalEvent;
import org.sgrewritten.stargate.api.network.RegistryAPI;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.api.network.portal.formatting.SignLine;

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
    public void onStargateFormatEvent(StargateSignFormatPortalEvent event){
        Sign sign = event.getSign();
        CustomLineFormatter formatter = registry.getLineFormatter(sign.getLocation());
        if(formatter == null){
            formatter = new CustomLineFormatter(config, (RealPortal) event.getPortal(),sign.getType());
            registry.registerLineFormatter(sign.getLocation(),formatter);
        } else{
            for(SignLine line : event.getLines()){
                formatter.modifyLine(line);
            }
        }
    }
}
