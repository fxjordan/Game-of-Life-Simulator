package de.fjobilabs.gameoflife.model.rules.standard;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.Rule;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 17:54:10
 */
public class StandardRule1 implements Rule {
    
    @Override
    public int apply(CellContext cellContext) {
        if (cellContext.getCellState() != Cell.ALIVE) {
            return NOT_APPLICABLE;
        }
        return cellContext.getAliveNeighbourCells() < 2 ? Cell.DEAD : NOT_APPLICABLE;
    }
}
