package de.fjobilabs.gameoflife.model.worlds;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.World;

/**
 * Represents a torus world with a fixed size. In fact this means that each edge
 * of the world corresponds to its opposite.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 16:48:58
 */
public class FixedSizeTorusWorld implements World {
    
    private int width;
    private int height;
    private int[][] cells;
    
    public FixedSizeTorusWorld(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new int[width][height];
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
        return this.width / 2;
    }
    
    @Override
    public int getCenterY() {
        return this.height / 2;
    }
    
    @Override
    public CellContext getCellContext(int x, int y) {
        validateCellPosition(x, y);
        return new CellContext(this.cells[x][y], getNeighbourCellStates(x, y));
    }
    
    @Override
    public int getCellState(int x, int y) {
        validateCellPosition(x, y);
        return this.cells[x][y];
    }
    
    @Override
    public void setCellState(int x, int y, int state) {
        validateCellPosition(x, y);
        Cell.validateCellState(state);
        this.cells[x][y] = state;
    }
    
    private int[] getNeighbourCellStates(int x, int y) {
        int[] neighbours = new int[CellContext.NUM_NEIGHBOUR_CELLS];
        int leftX;
        int rightX;
        int bottomY;
        int topY;
        leftX = x == 0 ? this.width - 1 : x - 1;
        rightX = x == this.width - 1 ? 0 : x + 1;
        bottomY = y == 0 ? this.height - 1 : y - 1;
        topY = y == this.height - 1 ? 0 : y + 1;
        
        neighbours[0] = this.cells[leftX][topY];
        neighbours[1] = this.cells[x][topY];
        neighbours[2] = this.cells[rightX][topY];
        neighbours[3] = this.cells[rightX][y];
        neighbours[4] = this.cells[rightX][bottomY];
        neighbours[5] = this.cells[x][bottomY];
        neighbours[6] = this.cells[leftX][bottomY];
        neighbours[7] = this.cells[leftX][y];
        return neighbours;
    }
    
    private void validateCellPosition(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            throw new IllegalArgumentException("Invalid cell position: x=" + x + ", y=" + y);
        }
    }
}
