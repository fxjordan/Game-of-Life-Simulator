package de.fjobilabs.gameoflife.desktop.simulator;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.SimulatorApplication;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.CellularAutomatonSimulation;
import de.fjobilabs.gameoflife.model.simulation.ca.RuleSet;
import de.fjobilabs.gameoflife.model.simulation.ca.rules.StandardGameOfLifeRuleSet;
import de.fjobilabs.gameoflife.model.worlds.FixedSizeTorusWorld;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 17:02:47
 */
public class Simulator {
    
    private static final Logger logger = LoggerFactory.getLogger(Simulator.class);
    
    private SimulatorApplication simulatorApplication;
    
    private Simulation currentSimulation;
    private SimulationState currentSimulationState;
    private File currentSimulationFile;
    
    public Simulator() {
        this.simulatorApplication = new SimulatorApplication();
    }
    
    public void createSimulation(SimulationConfiguration config) {
        logger.info("Creating new simulation from config: {}", config);
        World world = new FixedSizeTorusWorld(config.getWorldWidth(), config.getWorldHeight());
        RuleSet ruleSet = new StandardGameOfLifeRuleSet();
        this.currentSimulation = new CellularAutomatonSimulation(world, ruleSet);
        this.simulatorApplication.setSimulation(this.currentSimulation);
        this.simulatorApplication.setEditMode(true);
        this.currentSimulationState = SimulationState.Created;
    }
    
    SimulatorApplication getSimulatorApplication() {
        return simulatorApplication;
    }
    
    public boolean hasSimulation() {
        return this.currentSimulation != null;
    }
    
    public boolean hasSimulationChanged() {
        /*
         * TODO Check whether simulation has really changed. Simulation has
         * changed if either the simulation has calculated a newer generation or
         * any simulation property changed.
         */
        return true;
    }
    
    public SimulationState getCurrentSimulationState() {
        return currentSimulationState;
    }
    
    public File getCurrentSimulationFile() {
        return currentSimulationFile;
    }
    
    public void startSimulation() {
        checkSimulationState(SimulationState.Created, SimulationState.Paused);
        this.currentSimulation.setRunning(true);
        this.simulatorApplication.setEditMode(false);
        this.currentSimulationState = SimulationState.Running;
    }
    
    public void pauseSimulation() {
        checkSimulationState(SimulationState.Running);
        this.currentSimulation.setRunning(false);
        this.currentSimulationState = SimulationState.Paused;
    }
    
    public void stepForward() {
        checkSimulationState(SimulationState.Created, SimulationState.Paused);
        /*
         * TODO This should be implemented in the OpenGL simulator code! It may
         * be possible that the renderer performs an update the same time this
         * method enables running. This will result in one update form the AWT
         * thread and one from the OpenGL thread. This way either two updates
         * can be performed or even worse, the application will crash because
         * the two threads access one object to the same time.
         */
        this.simulatorApplication.setEditMode(false);
        this.currentSimulation.setRunning(true);
        this.currentSimulation.update();
        this.currentSimulation.setRunning(false);
        this.currentSimulationState = SimulationState.Paused;
    }
    
    public void stepBackward() {
        /*
         * TODO Implement reverse simulation!
         */
    }
    
    public void stopSimulation() {
        if (this.currentSimulation == null) {
            throw new IllegalStateException("No existing simulation");
        }
        if (this.currentSimulationState != SimulationState.Running
                || this.currentSimulationState != SimulationState.Stopped) {
            throw new IllegalStateException("Illegal state: " + this.currentSimulationState);
        }
    }
    
    public void saveSimulation(File file) {
        logger.info("Saving simulation to file: '{}'...", file);
        // TODO Save simulation to file
    }
    
    public void openSimulation(File file) {
        logger.info("Loading simulation from file: '{}'...", file);
        // TODO Open simulation from file
    }
    
    public void closeSimulation() {
        logger.info("Closing simulation...");
        this.simulatorApplication.setSimulation(null);
        this.currentSimulation = null;
        this.currentSimulationState = null;
    }
    
    /**
     * Checks if the current simulation state is one of the given states.
     * 
     * @param states All valid simulation states.
     */
    private void checkSimulationState(SimulationState... states) {
        if (this.currentSimulation == null) {
            throw new IllegalStateException("No existing simulation");
        }
        for (SimulationState state : states) {
            if (this.currentSimulationState == state) {
                return;
            }
        }
        throw new IllegalStateException("Illegal state: " + this.currentSimulationState);
    }
}
