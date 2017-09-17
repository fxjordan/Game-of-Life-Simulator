package de.fjobilabs.gameoflife.model.simulation;

import de.fjobilabs.gameoflife.model.AbstractSimulation;
import de.fjobilabs.gameoflife.model.Cell;
import de.fjobilabs.gameoflife.model.SimulationException;
import de.fjobilabs.gameoflife.model.World;
import de.fjobilabs.gameoflife.model.simulation.Ant.Direction;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 17.09.2017 - 21:43:18
 */
public class LangtonsAntSimulation extends AbstractSimulation {
    
    private Ant ant;
    private boolean endOfWorld;
    
    public LangtonsAntSimulation(World world) {
        super(world);
        this.ant = new Ant(this.world.getCenterX(), this.world.getCenterY(), Direction.DOWN);
    }
    
    @Override
    public void update() {
        if (this.endOfWorld) {
            this.setRunning(false);
            return;
        }
        super.update();
    }
    
    @Override
    protected void doUpdate() {
        int antX = this.ant.getX();
        int antY = this.ant.getY();
        if (!this.world.isCellPositionValid(antX, antY)) {
            /*
             * Its possible that the ant gets to the end of a limited world. If
             * this happens the only thing we can do is to stop the simulation.
             */
            this.endOfWorld = true;
            setRunning(false);
            return;
        }
        
        int antCellState = this.world.getCellState(antX, antY);
        if (antCellState == Cell.DEAD) {
            // Step 1
            this.ant.turnRight();
            // Step 2: Switch field state
            this.world.setCellState(this.ant.getX(), this.ant.getY(), Cell.ALIVE);
        } else if (antCellState == Cell.ALIVE) {
            // Step 1
            this.ant.turnLeft();
            // Step 2: Switch field state
            this.world.setCellState(this.ant.getX(), this.ant.getY(), Cell.DEAD);
        } else {
            throw new SimulationException("Invalid cell state: " + antCellState);
        }
        this.ant.moveForward();
    }
}
