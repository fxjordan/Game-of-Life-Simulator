package de.fjobilabs.libgdx.util;

import com.badlogic.gdx.utils.Logger;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 15.09.2017 - 19:38:46
 */
public class LoggerFactory {
    
    private static final int DEFAULT_LOG_LEVEL = Logger.INFO;
    private static final Logger DEFAULT_LOGGER = new Logger("Default Logger", DEFAULT_LOG_LEVEL);
    
    private LoggerFactory() {
    }
    
    public static Logger getLogger() {
        return DEFAULT_LOGGER;
    }
    
    public static Logger getLogger(String tag) {
        return new Logger(tag, DEFAULT_LOG_LEVEL);
    }
    
    public static Logger getLogger(String tag, int level) {
        return new Logger(tag, level);
    }
    
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(createLoggerNameForClass(clazz), DEFAULT_LOG_LEVEL);
    }
    
    public static Logger getLogger(Class<?> clazz, int level) {
        return new Logger(createLoggerNameForClass(clazz), level);
    }
    
    private static String createLoggerNameForClass(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
