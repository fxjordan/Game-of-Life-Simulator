package de.fjobilabs.gameoflife.desktop.gui.actions.control;

import java.awt.event.ActionEvent;

import de.fjobilabs.gameoflife.desktop.gui.actions.AbstractSimulationAction;
import de.fjobilabs.gameoflife.desktop.gui.actions.ActionManager;
import de.fjobilabs.gameoflife.desktop.simulator.Simulator;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 27.09.2017 - 14:39:19
 */
public class StepForwardAction extends AbstractSimulationAction {
    
    private static final long serialVersionUID = -6562814021502564945L;
    
    public static final String ACTION_COMMAND = "step_forward";
    
    public StepForwardAction(ActionManager actionManager, Simulator simulator) {
        super(actionManager, "Step Forward", ACTION_COMMAND, simulator);
        setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.simulator.hasSimulation()) {
            this.simulator.stepForward();
        }
    }
}
