package de.fjobilabs.gameoflife.model.worlds;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.World;

/**
 * Represents a world with a fixed size and a border of dead cells around it.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 15:16:UNKNOWN
 */
public class FixedSizeBorderedWorld implements World {
    
    private static final CellContext BORDER_CELL_CONTEXT = new CellContext(Cell.DEAD,
            new int[] {0, 0, 0, 0, 0, 0, 0, 0});
    
    private int width;
    private int height;
    private int[][] cells;
    
    public FixedSizeBorderedWorld(int width, int height) {
        this.width = width + 2;
        this.height = height + 2;
        this.cells = new int[this.width][this.height];
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
    public CellContext getCellContext(int x, int y) {
        validateCellPosition(x, y);
        if (isBorderCell(x, y)) {
            /*
             * We can return a fake context, because the new calculated state is
             * ignored in the setCell(x, y) method anyway.
             */
            return BORDER_CELL_CONTEXT;
        }
        return new CellContext(this.cells[x][y], getNeighbourCellStates(x, y));
    }
    
    private int[] getNeighbourCellStates(int x, int y) {
        int[] neighbours = new int[CellContext.NUM_NEIGHBOUR_CELLS];
        neighbours[0] = this.cells[x - 1][y + 1];
        neighbours[1] = this.cells[x][y + 1];
        neighbours[2] = this.cells[x + 1][y + 1];
        neighbours[3] = this.cells[x + 1][y];
        neighbours[4] = this.cells[x + 1][y - 1];
        neighbours[5] = this.cells[x][y - 1];
        neighbours[6] = this.cells[x - 1][y - 1];
        neighbours[7] = this.cells[x - 1][y];
        return neighbours;
    }
    
    @Override
    public int getCellState(int x, int y) {
        validateCellPosition(x, y);
        return this.cells[x][y];
    }
    
    @Override
    public void setCellState(int x, int y, int state) {
        validateCellPosition(x, y);
        if (isBorderCell(x, y)) {
            /*
             * We can ignore the new state, because the border cell should be
             * constantly dead.
             */
            return;
        }
        Cell.validateCellState(state);
        this.cells[x][y] = state;
    }
    
    private void validateCellPosition(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            throw new IllegalArgumentException("Invalid cell position: x=" + x + ", y=" + y);
        }
    }
    
    private boolean isBorderCell(int x, int y) {
        return x == 0 || x == this.width - 1 || y == 0 || y == this.height - 1;
    }
}
