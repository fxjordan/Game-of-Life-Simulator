package de.fjobilabs.gameoflife.desktop.simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fjobilabs.gameoflife.SimulatorApplication;
import de.fjobilabs.gameoflife.model.World;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 12:38:43
 */
public class WorldEditor {
    
    private static final Logger logger = LoggerFactory.getLogger(WorldEditor.class);
    
    private SimulatorApplication simulatorApplication;
    private PatternManager patternManager;
    private World world;
    private String currentPattern;
    
    public WorldEditor(SimulatorApplication simulatorApplication) {
        this.simulatorApplication = simulatorApplication;
        this.patternManager = new PatternManager();
    }
    
    public boolean isEditingEnabled() {
        return this.world != null;
    }
    
    public void setWorld(World world) {
        this.world = world;
    }
    
    public int getWorldWidth() {
        if (this.world == null) {
            return 0;
        }
        return this.world.getWidth();
    }
    
    public int getWorldHeight() {
        if (this.world == null) {
            return 0;
        }
        return this.world.getHeight();
    }
    
    public String getCurrentPattern() {
        return currentPattern;
    }
    
    public void setCurrentPattern(String patternId) {
        this.currentPattern = patternId;
    }
    
    public void addPatternToWorld(String patternId) throws WorldEditorException {
        checkEditingEnabled();
        LoadedPattern loadedPattern = this.patternManager.getLoadedPattern(patternId);
        if (loadedPattern == null) {
            throw new WorldEditorException("Unknown pattern id: " + patternId);
        }
        this.simulatorApplication.getSimulationController().startAddPattern(loadedPattern.getPattern());
        logger.debug("Start adding pattern '{}' to world", patternId);
    }
    
    public void cancelAddPattern() {
        this.simulatorApplication.getSimulationController().cancelAddPattern();
    }
    
    public void clearWorld() {
        this.world.clear();
    }
    
    public PatternManager getPatternManager() {
        return patternManager;
    }
    
    private void checkEditingEnabled() {
        if (!isEditingEnabled()) {
            throw new IllegalStateException("Cannot edit world");
        }
    }
}
