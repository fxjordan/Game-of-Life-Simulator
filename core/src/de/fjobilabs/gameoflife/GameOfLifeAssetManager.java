package de.fjobilabs.gameoflife;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 21:20:49
 */
public class GameOfLifeAssetManager {
    
    public static final String LOADING_IMAGE = "loading.png";
    public static final String DEAD_CELL = "dead_cell_simple_border_small_2.png";
    public static final String ALIVE_CELL = "alive_cell_simple_border_small_2.png";
    public static final String DEAD_CELL_NO_BORDER = "dead_cell_simple.png";
    public static final String ALIVE_CELL_NO_BORDER = "alive_cell_simple_2.png";
//    public static final String DEAD_CELL_NO_BORDER = "dead_cell_simple.png";
//    public static final String ALIVE_CELL_NO_BORDER = "alive_cell_simple.png";
    public static final String IDLE_IMAGE = "idle.png";
    
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
        TextureParameter textureParameter = new TextureParameter();
        textureParameter.genMipMaps = true;
        textureParameter.minFilter = TextureFilter.MipMapLinearLinear;
        textureParameter.magFilter = TextureFilter.MipMapLinearLinear;
        this.assetManager.load(DEAD_CELL, Texture.class, textureParameter);
        this.assetManager.load(ALIVE_CELL, Texture.class, textureParameter);
        this.assetManager.load(DEAD_CELL_NO_BORDER, Texture.class, textureParameter);
        this.assetManager.load(ALIVE_CELL_NO_BORDER, Texture.class, textureParameter);
        this.assetManager.load(IDLE_IMAGE, Texture.class);
    }
}
