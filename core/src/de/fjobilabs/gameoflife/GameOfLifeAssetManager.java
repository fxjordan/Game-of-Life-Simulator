package de.fjobilabs.gameoflife;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 21:20:49
 */
public class GameOfLifeAssetManager {
    
    public static final String LOADING_IMAGE = "loading.png";
    public static final String DEAD_CELL = "dead_cell_2.png";
    public static final String ALIVE_CELL = "alive_cell_1.png";
    
    private final AssetManager assetManager;
    
    public GameOfLifeAssetManager() {
        this.assetManager = new AssetManager();
    }
    
    public void load() {
        loadImages();
    }
    
    public boolean update(float delta) {
        return this.assetManager.update();
    }
    
    public <T> T getAsset(String name) {
        return this.assetManager.get(name);
    }
    
    public <T> T instantLoadAsset(String name) {
        this.assetManager.finishLoadingAsset(name);
        return this.assetManager.get(name);
    }
    
    public void dispose() {
        this.assetManager.dispose();
    }
    
    private void loadImages() {
        this.assetManager.load(LOADING_IMAGE, Texture.class);
        this.assetManager.load(DEAD_CELL, Texture.class);
        this.assetManager.load(ALIVE_CELL, Texture.class);
    }
}
