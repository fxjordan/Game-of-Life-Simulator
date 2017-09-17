package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 19:44:06
 */
public interface SimulationI {
    
    public void update();
    
    public boolean isRunning();
    
    public void setRunning(boolean running);
}
