package de.fjobilabs.gameoflife.desktop.simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 17:06:11
 */
public class SimulationConfiguration {
    
    public static final int DEFAULT_WORLD_WIDTH = 100;
    public static final int DEFAULT_WORLD_HEIGHT = 100;
    
    private int worldWidth;
    private int worldHeight;
    // TODO World type property
    // TODO Simulation type property
    // TODO Simulation properties (rule set)
    
    public SimulationConfiguration() {
        this.worldWidth = DEFAULT_WORLD_WIDTH;
        this.worldHeight = DEFAULT_WORLD_HEIGHT;
    }
    
    public SimulationConfiguration(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }
    
    public int getWorldWidth() {
        return worldWidth;
    }
    
    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }
    
    public int getWorldHeight() {
        return worldHeight;
    }
    
    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }
}