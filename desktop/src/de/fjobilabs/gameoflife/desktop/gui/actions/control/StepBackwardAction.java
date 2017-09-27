package de.fjobilabs.gameoflife.desktop.gui.actions.control;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 14:52:35
 */
public class StepBackwardAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = -7779941873823527180L;
    
    public static final String ACTION_COMMAND = "step_backward";
    
    public StepBackwardAction(ActionManager actionManager, Simulator simulator) {
        super(actionManager, "Step Backward", ACTION_COMMAND, simulator);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.simulator.hasSimulation()) {
            this.simulator.stepBackward();
        }
    }
}
