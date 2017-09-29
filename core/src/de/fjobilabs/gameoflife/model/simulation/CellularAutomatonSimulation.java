package de.fjobilabs.gameoflife.model.simulation;

import java.util.Arrays;

import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.model.AbstractSimulation;
import de.fjobilabs.gameoflife.model.Cell;
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
    
    /** The rule set of this simulation. */
    private final RuleSet ruleSet;
    
    /** Buffer for the next generation cell states. */
    private int[][] newCellStatesBuffer;
    
    /** Buffer for each rules result. */
    private int[] ruleResultsBuffer;
    
    /**
     * Creates a new {@code CellularAutomatonSimulation} for a given world with
     * a specific rule set.
     * 
     * @param world The world on which the simulation should operate.
     * @param ruleSet The rule set for this simulation.
     * @param generation The current generation number.
     */
    public CellularAutomatonSimulation(World world, RuleSet ruleSet) {
        this(world, ruleSet, 0);
    }
    
    /**
     * Creates a new {@code CellularAutomatonSimulation} for a given world with
     * a specific rule set.
     * 
     * @param world The world on which the simulation should operate.
     * @param ruleSet The rule set for this simulation.
     * @param generation The current generation number.
     */
    public CellularAutomatonSimulation(World world, RuleSet ruleSet, int generation) {
        super(world, generation);
        this.ruleSet = ruleSet;
        /*
         * TODO Implement as own class, so that buffer can grow (Necessary for
         * dynamic size worlds)
         */
        this.newCellStatesBuffer = new int[world.getWidth()][world.getHeight()];
        this.ruleResultsBuffer = new int[ruleSet.getRules().length];
    }
    
    /**
     * Applies the {@link RuleSet} of the simulation to each cell in the world.
     */
    @Override
    protected void doUpdate() {
        int worldWidth = this.world.getWidth();
        int worldHeight = this.world.getHeight();
        
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
    
    /**
     * Calculates the new cell state of the cell at the given position.
     * 
     * @param x The cell position on the x-axis.
     * @param y The cell position on the y-axis.
     * @return The cell state for the next generation (either {@link Cell#ALIVE}
     *         or {@link Cell#DEAD}).
     */
    private int calcNewCellState(int x, int y) {
        CellContext cellContext = this.world.getCellContext(x, y);
        Rule[] rules = this.ruleSet.getRules();
        
        // Apply all rules
        int[] results = this.ruleResultsBuffer;
        for (int i = 0; i < rules.length; i++) {
            results[i] = rules[i].apply(cellContext);
        }
        
        // Free the object to allow pooling
        this.world.freeCellContext(cellContext);
        
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
