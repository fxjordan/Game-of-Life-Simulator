package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 18:10:21
 */
public interface World {
    
    public int getWidth();
    
    public int getHeight();
    
    public CellContext getCellContext(int x, int y);
    
    public int getCellState(int x, int y);
    
    public void setCellState(int x, int y, int state);
}
