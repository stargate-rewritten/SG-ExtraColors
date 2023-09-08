package dev.thorinwasher.stargate.customizations.config;

public enum ColorConfigType {
    /**
     * No color config should be used, use the core implementation
     */
    NONE,

    /**
     * Use the color.yml color config
     */
    FILE,

    /**
     * Use a config from an online editor
     */
    THEME;
}
