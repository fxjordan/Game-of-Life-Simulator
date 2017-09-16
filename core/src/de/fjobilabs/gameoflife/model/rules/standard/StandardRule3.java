package de.fjobilabs.gameoflife.model.rules.standard;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.Rule;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 17:58:37
 */
public class StandardRule3 implements Rule {
    
    @Override
    public int apply(CellContext context) {
        if (context.getCellState() != Cell.ALIVE) {
            return NOT_APPLICABLE;
        }
        return context.getAliveNeighbourCells() > 3 ? Cell.DEAD : NOT_APPLICABLE;
    }
}
