package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 19:20:21
 */
public class SimulationException extends RuntimeException {
    
    private static final long serialVersionUID = -7355598950938143751L;
    
    public SimulationException() {
    }
    
    public SimulationException(String message) {
        super(message);
    }
    
    public SimulationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SimulationException(Throwable cause) {
        super(cause);
    }
}
