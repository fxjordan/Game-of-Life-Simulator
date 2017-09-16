package de.fjobilabs.libgdx;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 19:32:16
 */
public class ScreenManagerException extends RuntimeException {
    
    private static final long serialVersionUID = -8336204961127407197L;
    
    public ScreenManagerException() {
    }
    
    public ScreenManagerException(String message) {
        super(message);
    }
    
    public ScreenManagerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ScreenManagerException(Throwable cause) {
        super(cause);
    }
}
