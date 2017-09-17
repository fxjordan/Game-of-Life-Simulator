package de.fjobilabs.gameoflife.model.simulation.ca;

import de.fjobilabs.gameoflife.model.World;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 23:35:50
 */
public class RLEPattern implements Pattern {
    
    private int width;
    private int height;
    private String name;
    private String author;
    private String[] comments;
    
    public RLEPattern(int width, int height) {
        this.width = width;
        this.height = height;
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
        // TODO Auto-generated method stub
        
    }
}
