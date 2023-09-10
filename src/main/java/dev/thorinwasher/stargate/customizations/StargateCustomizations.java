package dev.thorinwasher.stargate.customizations;

import dev.thorinwasher.stargate.customizations.config.ColorConfigType;
import dev.thorinwasher.stargate.customizations.config.ConfigOption;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.exception.InitializationException;
import dev.thorinwasher.stargate.customizations.lineformatter.FormatterRegistry;
import dev.thorinwasher.stargate.customizations.lineformatter.LineFormatterStorage;
import dev.thorinwasher.stargate.customizations.listener.StargateListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.sgrewritten.stargate.api.StargateAPI;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class StargateCustomizations extends JavaPlugin {
    private static StargateCustomizations instance;
    private static final String CONFIG_FILE = "config.yml";
    private static final String COLOR_CONFIG_FILE = "color.yml";
    private ColorConfig colorConfig;
    private FormatterRegistry registry;
    private StargateAPI stargateAPI;
    private static Level logLevel;

    public static void log(Level level, String msg) {
        if(instance == null){
            System.out.println("[" + level + "]: " + msg);
            return;
        }
        if (level.intValue() < logLevel.intValue()) {
            return;
        }
        if (level.intValue() < Level.INFO.intValue()) {
            instance.getLogger().log(Level.INFO, msg);
        } else {
            instance.getLogger().log(level, msg);
        }
    }

    @Override
    public void onEnable() {
        try {
            load();
        } catch (InitializationException e) {
            StargateCustomizations.log(Level.SEVERE, e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void load() throws InitializationException {
        instance = this;
        loadConfig();
        logLevel = Level.parse(getConfig().getString(ConfigOption.LOGGING_LEVEL.getKey(),Level.INFO.getName()));
        this.colorConfig = loadColorConfig();
        if(colorConfig != null) {
            this.stargateAPI = this.getStargateAPI();
            LineFormatterStorage storage = new LineFormatterStorage(stargateAPI.getRegistry());
            this.registry = new FormatterRegistry();
            storage.load(registry, colorConfig);
            Bukkit.getPluginManager().registerEvents(new StargateListener(this.colorConfig,registry,stargateAPI.getRegistry()), this);
        }

    }

    private void loadConfig(){
        if (!new File(this.getDataFolder(), CONFIG_FILE).exists()) {
            super.saveDefaultConfig();
        }
    }

    private @Nullable ColorConfig loadColorConfig() throws InitializationException {
        String colorConfigTypeString = this.getConfig().getString(ConfigOption.STYLE_SOURCE.getKey());
        if(colorConfigTypeString == null){
            throw new InitializationException("Invalid configuration, missing configuration option " + ConfigOption.STYLE_SOURCE.getKey());
        }
        try{
            ColorConfigType colorConfigType = ColorConfigType.valueOf(colorConfigTypeString.toUpperCase());
            return switch (colorConfigType){
                case NONE -> null;
                case FILE -> getColorConfigFromFile();
                case THEME -> {
                    throw new InitializationException(ColorConfigType.THEME.name() + " type " + ConfigOption.STYLE_SOURCE.getKey() + " is not implemented yet");
                }
            };
        } catch(IllegalArgumentException ignored){
            throw new InitializationException("Invalid configuration, invalid configuration option for " + ConfigOption.STYLE_SOURCE.getKey());
        } catch (IOException e) {
            e.printStackTrace();
            throw new InitializationException("Could not access " + COLOR_CONFIG_FILE);
        }
    }

    private ColorConfig getColorConfigFromFile() throws IOException {
        if(!new File(this.getDataFolder(),COLOR_CONFIG_FILE).exists()){
            this.saveResource("/" + COLOR_CONFIG_FILE,false);
        }
        ColorConfig config = new ColorConfig();
        config.load(new File(this.getDataFolder(),COLOR_CONFIG_FILE));
        return config;
    }

    private StargateAPI getStargateAPI() throws InitializationException {
        ServicesManager servicesManager = this.getServer().getServicesManager();
        RegisteredServiceProvider<StargateAPI> stargateProvider = servicesManager.getRegistration(StargateAPI.class);
        if (stargateProvider != null) {
            return stargateProvider.getProvider();
        } else {
            throw new InitializationException("Unable to hook into Stargate. Make sure the Stargate plugin is installed " +
                    "and enabled.");
        }
    }
}
