package dev.thorinwasher.stargate.customizations.config;

public enum ConfigOption {

    LOGGING_LEVEL("logging_level"),
    STYLE_SOURCE("style_source");

    private final String key;

    ConfigOption(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}
