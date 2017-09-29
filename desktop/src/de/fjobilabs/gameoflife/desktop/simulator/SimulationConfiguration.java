package de.fjobilabs.gameoflife.desktop.simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 17:06:11
 */
public class SimulationConfiguration {
    
    public static final String DEFAULT_WORLD_TYPE = "fixed-size-torus-world";
    public static final int DEFAULT_WORLD_WIDTH = 100;
    public static final int DEFAULT_WORLD_HEIGHT = 100;
    public static final int DEFAULT_UPS = 10;
    
    private String worldType;
    private int worldWidth;
    private int worldHeight;
    private int ups;
    // TODO World type property
    // TODO Simulation type property
    // TODO Simulation properties (rule set)
    
    public SimulationConfiguration() {
        this.worldType = DEFAULT_WORLD_TYPE;
        this.worldWidth = DEFAULT_WORLD_WIDTH;
        this.worldHeight = DEFAULT_WORLD_HEIGHT;
        this.ups = DEFAULT_UPS;
    }
    
    public SimulationConfiguration(int worldWidth, int worldHeight) {
        super();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }
    
    public String getWorldType() {
        return worldType;
    }
    
    public void setWorldType(String worldType) {
        this.worldType = worldType;
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
    
    public int getUps() {
        return ups;
    }
    
    public void setUps(int ups) {
        this.ups = ups;
    }
}
