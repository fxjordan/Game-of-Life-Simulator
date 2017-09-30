package de.fjobilabs.gameoflife.desktop.simulator;

import de.fjobilabs.gameoflife.model.simulation.ca.Pattern;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 12:32:00
 */
public class LoadedPattern {
    
    private String patternId;
    private Pattern pattern;
    private String name;
    
    public LoadedPattern(String patternId, Pattern pattern, String label) {
        this.patternId = patternId;
        this.pattern = pattern;
        this.name = label;
    }
    
    public String getPatternId() {
        return patternId;
    }
    
    public Pattern getPattern() {
        return pattern;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
