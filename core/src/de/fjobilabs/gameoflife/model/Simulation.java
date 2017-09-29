package de.fjobilabs.gameoflife.model;

/**
 * A {@code Simulation} takes a {@link World} and updates the worlds cells for
 * every simulation update. On this abstraction level there is no detailed
 * definition on how these updates should be calculated or which cells are
 * affected by an update.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 19:44:06
 */
public interface Simulation {
    
    /**
     * Updates the simulation. This method must perform exactly one update of
     * the simulation. Also an implementation should only perform an update if
     * the simulation is currently running (if {@link #isRunning()} returns
     * <code>true</code>).
     */
    public void update();
    
    /**
     * Returns the {@link World} where the simulation is running in.
     * 
     * @return The world of the simulation.
     */
    public World getWorld();
    
    /**
     * Returns whether the simulation is running.
     * 
     * @return <code>true</code> if the the simulation is running,
     *         <code>false</code> if not.
     */
    public boolean isRunning();
    
    /**
     * Sets whether the simulation should run.
     * 
     * @param running Whether the simulation should run or not.
     */
    public void setRunning(boolean running);
    
    /**
     * Returns the current generation number of the simulation.
     * 
     * @return The current generation number.
     */
    public int getGeneration();
}
