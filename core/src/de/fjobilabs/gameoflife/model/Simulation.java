package de.fjobilabs.gameoflife.model;

import java.util.Arrays;

import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 19:02:46
 */
public class Simulation {
    
    private static final Logger logger = LoggerFactory.getLogger(Simulation.class, Logger.DEBUG);
    
    private World world;
    private RuleSet ruleSet;
    private boolean running;
    private int generation;
    
    public Simulation(World world, RuleSet ruleSet) {
        this.world = world;
        this.ruleSet = ruleSet;
        this.running = true;
        this.generation = 0;
    }
    
    /**
     * Apples the current {@link RuleSet} to the {@link World}.
     */
    public void update() {
        if (!this.running) {
            return;
        }
        
        int worldWidth = this.world.getWidth();
        int worldHeight = this.world.getHeight();
        int[][] newCellStatesBuffer = new int[worldWidth][worldHeight];
        
        long startTime = System.currentTimeMillis();
        // Calculate new states
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                newCellStatesBuffer[x][y] = calcNewCellState(x, y);
            }
        }
        long calcTime = System.currentTimeMillis() - startTime;
        logger.debug("Calculated " + worldWidth * worldHeight + " cells in " + calcTime + "ms");
        
        // Updates the world
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                this.world.setCellState(x, y, newCellStatesBuffer[x][y]);
            }
        }
        
        this.generation++;
    }
    
    private int calcNewCellState(int x, int y) {
        CellContext cellContext = this.world.getCellContext(x, y);
        Rule[] rules = this.ruleSet.getRules();
        
        // Apply all rules
        int[] results = new int[rules.length];
        for (int i = 0; i < rules.length; i++) {
            results[i] = rules[i].apply(cellContext);
        }
        
        // Calculate new state
        int newState = Rule.NOT_APPLICABLE;
        for (int i = 0; i < results.length; i++) {
            int result = results[i];
            if (result != Rule.NOT_APPLICABLE && result != newState) {
                if (newState == Rule.NOT_APPLICABLE) {
                    newState = result;
                } else {
                    logger.error("Unclear rule set result for cell @ (" + x + ", " + y + ") with context: "
                            + cellContext + ". Result: " + Arrays.toString(results));
                    throw new SimulationException("Unclear rule set result");
                }
            }
        }
        if (newState == Rule.NOT_APPLICABLE) {
            // If no rule applies the cell state keeps the same
            return cellContext.getCellState();
        }
        return newState;
    }
    
    public World getWorld() {
        return world;
    }
    
    public RuleSet getRuleSet() {
        return ruleSet;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public int getGeneration() {
        return generation;
    }
}
