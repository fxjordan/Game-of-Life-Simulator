package de.fjobilabs.gameoflife.model.simulation.ca.rules;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.simulation.ca.Rule;
import de.fjobilabs.gameoflife.model.simulation.ca.RuleSet;

/**
 * Rule set that can represent any GameOfLife rule variation in the form of
 * <i>SURVIVE</i>/<i>BIRTH</i>.<br>
 * <br>
 * Use {@link #parse(String)} to create instances directly from a rule
 * string.<br>
 * <br>
 * <i>SURVIVE</i> represents all neighbour numbers (0-8) for which a living cell
 * should survive.<br>
 * <i>BIRTH</i> represents all neighbour numbers (0-8) for which a dead cell
 * should be born.<br>
 * <br>
 * Example:<br>
 * 23/3, which is the original rule set from Conway, means that living cells
 * with 2 or 3 living neighbours should survive and dead cells with 3 living
 * neighbours should be born.
 * 
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 17:50:43
 */
public class LifeLikeRuleSet implements RuleSet {
    
    private final String name;
    private final Rule[] rules;
    
    public LifeLikeRuleSet(String name, int[] surviveNeighbours, int[] birthNeighbours) {
        this.name = name;
        validatePossibleNeighbours(surviveNeighbours);
        validatePossibleNeighbours(birthNeighbours);
        this.rules = new Rule[] {new SurviveRule(surviveNeighbours), new BirthRule(birthNeighbours)};
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Rule[] getRules() {
        return this.rules;
    }
    
    private class SurviveRule implements Rule {
        
        private final int[] surviveNeighbours;
        
        public SurviveRule(int[] surviveNeighbours) {
            this.surviveNeighbours = surviveNeighbours;
        }
        
        @Override
        public int apply(CellContext context) {
            if (context.getCellState() != Cell.ALIVE) {
                return NOT_APPLICABLE;
            }
            int aliveNeighbours = context.getAliveNeighbourCells();
            int[] survive = surviveNeighbours;
            int length = survive.length;
            for (int i = 0; i < length; i++) {
                if (survive[i] == aliveNeighbours) {
                    return Cell.ALIVE;
                }
            }
            return Cell.DEAD;
        }
    }
    
    private class BirthRule implements Rule {
        
        private final int[] birthNeighbours;
        
        public BirthRule(int[] birthNeighbours) {
            this.birthNeighbours = birthNeighbours;
        }
        
        @Override
        public int apply(CellContext context) {
            if (context.getCellState() != Cell.DEAD) {
                return NOT_APPLICABLE;
            }
            int aliveNeighbours = context.getAliveNeighbourCells();
            int[] birth = birthNeighbours;
            int length = birth.length;
            for (int i = 0; i < length; i++) {
                if (birth[i] == aliveNeighbours) {
                    return Cell.ALIVE;
                }
            }
            return Cell.DEAD;
        }
        
    }
    
    private void validatePossibleNeighbours(int[] possibleNeighbours) {
        int length = possibleNeighbours.length;
        if (length > 8) {
            throw new IllegalArgumentException("Maximal 8 possible neighbours are valid");
        }
        for (int i = 0; i < length; i++) {
            int numNeighbours = possibleNeighbours[i];
            if (numNeighbours < 0 || numNeighbours > 8) {
                throw new IllegalArgumentException(
                        "Invalid number of neighbours: " + numNeighbours + " (must be 0-8)");
            }
        }
    }
    
    /**
     * Creates a {@code GameOfLifeRuleSet} from a string representation in the
     * form of <i>SURVIVE</i>/<i>BIRTH</i>. The name of the rule set will be the
     * rule string itself. To specify a custom name use
     * {@link #parse(String, String)}<br>
     * <br>
     * See {@link LifeLikeRuleSet} for more information.
     * 
     * @param ruleString string representing a rule set.
     * @return A {@code GameOfLifeRuleSet} instance representing the given
     *         string.
     * 
     * @see LifeLikeRuleSet
     */
    public static LifeLikeRuleSet parse(String ruleString) {
        return parse(ruleString, ruleString);
        
    }
    
    /**
     * Creates a {@code GameOfLifeRuleSet} from a string representation in the
     * form of <i>SURVIVE</i>/<i>BIRTH</i>.<br>
     * <br>
     * See {@link LifeLikeRuleSet} for more information.
     * 
     * @param name The name of the rule set.
     * @param ruleString string representing a rule set.
     * @return A {@code GameOfLifeRuleSet} instance representing the given
     *         string.
     * 
     * @see LifeLikeRuleSet
     */
    public static LifeLikeRuleSet parse(String name, String ruleString) {
        int length = ruleString.length();
        /*
         * Maximal length is 19, because the longest possible rule is:
         * 012345678/012345678
         */
        if (length > 19) {
            throw new IllegalArgumentException(
                    "Invalid rule string: " + ruleString + " (length must be < 19");
        }
        int separatorIndex = ruleString.indexOf('/');
        if (separatorIndex == -1) {
            throw new IllegalArgumentException("Invalid rule string: " + ruleString + " (no separator '/')");
        }
        
        int possibleSurviveNeighboursLength = separatorIndex;
        int[] possibleSurviveNeighbours = new int[possibleSurviveNeighboursLength];
        int possibleBirthNeighboursLength = length - (separatorIndex + 1);
        int[] possibleBirthNeighbours = new int[possibleBirthNeighboursLength];
        
        char[] characters = ruleString.toCharArray();
        for (int i = 0; i < possibleSurviveNeighboursLength; i++) {
            possibleSurviveNeighbours[i] = Character.digit(characters[i], 10);
        }
        for (int i = 0; i < possibleBirthNeighboursLength; i++) {
            possibleBirthNeighbours[i] = Character.digit(characters[i + separatorIndex + 1], 10);
        }
        return new LifeLikeRuleSet(name, possibleSurviveNeighbours, possibleBirthNeighbours);
    }
}
