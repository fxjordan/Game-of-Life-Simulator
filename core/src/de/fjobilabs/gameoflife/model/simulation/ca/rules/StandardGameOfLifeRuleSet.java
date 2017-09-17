package de.fjobilabs.gameoflife.model.simulation.ca.rules;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.simulation.ca.Rule;
import de.fjobilabs.gameoflife.model.simulation.ca.RuleSet;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 18:19:14
 */
public class StandardGameOfLifeRuleSet implements RuleSet {
    
    private static final Rule[] STANDARD_RULES = new Rule[] {new StandardSurviveRule(),
            new StandardBirthRule()};
    
    @Override
    public String getName() {
        return "Standard Game Of Live Rule Set (23/3)";
    }
    
    @Override
    public Rule[] getRules() {
        return STANDARD_RULES;
    }
    
    private static class StandardSurviveRule implements Rule {
        
        private static final int SURVICE_NEIGHBOURS_1 = 2;
        private static final int SURVICE_NEIGHBOURS_2 = 3;
        
        @Override
        public int apply(CellContext context) {
            if (context.getCellState() != Cell.ALIVE) {
                return NOT_APPLICABLE;
            }
            int aliveNeighbours = context.getAliveNeighbourCells();
            if (aliveNeighbours == SURVICE_NEIGHBOURS_1 || aliveNeighbours == SURVICE_NEIGHBOURS_2) {
                return Cell.ALIVE;
            }
            return Cell.DEAD;
        }
    }
    
    private static class StandardBirthRule implements Rule {
        
        private static final int BIRTH_NEIGHBOURS = 3;
        
        @Override
        public int apply(CellContext context) {
            if (context.getCellState() != Cell.DEAD) {
                return NOT_APPLICABLE;
            }
            int aliveNeighbours = context.getAliveNeighbourCells();
            if (aliveNeighbours == BIRTH_NEIGHBOURS) {
                return Cell.ALIVE;
            }
            return Cell.DEAD;
        }
        
    }
}
