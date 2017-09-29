package de.fjobilabs.gameoflife.desktop.simulator.io;

import de.fjobilabs.gameoflife.desktop.simulator.SimulationConfiguration;

/**
 * Represents the model for a simulation, that can be loaded from or saved to a
 * file.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 28.09.2017 - 19:33:09
 */
public class SimulationModel {
    
    /** Configuration of the simulation */
    private SimulationConfiguration config;
    
    /** Start generation of the simulation. */
    private WorldContentModel startGeneration;
    
    /** Content of the simulations world. */
    private WorldContentModel worldContent;
    
    /** Current generation of the world. */
    private int generation;
    
    public SimulationConfiguration getConfig() {
        return config;
    }
    
    public void setConfig(SimulationConfiguration config) {
        this.config = config;
    }
    
    public WorldContentModel getStartGeneration() {
        return startGeneration;
    }
    
    public void setStartGeneration(WorldContentModel startGeneration) {
        this.startGeneration = startGeneration;
    }
    
    public WorldContentModel getWorldContent() {
        return worldContent;
    }
    
    public void setWorldContent(WorldContentModel worldContent) {
        this.worldContent = worldContent;
    }
    
    public int getGeneration() {
        return generation;
    }
    
    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
