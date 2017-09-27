package de.fjobilabs.gameoflife.gui;

import javax.xml.ws.WebEndpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 01:43:12
 */
/*
 * TODO Controller is not responsible for key events anymore. The java swing
 * wrapper will handle this.
 */
public class SimulationController extends InputAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);
    
    private static final float ZOOM_SPEED = 0.5f;
    private static final float MOVE_SPEED = 10.0f;
    
    private static final int EDIT_TYPE_NONE = -1;
    private static final int EDIT_TYPE_ADD = 0;
    private static final int EDIT_TYPE_REMOVE = 1;
    
    private Simulation simulation;
    private WorldRenderer worldRenderer;
    private Vector2 touchPoint;
    private boolean editMode;
    private int currentEditType;
    
    public SimulationController(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
        this.touchPoint = new Vector2();
        reset();
    }
    
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        reset();
    }
    
    public void setEditMode(boolean enabled) {
        this.editMode = enabled;
        this.currentEditType = EDIT_TYPE_NONE;
    }
    
    public void reset() {
        this.editMode = false;
        this.currentEditType = EDIT_TYPE_NONE;
    }
    
    public void update(float delta) {
        if (this.simulation == null) {
            return;
        }
        float moveSpeed = MOVE_SPEED;
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
            moveSpeed *= 20;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.worldRenderer.setCameraX(this.worldRenderer.getCameraX() - moveSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.worldRenderer.setCameraX(this.worldRenderer.getCameraX() + moveSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            this.worldRenderer.setCameraY(this.worldRenderer.getCameraY() - moveSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            this.worldRenderer.setCameraY(this.worldRenderer.getCameraY() + moveSpeed * delta);
        }
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (this.simulation == null) {
            return false;
        }
        if (this.currentEditType != EDIT_TYPE_NONE || !this.editMode) {
            return false;
        }
        if (button == Buttons.LEFT) {
            this.currentEditType = EDIT_TYPE_ADD;
            return true;
        }
        if (button == Buttons.RIGHT) {
            this.currentEditType = EDIT_TYPE_REMOVE;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (this.simulation == null) {
            return false;
        }
        if (this.currentEditType == EDIT_TYPE_NONE || !this.editMode) {
            return false;
        }
        if (this.currentEditType == EDIT_TYPE_ADD) {
            setCellState(screenX, screenY, Cell.ALIVE);
            return true;
        }
        if (this.currentEditType == EDIT_TYPE_REMOVE) {
            setCellState(screenX, screenY, Cell.DEAD);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.simulation == null) {
            return false;
        }
        if (this.currentEditType == EDIT_TYPE_NONE || !this.editMode) {
            return false;
        }
        if (this.currentEditType == EDIT_TYPE_ADD && button == Buttons.LEFT) {
            setCellState(screenX, screenY, Cell.ALIVE);
            this.currentEditType = EDIT_TYPE_NONE;
            return true;
        }
        if (this.currentEditType == EDIT_TYPE_REMOVE && button == Buttons.RIGHT) {
            setCellState(screenX, screenY, Cell.DEAD);
            this.currentEditType = EDIT_TYPE_NONE;
            return true;
        }
        return false;
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
    @Deprecated
    @SuppressWarnings("unused")
    public boolean keyDown(int keycode) {
        if (true) {
            return false;
        }
        if (this.simulation == null) {
            return false;
        }
        if (keycode == Keys.SPACE) {
            this.simulation.setRunning(!this.simulation.isRunning());
            return true;
        }
        if (keycode == Keys.S) {
            this.simulation.setRunning(true);
            for (int i = 0; i < 10; i++) {
                this.simulation.update();
            }
            this.simulation.setRunning(false);
            return true;
        }
        if (keycode == Keys.R) {
            // Enables or diables rendering
            this.worldRenderer.setEnabled(!this.worldRenderer.isEnabled());
            return true;
        }
        if (keycode == Keys.E) {
            // Enables or disables edit mode
            this.editMode = !this.editMode;
            logger.info("Edit mode " + (this.editMode ? "enabled" : "disabled"));
        }
        return false;
    }
    
    @Override
    public boolean scrolled(int amount) {
        if (this.simulation == null) {
            return false;
        }
        float zoomSpeed = ZOOM_SPEED;
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
            zoomSpeed *= 20;
        }
        this.worldRenderer.setZoom(this.worldRenderer.getZoom() + amount * zoomSpeed);
        return true;
    }
}
