package dev.thorinwasher.stargate.customizations.config.color.decider;

import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDecider implements ColorDecider {

    private final ColorTheme theme;
    private final List<ColorDecider> children;

    public AbstractDecider(ColorTheme theme){
        this.theme = theme;
        this.children = new ArrayList<>();
    }

    @Override
    public void addChildDecider(ColorDecider child) {
        this.children.add(child);
    }

    @Override
    public ColorTheme getTheme(){
        return this.theme;
    }

    @Override
    public List<ColorDecider> getChildren(){
        return this.children;
    }
}
