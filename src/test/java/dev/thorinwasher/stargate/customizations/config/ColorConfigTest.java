package dev.thorinwasher.stargate.customizations.config;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import dev.thorinwasher.stargate.customizations.StargateCustomizations;
import dev.thorinwasher.stargate.customizations.config.color.ColorConfig;
import dev.thorinwasher.stargate.customizations.config.color.ColorTheme;
import dev.thorinwasher.stargate.customizations.mock.GateMock;
import dev.thorinwasher.stargate.customizations.mock.NetworkMock;
import dev.thorinwasher.stargate.customizations.mock.PortalMock;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sgrewritten.stargate.api.gate.GateAPI;
import org.sgrewritten.stargate.api.network.portal.RealPortal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;

class ColorConfigTest {

    private ColorConfig colorConfig;
    private NetworkMock networkMock;
    private ServerMock server;
    private WorldMock ignoredWorld;
    private Location ignoredExitLocation;
    private GateMock ignoredGate;

    @BeforeEach
    void setUp() throws IOException {
        this.colorConfig = new ColorConfig();
        this.server = MockBukkit.mock();
        try(InputStream stream = new FileInputStream("src/test/resources/testColorConfig.yml")) {
            this.colorConfig.load(stream);
        }
        this.networkMock = new NetworkMock("ignoredName");
        this.ignoredWorld = server.addSimpleWorld("ignored");
        this.ignoredExitLocation = new Location(ignoredWorld, 0, 0, 0);
        this.ignoredGate = new GateMock("ignored.gate");
    }

    @AfterEach
    void tearDown(){
        MockBukkit.unmock();
    }
    @Test
    void getPortalColorTheme_getDefaultTheme(){
        RealPortal portal = new PortalMock(ignoredGate,networkMock,ignoredExitLocation);
        ColorTheme theme = this.colorConfig.getPortalColorTheme(portal, Material.ACACIA_WALL_SIGN,null);
        Assertions.assertEquals(ChatColor.GRAY, theme.textColor());
        Assertions.assertEquals(ChatColor.BLUE, theme.pointerColor());
        Assertions.assertEquals(ChatColor.RED, theme.errorColor());
        Assertions.assertEquals(ChatColor.GREEN, theme.networkColor());
    }

    @Test
    void getPortalColorTheme_getFlagDeterminedColor(){
        RealPortal portal = new PortalMock(ignoredGate, networkMock, ignoredExitLocation, Set.of('A'));
        ColorTheme theme = this.colorConfig.getPortalColorTheme(portal, Material.ACACIA_WALL_SIGN,null);
        Assertions.assertEquals(ChatColor.BLACK, theme.textColor());
        Assertions.assertEquals(ChatColor.WHITE, theme.pointerColor());
        Assertions.assertEquals(ChatColor.RED, theme.errorColor());
        Assertions.assertEquals(ChatColor.GREEN, theme.networkColor());
    }

    @Test
    void getPortalColorTheme_getFlagAndGateDeterminedColor(){
        GateAPI gate = new GateMock("something.gate");
        RealPortal portal = new PortalMock(gate, networkMock, ignoredExitLocation, Set.of('A'));
        ColorTheme theme = this.colorConfig.getPortalColorTheme(portal, Material.ACACIA_WALL_SIGN,null);
        Assertions.assertEquals(ChatColor.RED, theme.textColor());
        Assertions.assertEquals(ChatColor.WHITE, theme.pointerColor());
        Assertions.assertEquals(ChatColor.RED, theme.errorColor());
        Assertions.assertEquals(ChatColor.GREEN, theme.networkColor());
    }

    @Test
    void getPortalColorTheme_destinationOverride(){
        RealPortal portal = new PortalMock(ignoredGate, networkMock, ignoredExitLocation);
        RealPortal destination = new PortalMock(ignoredGate, networkMock, ignoredExitLocation);
        ColorTheme theme = this.colorConfig.getPortalColorTheme(portal, Material.ACACIA_WALL_SIGN,destination);
        Assertions.assertEquals(ChatColor.GRAY, theme.textColor());
        Assertions.assertEquals(ChatColor.BLUE, theme.pointerColor());
        Assertions.assertEquals(ChatColor.DARK_BLUE, theme.errorColor());
        Assertions.assertEquals(ChatColor.GREEN, theme.networkColor());
    }

    @Test
    void getPortalColorTheme_DefaultConfig() throws IOException {
        try(InputStream stream = StargateCustomizations.class.getResourceAsStream("/color.yml")){
            ColorConfig defaultConfig = new ColorConfig();
            defaultConfig.load(stream);
            RealPortal portal = new PortalMock(ignoredGate, networkMock, ignoredExitLocation);
            ColorTheme theme = defaultConfig.getPortalColorTheme(portal, Material.ACACIA_WALL_SIGN, null);
            Assertions.assertNotNull(theme.errorColor());
            Assertions.assertNotNull(theme.textColor());
            Assertions.assertNotNull(theme.pointerColor());
            Assertions.assertNotNull(theme.networkColor());
            Assertions.assertNotNull(theme.isGlowing());
        }
    }
}