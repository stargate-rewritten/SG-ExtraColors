package dev.thorinwasher.stargate.customizations.listener;

import dev.thorinwasher.stargate.customizations.CustomLineFormatter;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.sgrewritten.stargate.api.event.StargateSignFormatEvent;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.sgrewritten.stargate.network.portal.formatting.LineFormatter;

public class StargateListener implements Listener {

    private final ColorConfig config;

    public StargateListener(ColorConfig config){
        this.config = config;
    }

    @EventHandler
    void onStargateFormatEvent(StargateSignFormatEvent event){
        LineFormatter formatter = new CustomLineFormatter(config,(RealPortal) event.getPortal(),event.getSign().getType());
        event.setLineFormatter(formatter);
    }
}
