package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.World;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 00:28:32
 */
public class WorldRenderer {
    
    private World world;
    private OrthographicCamera camera;
    private FillViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture deadCellTexture;
    private Texture aliveCellTexture;
    
    public WorldRenderer(GameOfLifeAssetManager assetManager, World world) {
        this.world = world;
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(16, 9, this.camera);
        this.spriteBatch = new SpriteBatch();
        this.deadCellTexture = assetManager.getAsset(GameOfLifeAssetManager.DEAD_CELL);
        this.aliveCellTexture = assetManager.getAsset(GameOfLifeAssetManager.ALIVE_CELL);
    }
    
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }
    
    public void render() {
        this.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        this.spriteBatch.setProjectionMatrix(this.camera.combined);
        this.spriteBatch.begin();
        
        int worldWidth = this.world.getWidth();
        int worldHeight = this.world.getHeight();
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                drawCell(x, y);
            }
        }
        
        this.spriteBatch.end();
    }
    
    public void setCameraX(float x) {
        this.camera.position.x = x;
    }
    
    public float getCameraX() {
        return this.camera.position.x;
    }
    
    public void setCameraY(float y) {
        this.camera.position.y = y;
    }
    
    public float getCameraY() {
        return this.camera.position.y;
    }
    
    public void setZoom(float zoom) {
        this.camera.zoom = zoom;
    }
    
    public float getZoom() {
        return this.camera.zoom;
    }
    
    private void drawCell(int x, int y) {
        int cellState = this.world.getCellState(x, y);
        Texture texture;
        if (cellState == Cell.DEAD) {
            texture = this.deadCellTexture;
        } else if (cellState == Cell.ALIVE) {
            texture = this.aliveCellTexture;
        } else {
            throw new RuntimeException("Invalid cell state: " + cellState);
        }
        this.spriteBatch.draw(texture, x, y, 1, 1);
    }
}
