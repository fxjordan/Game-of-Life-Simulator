package de.fjobilabs.gameoflife.model;

/**
 * Defines default behavior for a {@link Simulation} implementation.<br>
 * This class stores the world, state and generation number of the simulation
 * and implements the related methods. Child classes only have to implement the
 * {@link #doUpdate()} method, which is only called when the simulation is
 * running.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 20:21:47
 */
public abstract class AbstractSimulation implements Simulation {
    
    protected World world;
    private boolean running;
    private int generation;
    
    public AbstractSimulation(World world) {
        this.world = world;
    }
    
    @Override
    public void update() {
        if (this.running) {
            doUpdate();
            this.generation++;
        }
    }
    
    /**
     * Performs one step of the simulation.
     */
    protected abstract void doUpdate();
    
    @Override
    public World getWorld() {
        return this.world;
    }
    
    @Override
    public boolean isRunning() {
        return this.running;
    }
    
    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    @Override
    public int getGeneration() {
        return this.generation;
    }
}
