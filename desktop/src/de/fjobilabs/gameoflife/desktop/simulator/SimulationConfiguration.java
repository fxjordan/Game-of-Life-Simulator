package de.fjobilabs.gameoflife.desktop.simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 26.09.2017 - 17:06:11
 */
public class SimulationConfiguration {
    
    public static final String TORUS_WORLD = "torus-world";
    public static final String BORDERED_WORLD = "bordered-world";
    public static final String CELLULAR_AUTOMATON_SIMULATION = "cellular-automaton-simulation";
    public static final String LANGTONS_ANT_SIMULATION = "langtons-ant-simulation";
    public static final String STANDARD_GAME_OF_LIFE_RULE_SET = "standard-game-of-life-rule-set";
    public static final String LIFE_LIKE_RULE_SET = "life-like-rule-set";
    
    public static final String DEFAULT_WORLD_TYPE = TORUS_WORLD;
    public static final int DEFAULT_WORLD_WIDTH = 100;
    public static final int DEFAULT_WORLD_HEIGHT = 100;
    public static final int DEFAULT_UPS = 10;
    public static final String DEFAULT_SIMULATION_TYPE = CELLULAR_AUTOMATON_SIMULATION;
    public static final String DEFAULT_RULE_SET = STANDARD_GAME_OF_LIFE_RULE_SET;
    public static final String DEFAULT_RULE_STRING = "23/3"; // TODO Change format to B3/S23
    
    private String worldType;
    private int worldWidth;
    private int worldHeight;
    // TODO Remove UPS from configuration
    private int ups;
    private String simulationType;
    private String ruleSet;
    private String ruleString;
    
    public SimulationConfiguration() {
        this.worldType = DEFAULT_WORLD_TYPE;
        this.worldWidth = DEFAULT_WORLD_WIDTH;
        this.worldHeight = DEFAULT_WORLD_HEIGHT;
        this.ups = DEFAULT_UPS;
        this.simulationType = DEFAULT_SIMULATION_TYPE;
        this.ruleSet = DEFAULT_RULE_SET;
        this.ruleString = DEFAULT_RULE_STRING;
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
    
    public String getSimulationType() {
        return simulationType;
    }
    
    public void setSimulationType(String simulationType) {
        this.simulationType = simulationType;
    }
    
    public String getRuleSet() {
        return ruleSet;
    }
    
    public void setRuleSet(String ruleSet) {
        this.ruleSet = ruleSet;
    }
    
    public String getRuleString() {
        return ruleString;
    }
    
    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }
}
