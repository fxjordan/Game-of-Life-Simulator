package de.fjobilabs.gameoflife.desktop.simulator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fjobilabs.gameoflife.SimulatorApplication;
import de.fjobilabs.gameoflife.desktop.simulator.io.SimulationModel;
import de.fjobilabs.gameoflife.desktop.simulator.io.SimulationWriter;
import de.fjobilabs.gameoflife.desktop.simulator.io.WorldContentModel;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.CellularAutomatonSimulation;
import de.fjobilabs.gameoflife.model.simulation.ca.RuleSet;
import de.fjobilabs.gameoflife.model.simulation.ca.rules.StandardGameOfLifeRuleSet;
import de.fjobilabs.gameoflife.model.worlds.FixedSizeBorderedWorld;
import de.fjobilabs.gameoflife.model.worlds.FixedSizeTorusWorld;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 17:02:47
 */
public class Simulator {
    
    private static final Logger logger = LoggerFactory.getLogger(Simulator.class);
    
    public static final int DEFAULT_UPS = 10;
    
    private SimulatorApplication simulatorApplication;
    
    private Simulation currentSimulation;
    private SimulationState currentSimulationState;
    private File currentSimulationFile;
    
    private SimulationModel currentSimulationModel;
    
    private ObjectMapper objectMapper;
    private WorldContentLoader worldContentLoader;
    private SimulationWriter simulationWriter;
    
    public Simulator() {
        this.simulatorApplication = new SimulatorApplication();
        this.objectMapper = new ObjectMapper();
        this.worldContentLoader = new WorldContentLoader();
        this.simulationWriter = new SimulationWriter();
    }
    
    /**
     * Loads a new simulation from a {@link SimulationModel}. This model holds
     * all necessary information about the simulation, the configuration and the
     * world.
     * 
     * @param simulationModel
     * @throws SimulatorException
     */
    /*
     * TODO This method should be transactional.
     */
    private void loadSimulation(SimulationModel simulationModel) throws SimulatorException {
        this.currentSimulationModel = simulationModel;
        SimulationConfiguration config = simulationModel.getConfig();
        this.currentSimulation = createSimulation(config, simulationModel.getGeneration());
        this.simulatorApplication.setSimulation(this.currentSimulation);
        configure(simulationModel);
        WorldContentModel worldContent = simulationModel.getWorldContent();
        if (worldContent != null) {
            try {
                this.worldContentLoader.loadContent(this.currentSimulation.getWorld(), worldContent);
            } catch (WorldContentLoaderException e) {
                throw new SimulatorException("Failed to load simulation", e);
            }
        }
    }
    
    private Simulation createSimulation(SimulationConfiguration config, int generation) {
        // TODO Support variable simulations and rule sets.
        World world = createWorld(config);
        RuleSet ruleSet = new StandardGameOfLifeRuleSet();
        return new CellularAutomatonSimulation(world, ruleSet, generation);
    }
    
    private World createWorld(SimulationConfiguration config) {
        // TODO Support external world types.
        String worldType = config.getWorldType();
        switch (worldType) {
        case "fixed-size-torus-world":
            return new FixedSizeTorusWorld(config.getWorldWidth(), config.getWorldHeight());
        case "fixed-size-bordered-world":
            return new FixedSizeBorderedWorld(config.getWorldWidth(), config.getWorldHeight());
        default:
            throw new IllegalArgumentException("Unsupported world type: " + config);
        }
    }
    
    private void configure(SimulationModel simulationModel) {
        if (simulationModel.getGeneration() > 0) {
            this.simulatorApplication.setEditMode(false);
            this.currentSimulationState = SimulationState.Paused;
        } else {
            this.simulatorApplication.setEditMode(true);
            this.currentSimulationState = SimulationState.Created;
        }
        applyConfiguration(simulationModel.getConfig());
    }
    
    private void applyConfiguration(SimulationConfiguration config) {
        /*
         * TODO UPS are not correctly changed in GUI after simulation was
         * loaded.
         */
        setUPS(config.getUps());
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
    
    public void setUPS(int ups) {
        if (ups < 0) {
            throw new IllegalArgumentException("UPS must be >= 0");
        }
        this.currentSimulationModel.getConfig().setUps(ups);
        this.simulatorApplication.setUps(ups);
    }
    
    public int getUPS() {
        return currentSimulationModel.getConfig().getUps();
    }
    
    public int getMeasuredUPS() {
        return this.simulatorApplication.getMeasuredUPS();
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
    
    public void newSimulation(SimulationConfiguration config) throws SimulatorException {
        logger.info("Creating new simulation from config: {}", config);
        SimulationModel model = new SimulationModel();
        model.setConfig(config);
        model.setGeneration(0);
        loadSimulation(model);
        this.currentSimulationFile = null;
        logger.info("Simulation created successfully");
    }
    
    /**
     * Loads a simulation from the given file.
     * 
     * @param file The file where the simulation should be loaded from.
     * 
     * @throws SimulatorException When the simulator cannot create a simulation
     *             from the loaded model.
     * @throws IOException When the simulation model cannot be loaded from file.
     */
    public void openSimulation(File file) throws SimulatorException, IOException {
        logger.info("Loading simulation from file: '{}'...", file);
        SimulationModel simulationModel = this.objectMapper.readValue(file, SimulationModel.class);
        loadSimulation(simulationModel);
        this.currentSimulationFile = file;
    }
    
    public void closeSimulation() {
        logger.info("Closing simulation...");
        this.simulatorApplication.setSimulation(null);
        this.currentSimulation = null;
        this.currentSimulationState = null;
        this.currentSimulationFile = null;
    }
    
    public void saveSimulation(File file) throws IOException {
        logger.info("Saving simulation to file: '{}'...", file);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            this.simulationWriter.writeSimulation(out, this.currentSimulationModel.getConfig(),
                    this.currentSimulation);
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.warn("Failed to close output stream after saving to file: " + file, e);
                }
            }
        }
        logger.info("Simulation saved successfully to file '{}'", file);
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
    
    public int getCurrentGeneration() {
        if (this.currentSimulation == null) {
            return 0;
        }
        return this.currentSimulation.getGeneration();
    }
}
