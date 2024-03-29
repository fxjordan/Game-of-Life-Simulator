package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.fjobilabs.gameoflife.gui.controller.SimulationController;
import de.fjobilabs.gameoflife.gui.screen.IdleScreen;
import de.fjobilabs.gameoflife.gui.screen.SimulationScreen;
import de.fjobilabs.gameoflife.model.Simulation;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 24.09.2017 - 17:41:30
 */
public class SimulatorApplication extends Game {
    
    public static final int TEXTURE_CELL_RENDERER = 0;
    public static final int SHAPE_CELL_RENDERER = 1;
    public static final int HIGH_PERFORMANCE_CELL_RENDERER = 2;
    
    private GameOfLifeAssetManager assetManager;
    private IdleScreen idleScreen;
    private SimulationScreen simulationScreen;
    private boolean hasSimulation;
    
    @Override
    public void create() {
        this.assetManager = new GameOfLifeAssetManager();
        this.assetManager.load();
        this.assetManager.finishLoading();
        
        this.simulationScreen = new SimulationScreen(assetManager);
        this.idleScreen = new IdleScreen(assetManager);
        
        setScreen(this.idleScreen);
    }
    
    @Override
    public void dispose() {
        this.assetManager.dispose();
        this.idleScreen.dispose();
        this.simulationScreen.dispose();
    }
    
    public boolean hasSimulation() {
        return this.hasSimulation;
    }
    
    public void setSimulation(Simulation simulation) {
        if (simulation == null) {
            Timer.post(new Task() {
                
                @Override
                public void run() {
                    changeToIdle();
                }
            });
            
        } else {
            changeToSimulation(simulation);
        }
        
    }
    
    public void setCellRenderer(int rendererType) {
        this.simulationScreen.setCellRenderer(rendererType);
    }
    
    public SimulationController getSimulationController() {
        return this.simulationScreen.getSimulationController();
    }
    
    /**
     * Returns the number of frames that are currently rendered per second.
     * 
     * @return The current FPS.
     */
    public int getFPS() {
        if (Gdx.graphics == null) {
            return 0;
        }
        return Gdx.graphics.getFramesPerSecond();
    }
    
    public void setUps(int ups) {
        this.simulationScreen.setUpdatesPerSecond(ups);
    }
    
    public int getMeasuredUPS() {
        if (this.simulationScreen == null) {
            return 0;
        }
        return this.simulationScreen.getMeasuredUpdatesPerSecond();
    }
    
    private void changeToIdle() {
        if (this.hasSimulation) {
            this.hasSimulation = false;
            setScreen(this.idleScreen);
        }
    }
    
    private void changeToSimulation(Simulation simulation) {
        if (!this.hasSimulation) {
            this.hasSimulation = true;
            setScreen(this.simulationScreen);
        }
        this.simulationScreen.setSimulation(simulation);
    }
}
