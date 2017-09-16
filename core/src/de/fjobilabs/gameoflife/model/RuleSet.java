package de.fjobilabs.gameoflife.model;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 15:32:14
 */
public interface RuleSet {
    
    /**
     * Returns the name of the rule set.
     * 
     * @return The rule sets name.
     */
    public String getName();
    
    /**
     * Returns all rules of this rule set.
     * 
     * @return All rules.
     */
    public Rule[] getRules();
}
