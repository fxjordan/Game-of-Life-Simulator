package de.fjobilabs.gameoflife.desktop.simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 29.09.2017 - 01:04:43
 */
public class WorldContentLoaderException extends Exception {
    
    private static final long serialVersionUID = 4298051332979466031L;
    
    public WorldContentLoaderException() {
    }
    
    public WorldContentLoaderException(String message) {
        super(message);
    }
    
    public WorldContentLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public WorldContentLoaderException(Throwable cause) {
        super(cause);
    }
}
