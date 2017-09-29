package de.fjobilabs.gameoflife.desktop.simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 29.09.2017 - 01:08:52
 */
public class SimulatorException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1664787803893722314L;
    
    public SimulatorException() {
    }
    
    public SimulatorException(String message) {
        super(message);
    }
    
    public SimulatorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SimulatorException(Throwable cause) {
        super(cause);
    }
}
