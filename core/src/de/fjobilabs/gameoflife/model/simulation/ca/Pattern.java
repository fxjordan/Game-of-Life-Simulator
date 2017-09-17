package de.fjobilabs.gameoflife.model.simulation.ca;

import de.fjobilabs.gameoflife.model.World;

/**
 * A pattern describes a configuration of cells over a finite width and
 * height.<br>
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 22:48:25
 */
public interface Pattern {
    
    public int getWidth();
    
    public int getHeight();
    
    /**
     * Applies the pattern to a world at a specific position. The pattern starts
     * at the position with its top-left corner.
     * 
     * @param world The world to which the pattern is to be applied.
     * @param x
     * @param y
     */
    public void apply(World world, int x, int y);
    
    @Deprecated
    public class ActiveCell {
        
        private final int x;
        private final int y;
        
        public ActiveCell(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
    }
}
