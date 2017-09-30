package de.fjobilabs.gameoflife.desktop.gui.actions;

import de.fjobilabs.gameoflife.desktop.SimulatorFrame;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 30.09.2017 - 13:40:39
 */
public abstract class AbstractSimulationGUIAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = 5401558988805651605L;
    
    protected SimulatorFrame simulatorFrame;
    
    public AbstractSimulationGUIAction(ActionManager actionManager, String name, String actionCommand,
            SimulatorFrame simulatorFrame, Simulator simulator) {
        super(actionManager, name, actionCommand, simulator);
        this.simulatorFrame = simulatorFrame;
    }
}
