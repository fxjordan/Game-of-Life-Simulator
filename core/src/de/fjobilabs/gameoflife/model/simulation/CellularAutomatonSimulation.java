package de.fjobilabs.gameoflife.model.simulation;

import java.util.Arrays;

import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.model.AbstractSimulation;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.SimulationException;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.ca.Rule;
import de.fjobilabs.gameoflife.model.simulation.ca.RuleSet;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * The {@code CellularAutomatonSimulation} can be used to simulate any
 * <i>cellular automaton</i> (e.g. Conway's Game of Life).<br>
 * The simulation takes a {@link World} and a specific {@link RuleSet}. This
 * rule set is applied once per update to any cell in the world to calculate
 * their new state. After the calculation the state of each cells in the world
 * is set to the new calculated state.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 19:02:46
 */
public class CellularAutomatonSimulation extends AbstractSimulation {
    
    private static final Logger logger = LoggerFactory.getLogger(CellularAutomatonSimulation.class,
            Logger.DEBUG);
    
    private final RuleSet ruleSet;
    
    public CellularAutomatonSimulation(World world, RuleSet ruleSet) {
        super(world);
        this.ruleSet = ruleSet;
    }
    
    /**
     * Apples the rule set of the simulation to each cell in the world.
     */
    @Override
    protected void doUpdate() {
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
}
