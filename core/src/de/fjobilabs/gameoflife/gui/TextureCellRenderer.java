package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 15:11:23
 */
public class TextureCellRenderer implements CellRenderer {
    
    private static final Logger logger = LoggerFactory.getLogger(TextureCellRenderer.class, Logger.DEBUG);
    
    private final SpriteBatch spriteBatch;
    private final Texture deadCellTexture;
    private final Texture aliveCellTexture;
    private final Texture deadCellNoBorderTexture;
    private final Texture aliveCellNoBorderTexture;
    
    private boolean borderEnabled;
    private Texture currentDeadCellTexture;
    private Texture currentAliveCellTexture;
    
    public TextureCellRenderer(GameOfLifeAssetManager assetManager) {
        this.spriteBatch = new SpriteBatch();
        this.deadCellTexture = assetManager.getAsset(GameOfLifeAssetManager.DEAD_CELL);
        this.aliveCellTexture = assetManager.getAsset(GameOfLifeAssetManager.ALIVE_CELL);
        this.deadCellNoBorderTexture = assetManager.getAsset(GameOfLifeAssetManager.DEAD_CELL_NO_BORDER);
        this.aliveCellNoBorderTexture = assetManager.getAsset(GameOfLifeAssetManager.ALIVE_CELL_NO_BORDER);
        this.currentDeadCellTexture = this.deadCellTexture;
        this.currentAliveCellTexture = this.aliveCellTexture;
    }
    
    @Override
    public void setBorderEnabled(boolean enabled) {
        if (this.borderEnabled != enabled) {
            this.borderEnabled = enabled;
            if (enabled) {
                this.currentDeadCellTexture = this.deadCellTexture;
                this.currentAliveCellTexture = this.aliveCellTexture;
            } else {
                this.currentDeadCellTexture = this.deadCellNoBorderTexture;
                this.currentAliveCellTexture = this.aliveCellNoBorderTexture;
            }
        }
    }
    
    @Override
    public boolean isBorderEnabled() {
        return this.borderEnabled;
    }
    
    @Override
    public void begin(Camera camera) {
        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.spriteBatch.begin();
    }
    
    @Override
    public void drawCell(int x, int y, int state) {
        Texture texture;
        if (state == Cell.DEAD) {
            texture = this.currentDeadCellTexture;
        } else if (state == Cell.ALIVE) {
            texture = this.currentAliveCellTexture;
        } else {
            throw new RuntimeException("Invalid cell state: " + state);
        }
        this.spriteBatch.draw(texture, x, y, 1, 1);
    }
    
    @Override
    public void end() {
        this.spriteBatch.end();
    }
    
    @Override
    public void dispose() {
        logger.debug("Disposing TextureCellRenderer");
        this.spriteBatch.dispose();
    }
}
