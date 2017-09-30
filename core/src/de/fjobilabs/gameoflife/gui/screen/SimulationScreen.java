package de.fjobilabs.gameoflife.gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Logger;

import de.fjobilabs.gameoflife.GameOfLifeAssetManager;
import de.fjobilabs.gameoflife.SimulatorApplication;
import de.fjobilabs.gameoflife.gui.HighPerformanceCellRenderer;
import de.fjobilabs.gameoflife.gui.ShapeCellRenderer;
import de.fjobilabs.gameoflife.gui.TextureCellRenderer;
import de.fjobilabs.gameoflife.gui.WorldRenderer;
import de.fjobilabs.gameoflife.gui.controller.SimulationController;
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
    
    private GameOfLifeAssetManager assetManager;
    private WorldRenderer worldRenderer;
    private SimulationController simulationController;
    private FPSLogger fpsLogger;
    private Simulation simulation;
    private float fixedStepTime;
    
    private TextureCellRenderer textureCellRenderer;
    private ShapeCellRenderer shapeCellRenderer;
    private HighPerformanceCellRenderer highPerformanceCellRenderer;
    
    private UPSCounter upsCounter;
    
    // Configuration variables
    private float updateDuration;
    private int maxUpdatesPerFrame;
    
    public SimulationScreen(GameOfLifeAssetManager assetManager) {
        this.assetManager = assetManager;
        this.worldRenderer = new WorldRenderer(assetManager);
        this.worldRenderer.setCellRenderer(getTextureCellRendererInstance());
        this.simulationController = new SimulationController(this.worldRenderer);
        this.fpsLogger = new FPSLogger();
        this.upsCounter = new UPSCounter();
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
        this.fixedStepTime += delta;
        int updates = 0;
        while (fixedStepTime >= this.updateDuration && updates < this.maxUpdatesPerFrame) {
            updateSimulation();
            updates++;
            this.fixedStepTime -= this.updateDuration;
        }
        
        this.upsCounter.update();
        
        // Normal updates
        this.simulationController.update(delta);
    }
    
    private void updateSimulation() {
        if (this.simulation != null) {
            // TODO Call counter only when simulation has made an update
            if (this.simulation.isRunning()) {
                this.upsCounter.logUpdate();
            }
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
        if (this.textureCellRenderer != null) {
            this.textureCellRenderer.dispose();
        }
        if (this.shapeCellRenderer != null) {
            this.shapeCellRenderer.dispose();
        }
        if (this.highPerformanceCellRenderer != null) {
            this.highPerformanceCellRenderer.dispose();
        }
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
    
    public void setCellRenderer(int rendererType) {
        switch (rendererType) {
        case SimulatorApplication.TEXTURE_CELL_RENDERER:
            this.worldRenderer.setCellRenderer(getTextureCellRendererInstance());
            break;
        case SimulatorApplication.SHAPE_CELL_RENDERER:
            this.worldRenderer.setCellRenderer(getShapeCellRendererInstance());
            break;
        case SimulatorApplication.HIGH_PERFORMANCE_CELL_RENDERER:
            this.worldRenderer.setCellRenderer(getHighPerformanceCellRendererInstance());
            break;
        default:
            throw new IllegalArgumentException("Illegal cell renderer type: " + rendererType);
        }
    }
    
    public void setUpdatesPerSecond(int updatesPerSecond) {
        this.updateDuration = 1.0f / (float) updatesPerSecond;
    }
    
    /**
     * Returns the number of updates which are currently calculated per
     * second.<br>
     * This value may not be the same as the theoretical UPS, which are set by
     * the user.
     * 
     * @return The current UPS.
     */
    public int getMeasuredUpdatesPerSecond() {
        return this.upsCounter.getUPS();
    }
    
    public void setMaxUpdatesPerFrame(int maxUpdatesPerFrame) {
        this.maxUpdatesPerFrame = maxUpdatesPerFrame;
    }
    
    private TextureCellRenderer getTextureCellRendererInstance() {
        if (this.textureCellRenderer == null) {
            this.textureCellRenderer = new TextureCellRenderer(assetManager);
        }
        return this.textureCellRenderer;
    }
    
    private ShapeCellRenderer getShapeCellRendererInstance() {
        if (this.shapeCellRenderer == null) {
            this.shapeCellRenderer = new ShapeCellRenderer();
        }
        return this.shapeCellRenderer;
    }
    
    private HighPerformanceCellRenderer getHighPerformanceCellRendererInstance() {
        if (this.highPerformanceCellRenderer == null) {
            this.highPerformanceCellRenderer = new HighPerformanceCellRenderer();
        }
        return this.highPerformanceCellRenderer;
    }
}
