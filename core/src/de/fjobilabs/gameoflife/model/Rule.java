package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 16:42:10
 */
public interface Rule {
    
    public static final int NOT_APPLICABLE = -1;
    
    /**
     * Applies this rule a cell. This method calculates the new state of a cell
     * depending on its neighbour cells.
     * 
     * @param context All information about the cell and its environment.
     * @return The new state of the cell, or {@link Rule#NOT_APPLICABLE} if the
     *         rule does not apply to this cell.
     */
    public int apply(CellContext context);
}
