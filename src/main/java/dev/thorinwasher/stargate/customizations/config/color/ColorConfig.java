package dev.thorinwasher.stargate.customizations.config.color;

import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.decider.ColorDecider;
import dev.thorinwasher.stargate.customizations.config.color.decider.DeciderFactory;
import dev.thorinwasher.stargate.customizations.exception.ParseException;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.sgrewritten.stargate.Stargate;
import org.sgrewritten.stargate.api.network.portal.RealPortal;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;

public class ColorConfig {
    private ColorDecider baseDecider;
    private ColorDecider destinationOverrideBaseDecider;

    public ColorTheme getPortalColorTheme(RealPortal portal, Material signMaterial, @Nullable RealPortal destination) {
        ColorTheme overrideTheme = ColorTheme.NONE;
        if(destination != null){
            overrideTheme = getPortalColorTheme(destination,signMaterial, destinationOverrideBaseDecider);
        }
        ColorTheme theme = getPortalColorTheme(portal, signMaterial, baseDecider);
        return new ColorTheme(overrideTheme,theme);
    }

    private ColorTheme getPortalColorTheme(RealPortal portal, Material signMaterial, ColorDecider colorDecider) {
        if(colorDecider == null){
            return ColorTheme.NONE;
        }
        for (ColorDecider child : colorDecider.getChildren()) {
            if(child.isApplicable(portal,signMaterial)){
                return getPortalColorTheme(portal,signMaterial,child);
            }
        }
        return colorDecider.getTheme();
    }



    public void load(File file) throws IOException {
        try(InputStream stream = new FileInputStream(file) ){
            this.load(stream);
        }
    }

    public void load(InputStream stream){
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(stream);

        if(data.get(ColorConfigOption.DESTINATION_STYLING_OVERRIDES.name().toLowerCase()) instanceof Map<?,?> overrideData){
            Map<String,Object> overrideDataConverted = (Map<String,Object>) overrideData;
            destinationOverrideBaseDecider = DeciderFactory.getBaseColorDecider(overrideDataConverted, ColorTheme.NONE);
            StargateCustomizations.log(Level.FINEST,destinationOverrideBaseDecider.getTheme().toString());
            recursiveLoad(destinationOverrideBaseDecider,overrideDataConverted, ColorConfigOption.DESTINATION_STYLING_OVERRIDES.name().toLowerCase());
        }
        if(data.get(ColorConfigOption.STYLING.name().toLowerCase()) instanceof Map<?,?> baseData){
            Map<String,Object> baseDataConverted = (Map<String,Object>) baseData;
            baseDecider = DeciderFactory.getBaseColorDecider(baseDataConverted, ColorTheme.DEFAULT);
            recursiveLoad(baseDecider, baseDataConverted, ColorConfigOption.STYLING.name().toLowerCase());
        }
    }

    private void recursiveLoad(ColorDecider parentDecider, Map<String,Object> data, String path) {
        for(String key : data.keySet()){
            String childPath = path + "." + key;
            try{
                ColorDeciderType.valueOf(key);
                if(data.get(key) instanceof Map<?,?> childData) {
                    ColorDecider child = DeciderFactory.getDecider(parentDecider, key, (Map<String, Object>) childData);
                    parentDecider.addChildDecider(child);
                    recursiveLoad(child,(Map<String, Object>) childData, childPath);
                }
            } catch(IllegalArgumentException ignored) {}
            catch (ParseException e) {
                StargateCustomizations.log(Level.WARNING, "Invalid color configuration in path '" + childPath + "' :" + e.getMessage() );
            }
        }
    }
}
