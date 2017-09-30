package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.graphics.Camera;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 15:10:20
 */
public interface CellRenderer {
    
    public void setBorderEnabled(boolean enabled);
    
    public boolean isBorderEnabled();
    
    /**
     * Configures the renderer for a specific world size.
     * 
     * @param worldWidth The world width in cells.
     * @param worldHeight the world height in cells.
     */
    public default void configure(int worldWidth, int worldHeight) {
    }
    
    public void begin(Camera camera);
    
    public void drawCell(int x, int y, int state);
    
    /**
     * Draws an overlay cell over the actual cell of the world. Can be used to
     * draw a pattern that is moved in the world.
     * 
     * @param x
     * @param y
     * @param state
     */
    public default void drawOverlayCell(int x, int y, int state) {
    }
    
    public void end();
    
    /**
     * Should reset all OpenGL states so that another renderer can start with a
     * clean state.
     */
    public default void hide() {
    };
    
    public void dispose();
}
