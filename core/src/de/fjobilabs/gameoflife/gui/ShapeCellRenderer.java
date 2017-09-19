package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 18.09.2017 - 19:46:09
 */
public class ShapeCellRenderer implements CellRenderer {
    
    private static final Color DEAD_CELL_COLOR = new Color(117f / 255f, 111f / 255f, 85f / 255f, 1f);
    private static final Color ALIVE_CELL_COLOR = new Color(255f / 255f, 216f / 255f, 0f / 255f, 1f);
    
    private static final Logger logger = LoggerFactory.getLogger(ShapeCellRenderer.class, Logger.DEBUG);
    
    private ShapeRenderer shapeRenderer;
    
    private boolean borderEnabled;
    
    public ShapeCellRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }
    
    @Override
    public void setBorderEnabled(boolean enabled) {
        this.borderEnabled = enabled;
    }
    
    @Override
    public boolean isBorderEnabled() {
        return this.borderEnabled;
    }
    
    @Override
    public void begin(Camera camera) {
        this.shapeRenderer.setProjectionMatrix(camera.combined);
        this.shapeRenderer.begin(ShapeType.Filled);
//        this.shapeRenderer.setColor(Color.RED);
    }
    
    @Override
    public void drawCell(int x, int y, int state) {
        if (state == Cell.DEAD) {
            this.shapeRenderer.setColor(DEAD_CELL_COLOR);
        } else {
            // We do not check state==ALIVE again to increase performance
            this.shapeRenderer.setColor(ALIVE_CELL_COLOR);
        }
        this.shapeRenderer.rect(x, y, 1, 1);
    }
    
    @Override
    public void end() {
        this.shapeRenderer.end();
    }
    
    @Override
    public void dispose() {
        this.shapeRenderer.dispose();
    }
}
