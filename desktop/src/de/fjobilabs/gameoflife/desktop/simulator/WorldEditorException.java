package de.fjobilabs.gameoflife.desktop.simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 13:00:41
 */
public class WorldEditorException extends Exception {
    
    private static final long serialVersionUID = -41127640669837167L;

    public WorldEditorException() {
    }
    
    public WorldEditorException(String message) {
        super(message);
    }
    
    public WorldEditorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public WorldEditorException(Throwable cause) {
        super(cause);
    }
}
