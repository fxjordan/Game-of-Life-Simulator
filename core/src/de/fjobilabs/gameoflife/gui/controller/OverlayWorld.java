package de.fjobilabs.gameoflife.gui.controller;

import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.World;

/**
 * Very simple world implementation that can be used to store the state of the
 * overlay.<br>
 * This world can not be used with a simulation!
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 01:49:00
 */
public class OverlayWorld implements World {
    
    public static final int IGNORE_CELL = -1;
    
    private int width;
    private int height;
    private int[][] cells;
    
    public OverlayWorld(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new int[width][height];
        clear();
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getCenterX() {
        return width / 2;
    }
    
    @Override
    public int getCenterY() {
        return height / 2;
    }
    
    @Override
    public CellContext getCellContext(int x, int y) {
        /*
         * This world should not be used with a simulation. It's only used to
         * render the current overly of a world editor.
         */
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void freeCellContext(CellContext cellContext) {
        /*
         * This world should not be used with a simulation. It's only used to
         * render the current overly of a world editor.
         */
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getCellState(int x, int y) {
        return this.cells[x][y];
    }
    
    @Override
    public void setCellState(int x, int y, int state) {
        this.cells[x][y] = state;
    }
    
    @Override
    public boolean isCellPositionValid(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
    
    @Override
    public void clear() {
        for (int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
                this.cells[x][y] = IGNORE_CELL;
            }
        }
    }
}
