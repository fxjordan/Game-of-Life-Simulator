package de.fjobilabs.gameoflife.gui.screen;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 18:33:21
 */
public class UPSCounter {
    
    private int ups;
    private int updates;
    private long startTime;
    
    public UPSCounter() {
        this.startTime = System.nanoTime();
    }
    
    /**
     * Should be called once per frame.
     */
    public void update() {
        if (System.nanoTime() - this.startTime > 1000000000) {
            this.ups = updates;
            this.updates = 0;
            this.startTime = System.nanoTime();
        }
    }
    
    /**
     * Should be called for each simulation update.
     */
    public void logUpdate() {
        this.updates++;
    }
    
    public int getUPS() {
        return ups;
    }
}
