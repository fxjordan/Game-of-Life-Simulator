package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 00:28:32
 */
public class WorldRenderer {
    
    private static final float MIN_ZOOM = 0.5f;
    private static final float DISABLE_BORDER_ZOOM = 5f;
    
    private static final Logger logger = LoggerFactory.getLogger(WorldRenderer.class, Logger.DEBUG);
    
    private World world;
    private OrthographicCamera camera;
    private FillViewport viewport;
    private CellRenderer cellRenderer;
    private boolean enabled;
    
    public WorldRenderer(GameOfLifeAssetManager assetManager, World world) {
        this.world = world;
        this.camera = new OrthographicCamera();
        this.camera.position.x = this.world.getCenterX();
        this.camera.position.y = this.world.getCenterY();
        this.viewport = new FillViewport(50, 50, this.camera);
        // this.cellRenderer = new TextureCellRenderer(assetManager);
        this.cellRenderer = new ShapeCellRenderer();
        this.enabled = true;
    }
    
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }
    
    public void render() {
        if (!this.enabled) {
            return;
        }
        
        this.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        this.cellRenderer.begin(this.camera);
        
        int worldWidth = this.world.getWidth();
        int worldHeight = this.world.getHeight();
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                this.cellRenderer.drawCell(x, y, this.world.getCellState(x, y));
            }
        }
        
        this.cellRenderer.end();
    }
    
    public Vector2 touchToWorld(Vector2 screenCoords) {
        return this.viewport.unproject(screenCoords);
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
        if (zoom < MIN_ZOOM) {
            zoom = MIN_ZOOM;
        }
        logger.debug("zoom: " + zoom);
        this.camera.zoom = zoom;
        if (zoom >= DISABLE_BORDER_ZOOM && this.cellRenderer.isBorderEnabled()) {
            this.cellRenderer.setBorderEnabled(false);
        } else if (zoom <= DISABLE_BORDER_ZOOM && !this.cellRenderer.isBorderEnabled()) {
            this.cellRenderer.setBorderEnabled(true);
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        logger.info("Rendering " + (this.enabled ? "enabled" : "disabled"));
    }
    
    public float getZoom() {
        return this.camera.zoom;
    }
    
    public void dispose() {
        logger.debug("Disposing World Renderer");
        this.cellRenderer.dispose();
    }
}
