package de.fjobilabs.gameoflife.model.rules.standard;

import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.CellContext;
import de.fjobilabs.gameoflife.model.Rule;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 16.09.2017 - 17:55:39
 */
public class StandardRule2 implements Rule {
    
    @Override
    public int apply(CellContext context) {
        if (context.getCellState() != Cell.ALIVE) {
            return NOT_APPLICABLE;
        }
        int aliveNeighbours = context.getAliveNeighbourCells();
        return aliveNeighbours == 2 || aliveNeighbours == 3 ? Cell.ALIVE : NOT_APPLICABLE;
    }
}
