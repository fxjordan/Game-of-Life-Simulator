package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 01:43:12
 */
public class GameController extends InputAdapter {
    
    private WorldRenderer worldRenderer;
    
    public GameController(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }
    
    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.worldRenderer.setCameraX(this.worldRenderer.getCameraX() - 8f * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.worldRenderer.setCameraX(this.worldRenderer.getCameraX() + 8f * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            this.worldRenderer.setCameraY(this.worldRenderer.getCameraY() - 8f * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.UP)) {
            this.worldRenderer.setCameraY(this.worldRenderer.getCameraY() + 8f * delta);
        }
    }
    
    @Override
    public boolean scrolled(int amount) {
        this.worldRenderer.setZoom(this.worldRenderer.getZoom() + amount * 0.1f);
        return true;
    }
}
