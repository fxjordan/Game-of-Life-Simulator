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
    
    public void begin(Camera camera);
    
    public void drawCell(int x, int y, int state);
    
    public void end();
    
    public void dispose();
}
