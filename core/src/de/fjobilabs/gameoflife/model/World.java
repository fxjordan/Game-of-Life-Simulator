package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 18:10:21
 */
public interface World {
    
    public int getWidth();
    
    public int getHeight();
    
    public int getCenterX();
    public int getCenterY();
    
    public CellContext getCellContext(int x, int y);
    
    /**
     * Gives the cell context back to the world, so that the object can be pooled.
     * 
     * @param cellContext
     */
    public void freeCellContext(CellContext cellContext);
    
    public int getCellState(int x, int y);
    
    public void setCellState(int x, int y, int state);
    
    public boolean isCellPositionValid(int x, int y);
}
