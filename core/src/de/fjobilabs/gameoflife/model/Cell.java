package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 15:29:28
 */
public class Cell {
    
    public static final int DEAD = 0;
    public static final int ALIVE = 1;
    
    /**
     * Validates a cell state.<br>
     * A cell state is only valid if it has the value {@link Cell#DEAD} or
     * {@link Cell#ALIVE}.
     * 
     * @param cellState The cell state to validate.
     */
    public static void validateCellState(int cellState) {
        if (cellState != Cell.DEAD && cellState != Cell.ALIVE) {
            throw new IllegalArgumentException("Invalid cell state: " + cellState);
        }
    }
}
