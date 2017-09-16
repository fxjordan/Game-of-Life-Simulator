package de.fjobilabs.gameoflife.model.rules.standard;

import de.fjobilabs.gameoflife.model.Rule;
import de.fjobilabs.gameoflife.model.RuleSet;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 18:01:47
 */
public class StandardRuleSet implements RuleSet {
    
    public static final String NAME = "Standard-Rules";
    
    private static final Rule[] RULES = new Rule[] {new StandardRule1(), new StandardRule2(),
            new StandardRule3(), new StandardRule4()};
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public Rule[] getRules() {
        return RULES;
    }
}
