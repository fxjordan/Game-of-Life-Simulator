package de.fjobilabs.gameoflife.desktop.simulator.io;

/**
 * Model representing a worlds content in static, serializable way.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 28.09.2017 - 19:42:07
 */
public class WorldContentModel {
    
    /** Pattern format used by this model */
    private String patternFormat;
    
    /** Start position of the patter on the x-axis. */
    private int patternX;
    
    /** Start position of the pattern on the y-axis. */
    private int patternY;
    
    /** Pattern that represents the simulations world */
    private String pattern;
    
    public String getPatternFormat() {
        return patternFormat;
    }
    
    public void setPatternFormat(String patternFormat) {
        this.patternFormat = patternFormat;
    }
    
    public int getPatternX() {
        return patternX;
    }
    
    public void setPatternX(int patternX) {
        this.patternX = patternX;
    }
    
    public int getPatternY() {
        return patternY;
    }
    
    public void setPatternY(int patternY) {
        this.patternY = patternY;
    }
    
    public String getPattern() {
        return pattern;
    }
    
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
