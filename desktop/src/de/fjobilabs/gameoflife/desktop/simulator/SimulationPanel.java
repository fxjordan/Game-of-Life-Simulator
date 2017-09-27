package de.fjobilabs.gameoflife.desktop.simulator;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import de.fjobilabs.gameoflife.SimulatorApplication;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 25.09.2017 - 17:27:26
 */
public class SimulationPanel extends JPanel {
    
    private static final long serialVersionUID = 8541720587560047511L;
    
    private final SimulatorApplication simulatorApplication;
    private final LwjglCanvas lwjglCanvas;
    
    public SimulationPanel(Simulator simulator) {
        this.simulatorApplication = simulator.getSimulatorApplication();
        this.lwjglCanvas = new LwjglCanvas(this.simulatorApplication, createApplicationConfig());
        setLayout(new BorderLayout());
        add(this.lwjglCanvas.getCanvas());
        setBorder(BorderFactory.createTitledBorder("Simulation Renderer"));
    }
    
    public void dispose() {
        if (this.lwjglCanvas != null) {
            this.lwjglCanvas.stop();
        }
    }
    
    private LwjglApplicationConfiguration createApplicationConfig() {
        return new LwjglApplicationConfiguration();
    }
}
