package de.fjobilabs.gameoflife.model;

import java.util.Arrays;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Represents the context of a single cell, holding the states of all 8
 * neighbour cells.<br>
 * The cell states are ordered clockwise form the top-left cell to the left
 * cell.<br>
 * <br>
 * <i>X</i> represents the cell for that the context holds information.
 * <table>
 * <tr>
 * <td>0</td>
 * <td>1</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td><i>X</i></td>
 * <td>3</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>5</td>
 * <td>4</td>
 * </tr>
 * </table>
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 15:33:17
 */
public class CellContext implements Poolable {
    
    public static final int NUM_NEIGHBOUR_CELLS = 8;
    
    private int cellState;
    private int[] neighbourCellStates;
    private int aliveCells;
    
    public CellContext(int cellState, int[] neighbourCellStates) {
        Cell.validateCellState(cellState);
        this.cellState = cellState;
        if (neighbourCellStates == null) {
            throw new NullPointerException("Neighbour cells states must not be null");
        }
        if (neighbourCellStates.length != NUM_NEIGHBOUR_CELLS) {
            throw new IllegalArgumentException(
                    "Number of neighbour cell states must be " + NUM_NEIGHBOUR_CELLS);
        }
        // We do the validation of the cell states in the next calculation
        this.neighbourCellStates = neighbourCellStates;
        this.aliveCells = calcAliveCells(neighbourCellStates);
    }
    
    CellContext() {
        this.neighbourCellStates = new int[NUM_NEIGHBOUR_CELLS];
    }
    
    /**
     * Returns the state of the cell for which this context holds information.
     * 
     * @return The cell state.
     */
    public int getCellState() {
        return cellState;
    }
    
    public void setCellState(int cellState) {
        this.cellState = cellState;
    }
    
    /**
     * Returns the states of all 8 neighbour states, clockwise from the top-left
     * to the left.
     * 
     * @return The states of all neighbour cells.
     */
    public int[] getNeighbourCellStates() {
        return neighbourCellStates;
    }
    
    public void setNeighbourCellStates(int[] neighbourCellStates) {
        // We do the validation of the cell states in the next calculation
        this.neighbourCellStates = neighbourCellStates;
        this.aliveCells = calcAliveCells(neighbourCellStates);
        
    }
    
    /**
     * Returns the number of dead neighbour cells.
     * 
     * @return The number of dead neighbour cells.
     */
    public int getDeadNeighbourCells() {
        return NUM_NEIGHBOUR_CELLS - aliveCells;
    }
    
    /**
     * Returns the number of alive neighbour cells.
     * 
     * @return The number of alive neighbour cells.
     */
    public int getAliveNeighbourCells() {
        return aliveCells;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("CellContext(cellState=");
        builder.append(this.cellState);
        builder.append(", neighbourCellStates=");
        builder.append(Arrays.toString(this.neighbourCellStates));
        builder.append(")");
        return builder.toString();
    }
    
    /**
     * Calculates the number of alive cells.<br>
     * This method also validates all cell states.
     * 
     * @param cellsStates The cell states to check
     * @return The number of alive cells.
     */
    private int calcAliveCells(int[] cellStates) {
        int alive = 0;
        for (int i = 0; i < NUM_NEIGHBOUR_CELLS; i++) {
            int state = cellStates[i];
            Cell.validateCellState(state);
            alive += state;
        }
        return alive;
    }
    
    @Override
    public void reset() {
        this.aliveCells = 0;
        this.cellState = 0;
    }
}
