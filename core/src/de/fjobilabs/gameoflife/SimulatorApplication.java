package de.fjobilabs.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.fjobilabs.gameoflife.gui.screen.IdleScreen;
import de.fjobilabs.gameoflife.gui.screen.SimulationScreen;
import de.fjobilabs.gameoflife.model.Simulation;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 24.09.2017 - 17:41:30
 */
public class SimulatorApplication extends Game {
    
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
    
    public void setEditMode(boolean enabled) {
        this.simulationScreen.getSimulationController().setEditMode(enabled);
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
