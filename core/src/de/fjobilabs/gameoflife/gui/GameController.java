package de.fjobilabs.gameoflife.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 01:43:12
 */
public class GameController extends InputAdapter {
    
    private static final float ZOOM_SPEED = 0.5f;
    private static final float MOVE_SPEED = 10.0f;
    
    private Simulation simulation;
    private WorldRenderer worldRenderer;
    private Vector2 touchPoint;
    
    public GameController(Simulation simulation, WorldRenderer worldRenderer) {
        this.simulation = simulation;
        this.worldRenderer = worldRenderer;
        this.touchPoint = new Vector2();
    }
    
    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.worldRenderer.setCameraX(this.worldRenderer.getCameraX() - MOVE_SPEED * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.worldRenderer.setCameraX(this.worldRenderer.getCameraX() + MOVE_SPEED * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            this.worldRenderer.setCameraY(this.worldRenderer.getCameraY() - MOVE_SPEED * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.UP)) {
            this.worldRenderer.setCameraY(this.worldRenderer.getCameraY() + MOVE_SPEED * delta);
        }
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            setCellState(screenX, screenY, Cell.ALIVE);
        }
        if (button == Buttons.RIGHT) {
            setCellState(screenX, screenY, Cell.DEAD);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
    
    private boolean setCellState(int screenX, int screenY, int state) {
        this.touchPoint.set(screenX, screenY);
        this.worldRenderer.touchToWorld(this.touchPoint);
        int x = (int) this.touchPoint.x;
        int y = (int) this.touchPoint.y;
        World world = this.simulation.getWorld();
        if (world.isCellPositionValid(x, y)) {
            world.setCellState(x, y, state);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.SPACE) {
            this.simulation.setRunning(!this.simulation.isRunning());
            return true;
        }
        return false;
    }
    
    @Override
    public boolean scrolled(int amount) {
        this.worldRenderer.setZoom(this.worldRenderer.getZoom() + amount * ZOOM_SPEED);
        return true;
    }
}
