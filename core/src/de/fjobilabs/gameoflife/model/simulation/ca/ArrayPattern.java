package de.fjobilabs.gameoflife.model.simulation.ca;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.World;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 23:20:23
 */
public class ArrayPattern implements Pattern {
    
    private final int width;
    private final int height;
    private final int[][] cells;
    
    public ArrayPattern(int width, int height, int[][] pattern) {
        this.width = width;
        this.height = height;
        this.cells = new int[width][height];
        if (pattern.length != height) {
            throw new IllegalArgumentException(
                    "Invalid pattern (columns=" + pattern.length + ", width=" + width + ")");
        }
        for (int x = 0; x != width; x++) {
            int[] column = pattern[x];
            if (column.length < height) {
                throw new IllegalArgumentException(
                        "Invalid pattern (colummLength=" + column.length + ", height=" + height + ")");
            }
            for (int y = 0; y < height; y++) {
                int state = pattern[x][y];
                Cell.validateCellState(state);
                this.cells[x][y] = pattern[x][y];
            }
        }
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void apply(World world, int x, int y) {
        for (int localX=0; localX<this.width; localX++) {
            for (int localY=0; localY<this.height; localY++) {
                world.setCellState(x + localX, y - localY, this.cells[localX][localY]);
            }
        }
    }
}
