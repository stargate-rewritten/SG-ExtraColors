package dev.thorinwasher.stargate.customizations;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class StargateCustomizations extends JavaPlugin {
    private static StargateCustomizations instance;

    public static void log(Level level, String msg) {
        if(instance == null){
            System.out.println("[" + level + "]: " + msg);
            return;
        }
        instance.getLogger().log(level,msg);
    }

    @Override
    public void onEnable() {
        instance = this;
    }
}
