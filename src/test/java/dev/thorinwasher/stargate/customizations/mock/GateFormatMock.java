package dev.thorinwasher.stargate.customizations.mock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.sgrewritten.stargate.api.gate.GateFormatAPI;
import org.sgrewritten.stargate.api.gate.structure.GateFormatStructureType;
import org.sgrewritten.stargate.api.gate.structure.GateStructure;
import org.sgrewritten.stargate.api.vectorlogic.VectorOperation;

import java.util.List;

public class GateFormatMock implements GateFormatAPI {

    private final String fileName;

    public GateFormatMock(String fileName){
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public List<BlockVector> getControlBlocks() {
        return null;
    }

    @Override
    public Material getIrisMaterial(boolean b) {
        return null;
    }

    @Override
    public boolean isIronDoorBlockable() {
        return false;
    }

    @Override
    public BlockVector getExit() {
        return null;
    }

    @Override
    public GateStructure getStructure(GateFormatStructureType gateFormatStructureType) {
        return null;
    }

    @Override
    public boolean matches(VectorOperation vectorOperation, Location location) {
        return false;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }
}
