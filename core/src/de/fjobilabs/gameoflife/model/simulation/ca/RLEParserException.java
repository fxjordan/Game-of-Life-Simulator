package de.fjobilabs.gameoflife.model.simulation.ca;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 18.09.2017 - 00:03:35
 */
public class RLEParserException extends RuntimeException {
    
    private static final long serialVersionUID = 5003961947477819185L;
    
    public RLEParserException() {
    }
    
    public RLEParserException(String message) {
        super(message);
    }
    
    public RLEParserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public RLEParserException(Throwable cause) {
        super(cause);
    }
}
