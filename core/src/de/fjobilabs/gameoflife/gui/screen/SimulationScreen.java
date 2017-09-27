package de.fjobilabs.gameoflife.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.gui.SimulationController;
import de.fjobilabs.gameoflife.gui.WorldRenderer;
import de.fjobilabs.gameoflife.model.Simulation;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.libgdx.util.LoggerFactory;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 24.09.2017 - 19:30:02
 */
public class SimulationScreen extends ScreenAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(SimulationScreen.class, Logger.DEBUG);
    
    private static final int DEFAULT_UPDATES_PER_SECOND = 10;
    private static final int DEFAULT_MAX_UPDATES_PER_FRAME = 5;
    
    private WorldRenderer worldRenderer;
    private SimulationController simulationController;
    private FPSLogger fpsLogger;
    private Simulation simulation;
    private float time;
    
    // Configuration variables
    private float updateDuration;
    private int maxUpdatesPerFrame;
    
    public SimulationScreen(GameOfLifeAssetManager assetManager) {
        this.worldRenderer = new WorldRenderer(assetManager);
        this.simulationController = new SimulationController(this.worldRenderer);
        this.fpsLogger = new FPSLogger();
        setUpdatesPerSecond(DEFAULT_UPDATES_PER_SECOND);
        setMaxUpdatesPerFrame(DEFAULT_MAX_UPDATES_PER_FRAME);
    }
    
    @Override
    public void show() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.input.setInputProcessor(this.simulationController);
    }
    
    @Override
    public void resize(int width, int height) {
        this.worldRenderer.resize(width, height);
        logger.debug("Resized to: width=" + width + ", height=" + height);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        update(delta);
        
        this.worldRenderer.render();
        this.fpsLogger.log();
    }
    
    private void update(float delta) {
        // Fixed time step updates
        this.time += delta;
        int updates = 0;
        while (time >= this.updateDuration && updates < this.maxUpdatesPerFrame) {
            updateSimulation();
            updates++;
            this.time -= this.updateDuration;
        }
        
        // Normal updates
        this.simulationController.update(delta);
    }
    
    private void updateSimulation() {
        if (this.simulation != null) {
            this.simulation.update();
        }
    }
    
    @Override
    public void hide() {
        this.worldRenderer.hide();
    }
    
    @Override
    public void dispose() {
        this.worldRenderer.dispose();
    }
    
    public SimulationController getSimulationController() {
        return simulationController;
    }
    
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        World world = null;
        if (simulation != null) {
            world = simulation.getWorld();
        }
        this.worldRenderer.setWorld(world);
        this.simulationController.setSimulation(simulation);
    }
    
    public void setUpdatesPerSecond(int updatesPerSecond) {
        this.updateDuration = 1.0f / (float) updatesPerSecond;
    }
    
    public void setMaxUpdatesPerFrame(int maxUpdatesPerFrame) {
        this.maxUpdatesPerFrame = maxUpdatesPerFrame;
    }
}
