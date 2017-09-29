package de.fjobilabs.gameoflife.desktop.gui.dialog;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 29.09.2017 - 22:34:02
 */
public class RuleSetItem {
    
    private String ruleSet;
    private String label;
    
    public RuleSetItem(String ruleSet, String label) {
        this.ruleSet = ruleSet;
        this.label = label;
    }
    
    public String getRuleSet() {
        return ruleSet;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
